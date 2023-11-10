package com.advertisement.advertisementsystem.builder.country;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.request.CountryRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCountryRequest")
public class CountryRequestTestBuilder implements TestBuilder<CountryRequest> {

    private String name = TEST_STRING;

    @Override
    public CountryRequest build() {
        return CountryRequest.builder()
                .name(name)
                .build();
    }
}
