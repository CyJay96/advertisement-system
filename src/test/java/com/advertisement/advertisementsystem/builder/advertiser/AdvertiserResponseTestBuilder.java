package com.advertisement.advertisementsystem.builder.advertiser;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.response.AdvertiserResponse;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_DATE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_EMAIL;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aAdvertiserResponse")
public class AdvertiserResponseTestBuilder implements TestBuilder<AdvertiserResponse> {

    private Long id = TEST_ID;

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private String pictureUrl = TEST_EMAIL;

    private Status status = Status.ACTIVE;

    private OffsetDateTime createDate = TEST_DATE;

    private OffsetDateTime lastUpdateDate = TEST_DATE;

    private PageResponse<CampaignResponse> campaigns = PageResponse.<CampaignResponse>builder().build();

    @Override
    public AdvertiserResponse build() {
        return AdvertiserResponse.builder()
                .id(id)
                .title(title)
                .description(description)
                .location(location)
                .pictureUrl(pictureUrl)
                .status(status)
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .campaigns(campaigns)
                .build();
    }
}
