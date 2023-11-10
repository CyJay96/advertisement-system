package com.advertisement.advertisementsystem.builder.role;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.entity.Role;
import com.advertisement.advertisementsystem.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aRole")
public class RoleTestBuilder implements TestBuilder<Role> {

    private Long id = TEST_ID;

    private String name = TEST_STRING;

    private List<User> users = new ArrayList<>();

    @Override
    public Role build() {
        return Role.builder()
                .id(id)
                .name(name)
                .users(users)
                .build();
    }
}
