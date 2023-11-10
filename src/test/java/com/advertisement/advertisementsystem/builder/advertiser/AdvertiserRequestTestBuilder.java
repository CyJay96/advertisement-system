package com.advertisement.advertisementsystem.builder.advertiser;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.request.AdvertiserRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_EMAIL;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aAdvertiserRequest")
public class AdvertiserRequestTestBuilder implements TestBuilder<AdvertiserRequest> {

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private String pictureUrl = TEST_EMAIL;

    @Override
    public AdvertiserRequest build() {
        return AdvertiserRequest.builder()
                .title(title)
                .description(description)
                .location(location)
                .pictureUrl(pictureUrl)
                .build();
    }
}
