package com.advertisement.advertisementsystem.builder.campaign;

import com.advertisement.advertisementsystem.builder.advertiser.AdvertiserTestBuilder;
import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.entity.Campaign;
import com.advertisement.advertisementsystem.model.entity.Country;
import com.advertisement.advertisementsystem.model.entity.Language;
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
@NoArgsConstructor(staticName = "aCampaign")
public class CampaignTestBuilder implements TestBuilder<Campaign> {

    private Long id = TEST_ID;

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private String pictureUrl = TEST_EMAIL;

    private List<Country> countries = new ArrayList<>();

    private List<Language> languages = new ArrayList<>();

    private UserType userType = UserType.FROM_18_YEARS;

    private Status status = Status.ACTIVE;

    private OffsetDateTime createDate = TEST_DATE;

    private OffsetDateTime lastUpdateDate = TEST_DATE;

    private Advertiser advertiser = AdvertiserTestBuilder.aAdvertiser().build();

    @Override
    public Campaign build() {
        return Campaign.builder()
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
                .advertiser(advertiser)
                .build();
    }
}
