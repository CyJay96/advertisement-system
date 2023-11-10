package com.advertisement.advertisementsystem.builder.criteria;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.criteria.CampaignCriteria;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE_SIZE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCampaignCriteria")
public class CampaignCriteriaTestBuilder implements TestBuilder<CampaignCriteria> {

    private String title = TEST_STRING;

    private String description = TEST_STRING;

    private String location = TEST_STRING;

    private Integer page = TEST_PAGE;

    private Integer size = TEST_PAGE_SIZE;

    @Override
    public CampaignCriteria build() {
        return CampaignCriteria.builder()
                .title(title)
                .description(description)
                .location(location)
                .page(page)
                .size(size)
                .build();
    }
}
