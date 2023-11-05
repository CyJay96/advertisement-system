package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.dto.request.UserRequest;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.dto.response.UserResponse;
import com.advertisement.advertisementsystem.model.entity.User;
import org.springframework.data.domain.Pageable;

public interface UserService {

    PageResponse<UserResponse> findAll(Pageable pageable);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    User findEntityByUsername(String username);

    UserResponse update(Long id, UserRequest userRequest);

    UserResponse restoreById(Long id);

    UserResponse deleteById(Long id);
}
