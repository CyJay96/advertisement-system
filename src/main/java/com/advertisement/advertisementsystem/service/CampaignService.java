package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.criteria.CampaignCriteria;
import com.advertisement.advertisementsystem.model.dto.request.CampaignRequest;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CampaignService {

    CampaignResponse save(Long advertiserId, CampaignRequest campaignRequest);

    PageResponse<CampaignResponse> findAll(Pageable pageable);

    PageResponse<CampaignResponse> findAllByCriteria(CampaignCriteria searchCriteria, Pageable pageable);

    CampaignResponse findById(Long id);

    CampaignResponse update(Long id, CampaignRequest campaignRequest);

    CampaignResponse restoreById(Long id);

    CampaignResponse deleteById(Long id);
}
