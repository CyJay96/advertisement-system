package com.advertisement.advertisementsystem.builder.advertiser;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.entity.Campaign;
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
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aAdvertiser")
public class AdvertiserTestBuilder implements TestBuilder<Advertiser> {

    private Long id = TEST_ID;

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private String pictureUrl = TEST_EMAIL;

    private Status status = Status.ACTIVE;

    private OffsetDateTime createDate = TEST_DATE;

    private OffsetDateTime lastUpdateDate = TEST_DATE;

    private List<Campaign> campaigns = new ArrayList<>();

    @Override
    public Advertiser build() {
        return Advertiser.builder()
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
