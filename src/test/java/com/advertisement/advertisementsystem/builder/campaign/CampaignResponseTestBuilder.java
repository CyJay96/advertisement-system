package com.advertisement.advertisementsystem.builder.campaign;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.CountryResponse;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_DATE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_EMAIL;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCampaignResponse")
public class CampaignResponseTestBuilder implements TestBuilder<CampaignResponse> {

    private Long id = TEST_ID;

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private String pictureUrl = TEST_EMAIL;

    private List<CountryResponse> countries = new ArrayList<>();

    private List<LanguageResponse> languages = new ArrayList<>();

    private UserType userType = UserType.FROM_18_YEARS;

    private Status status = Status.ACTIVE;

    private OffsetDateTime createDate = TEST_DATE;

    private OffsetDateTime lastUpdateDate = TEST_DATE;

    private Long advertiserId = TEST_ID;

    @Override
    public CampaignResponse build() {
        return CampaignResponse.builder()
                .id(id)
                .title(title)
                .description(description)
                .location(location)
                .pictureUrl(pictureUrl)
                .countries(countries)
                .languages(languages)
                .userType(userType)
                .status(status)
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .advertiserId(advertiserId)
                .build();
    }
}
