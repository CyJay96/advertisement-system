package com.advertisement.advertisementsystem.service.impl;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.UserMapper;
import com.advertisement.advertisementsystem.model.dto.request.UserRequest;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.dto.response.UserResponse;
import com.advertisement.advertisementsystem.model.entity.User;
import com.advertisement.advertisementsystem.model.enums.Role;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.UserRepository;
import com.advertisement.advertisementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public PageResponse<UserResponse> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> userResponses = userPage.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return PageResponse.<UserResponse>builder()
                .content(userResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(userResponses.size())
                .build();
    }

    @Override
    @Transactional
    public UserResponse findById(Long id, Principal principal) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
        checkUserForPermissions(user, principal);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse findByUsername(String username, Principal principal) {
        User user = findEntityByUsername(username);
        checkUserForPermissions(user, principal);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, username));
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest userRequest, Principal principal) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
        checkUserForPermissions(user, principal);
        userMapper.updateUser(userRequest, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse restoreById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
        user.setStatus(Status.ACTIVE);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
        user.setStatus(Status.DELETED);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    private void checkUserForPermissions(User userByRequest, Principal principal) {
        User userPrincipal = findEntityByUsername(principal.getName());

        if (!userByRequest.getUsername().equals(principal.getName()) &&
                userPrincipal.getRoles().stream().noneMatch(role -> role.getName().equals(Role.ROLE_ADMIN.name()))
        ) {
            throw new AccessDeniedException("Access is forbidden for this role");
        }
    }
}
