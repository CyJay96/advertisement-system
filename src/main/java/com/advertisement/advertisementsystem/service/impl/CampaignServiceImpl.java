package com.advertisement.advertisementsystem.service.impl;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.CampaignMapper;
import com.advertisement.advertisementsystem.model.criteria.CampaignCriteria;
import com.advertisement.advertisementsystem.model.dto.request.CampaignRequest;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.entity.Campaign;
import com.advertisement.advertisementsystem.model.entity.Country;
import com.advertisement.advertisementsystem.model.entity.Language;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.AdvertiserRepository;
import com.advertisement.advertisementsystem.repository.CampaignRepository;
import com.advertisement.advertisementsystem.repository.CountryRepository;
import com.advertisement.advertisementsystem.repository.LanguageRepository;
import com.advertisement.advertisementsystem.service.CampaignService;
import com.advertisement.advertisementsystem.service.searcher.CampaignSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignSearcher campaignSearcher;
    private final CampaignRepository campaignRepository;
    private final AdvertiserRepository advertiserRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final CampaignMapper campaignMapper;

    @Override
    public CampaignResponse save(Long advertiserId, CampaignRequest campaignRequest) {
        Advertiser advertiser = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new EntityNotFoundException(Advertiser.class, advertiserId));

        Campaign campaign = campaignMapper.toCampaign(campaignRequest);
        campaign.setAdvertiser(advertiser);

        List<Country> countries = countryRepository.findAllById(campaignRequest.getCountriesIds());
        List<Language> languages = languageRepository.findAllById(campaignRequest.getLanguagesIds());

        campaign.setCountries(countries);
        campaign.setLanguages(languages);

        Campaign savedCampaign = campaignRepository.save(campaign);
        return campaignMapper.toCampaignResponse(savedCampaign);
    }

    @Override
    public PageResponse<CampaignResponse> findAll(Pageable pageable) {
        Page<Campaign> campaignPage = campaignRepository.findAllByStatus(Status.ACTIVE, pageable);

        List<CampaignResponse> campaignResponses = campaignPage.stream()
                .map(campaignMapper::toCampaignResponse)
                .toList();

        return PageResponse.<CampaignResponse>builder()
                .content(campaignResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(campaignResponses.size())
                .build();
    }

    @Override
    public PageResponse<CampaignResponse> findAllByCriteria(CampaignCriteria searchCriteria, Pageable pageable) {
        searchCriteria.setPage(pageable.getPageNumber());
        searchCriteria.setSize(pageable.getPageSize());

        Page<Campaign> campaignPage = campaignSearcher.getCampaignByCriteria(searchCriteria);

        List<CampaignResponse> campaignResponses = campaignPage.stream()
                .map(campaignMapper::toCampaignResponse)
                .toList();

        return PageResponse.<CampaignResponse>builder()
                .content(campaignResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(campaignResponses.size())
                .build();
    }

    @Override
    public CampaignResponse findById(Long id) {
        return campaignRepository.findById(id)
                .map(campaignMapper::toCampaignResponse)
                .orElseThrow(() -> new EntityNotFoundException(Campaign.class, id));
    }

    @Override
    public CampaignResponse update(Long id, CampaignRequest campaignRequest) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Campaign.class, id));
        campaignMapper.updateCampaign(campaignRequest, campaign);

        if (Objects.nonNull(campaignRequest.getCountriesIds())) {
            List<Country> countries = countryRepository.findAllById(campaignRequest.getCountriesIds());
            campaign.setCountries(countries);
        }
        if (Objects.nonNull(campaignRequest.getLanguagesIds())) {
            List<Language> languages = languageRepository.findAllById(campaignRequest.getLanguagesIds());
            campaign.setLanguages(languages);
        }

        return campaignMapper.toCampaignResponse(campaignRepository.save(campaign));
    }

    @Override
    public CampaignResponse restoreById(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Campaign.class, id));
        campaign.setStatus(Status.ACTIVE);
        return campaignMapper.toCampaignResponse(campaignRepository.save(campaign));
    }

    @Override
    public CampaignResponse deleteById(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Campaign.class, id));
        campaign.setStatus(Status.DELETED);
        return campaignMapper.toCampaignResponse(campaignRepository.save(campaign));
    }
}
