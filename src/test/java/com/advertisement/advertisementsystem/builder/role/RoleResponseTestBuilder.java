package com.advertisement.advertisementsystem.builder.role;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.response.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aRoleResponse")
public class RoleResponseTestBuilder implements TestBuilder<RoleResponse> {

    private Long id = TEST_ID;

    private String name = TEST_STRING;

    @Override
    public RoleResponse build() {
        return RoleResponse.builder()
                .id(id)
                .name(name)
                .build();
    }
}
