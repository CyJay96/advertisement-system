package com.advertisement.advertisementsystem.builder.user;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.entity.Role;
import com.advertisement.advertisementsystem.model.entity.User;
import com.advertisement.advertisementsystem.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_DATE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_EMAIL;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PHONE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aUser")
public class UserTestBuilder implements TestBuilder<User> {

    private Long id = TEST_ID;

    private String username = TEST_STRING;

    private String firstName = TEST_STRING;

    private String lastName = TEST_STRING;

    private String email = TEST_EMAIL;

    private String phone = TEST_PHONE;

    private OffsetDateTime createDate = TEST_DATE;

    private OffsetDateTime lastUpdateDate = TEST_DATE;

    private List<Role> roles = new ArrayList<>();

    private Status status = Status.ACTIVE;

    @Override
    public User build() {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .roles(roles)
                .status(status)
                .build();
    }
}
