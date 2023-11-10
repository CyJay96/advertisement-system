package com.advertisement.advertisementsystem.service.searcher;

import com.advertisement.advertisementsystem.model.criteria.CampaignCriteria;
import com.advertisement.advertisementsystem.model.entity.Campaign;
import com.advertisement.advertisementsystem.model.specification.CampaignSpecification;
import com.advertisement.advertisementsystem.repository.CampaignRepository;
import com.advertisement.advertisementsystem.util.SearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignSearcher {

    private static final String ADVERTISER_ID_FIELD = "id";

    private final CampaignRepository campaignRepository;

    private final Function<CampaignCriteria, Specification<Campaign>> toSpecification =
            searchCriteria -> {
                Specification<Campaign> specification = null;

                if (Objects.nonNull(searchCriteria.getTitle())) {
                    specification = SearchUtil.append(specification,
                            CampaignSpecification.matchTitle(searchCriteria.getTitle()));
                }
                if (Objects.nonNull(searchCriteria.getDescription())) {
                    specification = SearchUtil.append(specification,
                            CampaignSpecification.matchDescription(searchCriteria.getDescription()));
                }
                if (Objects.nonNull(searchCriteria.getLocation())) {
                    specification = SearchUtil.append(specification,
                            CampaignSpecification.matchLocation(searchCriteria.getLocation()));
                }

                return specification;
            };

    public Page<Campaign> getCampaignByCriteria(CampaignCriteria searchCriteria) {
        return toSpecification
                .andThen(specification -> campaignRepository.findAll(specification, PageRequest.of(searchCriteria.getPage(),
                        searchCriteria.getSize(), Sort.by(Sort.Direction.ASC, ADVERTISER_ID_FIELD))))
                .apply(searchCriteria);
    }
}
