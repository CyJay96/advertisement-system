package com.advertisement.advertisementsystem.service.impl;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.AdvertiserMapper;
import com.advertisement.advertisementsystem.mapper.CampaignMapper;
import com.advertisement.advertisementsystem.model.criteria.AdvertiserCriteria;
import com.advertisement.advertisementsystem.model.dto.request.AdvertiserRequest;
import com.advertisement.advertisementsystem.model.dto.response.AdvertiserResponse;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.repository.AdvertiserRepository;
import com.advertisement.advertisementsystem.repository.CampaignRepository;
import com.advertisement.advertisementsystem.service.AdvertiserService;
import com.advertisement.advertisementsystem.service.searcher.AdvertiserSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertiserServiceImpl implements AdvertiserService {

    private final AdvertiserSearcher advertiserSearcher;
    private final AdvertiserRepository advertiserRepository;
    private final CampaignRepository campaignRepository;
    private final AdvertiserMapper advertiserMapper;
    private final CampaignMapper campaignMapper;

    @Override
    public AdvertiserResponse save(AdvertiserRequest advertiserRequest) {
        Advertiser advertiser = advertiserMapper.toAdvertiser(advertiserRequest);
        return advertiserMapper.toAdvertiserResponse(advertiserRepository.save(advertiser));
    }

    @Override
    public PageResponse<AdvertiserResponse> findAll(Pageable pageable) {
        Page<Advertiser> advertiserPage = advertiserRepository.findAll(pageable);

        List<AdvertiserResponse> advertiserResponses = advertiserPage.stream()
                .map(advertiserMapper::toAdvertiserResponse)
                .toList();

        return PageResponse.<AdvertiserResponse>builder()
                .content(advertiserResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(advertiserResponses.size())
                .build();
    }

    @Override
    public PageResponse<AdvertiserResponse> findAllByCriteria(AdvertiserCriteria searchCriteria, Pageable pageable) {
        searchCriteria.setPage(pageable.getPageNumber());
        searchCriteria.setSize(pageable.getPageSize());

        Page<Advertiser> advertiserPage = advertiserSearcher.getAdvertiserByCriteria(searchCriteria);

        List<AdvertiserResponse> advertiserResponses = advertiserPage.stream()
                .map(advertiserMapper::toAdvertiserResponse)
                .toList();

        return PageResponse.<AdvertiserResponse>builder()
                .content(advertiserResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(advertiserResponses.size())
                .build();
    }

    @Override
    public AdvertiserResponse findById(Long id, Pageable pageable) {
        AdvertiserResponse advertiserResponse = advertiserRepository.findById(id)
                .map(advertiserMapper::toAdvertiserResponse)
                .orElseThrow(() -> new EntityNotFoundException(Advertiser.class, id));

        List<CampaignResponse> campaignResponses = campaignRepository.findAllByAdvertiserId(id, pageable)
                .stream()
                .map(campaignMapper::toCampaignResponse)
                .toList();

        PageResponse<CampaignResponse> campaigns = PageResponse.<CampaignResponse>builder()
                .content(campaignResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(campaignResponses.size())
                .build();

        advertiserResponse.setCampaigns(campaigns);

        return advertiserResponse;
    }

    @Override
    public AdvertiserResponse update(Long id, AdvertiserRequest advertiserRequest) {
        Advertiser advertiser = advertiserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Advertiser.class, id));
        advertiserMapper.updateAdvertiser(advertiserRequest, advertiser);
        return advertiserMapper.toAdvertiserResponse(advertiserRepository.save(advertiser));
    }

    @Override
    public void deleteById(Long id) {
        if (!advertiserRepository.existsById(id)) {
            throw new EntityNotFoundException(Advertiser.class, id);
        }
        advertiserRepository.deleteById(id);
    }
}
