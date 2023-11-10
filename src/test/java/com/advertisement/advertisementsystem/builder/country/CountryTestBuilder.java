package com.advertisement.advertisementsystem.builder.country;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.entity.Country;
import com.advertisement.advertisementsystem.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCountry")
public class CountryTestBuilder implements TestBuilder<Country> {

    private Long id = TEST_ID;

    private String name = TEST_STRING;

    private Status status = Status.ACTIVE;

    @Override
    public Country build() {
        return Country.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
    }
}
