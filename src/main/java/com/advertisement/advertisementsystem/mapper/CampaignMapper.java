package com.advertisement.advertisementsystem.mapper;

import com.advertisement.advertisementsystem.model.dto.request.CampaignRequest;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.entity.Campaign;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        uses = {CountryMapper.class, LanguageMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CampaignMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "advertiser", ignore = true)
    @Mapping(target = "createDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    Campaign toCampaign(CampaignRequest campaignRequest);

    @Mapping(
            target = "advertiserId",
            expression = "java(java.util.Objects.nonNull(campaign.getAdvertiser()) ? " +
                    "campaign.getAdvertiser().getId() : null)")
    CampaignResponse toCampaignResponse(Campaign campaign);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "advertiser", ignore = true)
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    void updateCampaign(CampaignRequest campaignRequest, @MappingTarget Campaign campaign);
}
