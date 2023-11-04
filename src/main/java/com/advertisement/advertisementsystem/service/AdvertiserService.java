package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.criteria.AdvertiserCriteria;
import com.advertisement.advertisementsystem.model.dto.request.AdvertiserRequest;
import com.advertisement.advertisementsystem.model.dto.response.AdvertiserResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface AdvertiserService {

    AdvertiserResponse save(AdvertiserRequest advertiserRequest);

    PageResponse<AdvertiserResponse> findAll(Pageable pageable);

    PageResponse<AdvertiserResponse> findAllByCriteria(AdvertiserCriteria searchCriteria, Pageable pageable);

    AdvertiserResponse findById(Long id, Pageable pageable);

    AdvertiserResponse update(Long id, AdvertiserRequest advertiserRequest);

    void deleteById(Long id);
}
