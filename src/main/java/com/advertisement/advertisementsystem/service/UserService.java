package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.dto.request.UserRequest;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.dto.response.UserResponse;
import com.advertisement.advertisementsystem.model.entity.User;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface UserService {

    PageResponse<UserResponse> findAll(Pageable pageable);

    UserResponse findById(Long id, Principal principal);

    UserResponse findByUsername(String username, Principal principal);

    User findEntityByUsername(String username);

    UserResponse update(Long id, UserRequest userRequest, Principal principal);

    UserResponse restoreById(Long id);

    UserResponse deleteById(Long id);
}
