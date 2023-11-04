package com.advertisement.advertisementsystem.service.searcher;

import com.advertisement.advertisementsystem.model.criteria.AdvertiserCriteria;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.specification.AdvertiserSpecification;
import com.advertisement.advertisementsystem.repository.AdvertiserRepository;
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
public class AdvertiserSearcher {

    private static final String ADVERTISER_ID_FIELD = "id";

    private final AdvertiserRepository advertiserRepository;

    private final Function<AdvertiserCriteria, Specification<Advertiser>> toSpecification =
            searchCriteria -> {
                Specification<Advertiser> specification = null;

                if (Objects.nonNull(searchCriteria.getTitle())) {
                    specification = SearchUtil.append(specification,
                            AdvertiserSpecification.matchTitle(searchCriteria.getTitle()));
                }
                if (Objects.nonNull(searchCriteria.getDescription())) {
                    specification = SearchUtil.append(specification,
                            AdvertiserSpecification.matchDescription(searchCriteria.getDescription()));
                }
                if (Objects.nonNull(searchCriteria.getLocation())) {
                    specification = SearchUtil.append(specification,
                            AdvertiserSpecification.matchLocation(searchCriteria.getLocation()));
                }

                return specification;
            };

    public Page<Advertiser> getAdvertiserByCriteria(AdvertiserCriteria searchCriteria) {
        return toSpecification
                .andThen(specification -> advertiserRepository.findAll(specification, PageRequest.of(searchCriteria.getPage(),
                        searchCriteria.getSize(), Sort.by(Sort.Direction.ASC, ADVERTISER_ID_FIELD))))
                .apply(searchCriteria);
    }
}
