package com.advertisement.advertisementsystem.builder.campaign;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.request.CampaignRequest;
import com.advertisement.advertisementsystem.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_EMAIL;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCampaignRequest")
public class CampaignRequestTestBuilder implements TestBuilder<CampaignRequest> {

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private String pictureUrl = TEST_EMAIL;

    private List<Long> countriesIds = new ArrayList<>();

    private List<Long> languagesIds = new ArrayList<>();

    private UserType userType = UserType.FROM_18_YEARS;

    @Override
    public CampaignRequest build() {
        return CampaignRequest.builder()
                .title(title)
                .description(description)
                .location(location)
                .pictureUrl(pictureUrl)
                .countriesIds(countriesIds)
                .languagesIds(languagesIds)
                .userType(userType)
                .build();
    }
}
