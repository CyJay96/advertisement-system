package com.advertisement.advertisementsystem.builder.role;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.request.RoleRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aRoleRequest")
public class RoleRequestTestBuilder implements TestBuilder<RoleRequest> {

    private String name = TEST_STRING;

    @Override
    public RoleRequest build() {
        return RoleRequest.builder()
                .name(name)
                .build();
    }
}
