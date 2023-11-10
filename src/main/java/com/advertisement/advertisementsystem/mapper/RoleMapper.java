package com.advertisement.advertisementsystem.mapper;

import com.advertisement.advertisementsystem.model.dto.request.RoleRequest;
import com.advertisement.advertisementsystem.model.dto.response.RoleResponse;
import com.advertisement.advertisementsystem.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
