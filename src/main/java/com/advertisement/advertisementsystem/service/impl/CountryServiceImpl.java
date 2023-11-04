package com.advertisement.advertisementsystem.service.impl;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.CountryMapper;
import com.advertisement.advertisementsystem.model.dto.request.CountryRequest;
import com.advertisement.advertisementsystem.model.dto.response.CountryResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Country;
import com.advertisement.advertisementsystem.repository.CountryRepository;
import com.advertisement.advertisementsystem.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public CountryResponse save(CountryRequest countryRequest) {
        Country country = countryMapper.toCountry(countryRequest);
        return countryMapper.toCountryResponse(countryRepository.save(country));
    }

    @Override
    public PageResponse<CountryResponse> findAll(Pageable pageable) {
        Page<Country> countryPage = countryRepository.findAll(pageable);

        List<CountryResponse> countryResponses = countryPage.stream()
                .map(countryMapper::toCountryResponse)
                .toList();

        return PageResponse.<CountryResponse>builder()
                .content(countryResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(countryResponses.size())
                .build();
    }

    @Override
    public CountryResponse findById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toCountryResponse)
                .orElseThrow(() -> new EntityNotFoundException(Country.class, id));
    }

    @Override
    public CountryResponse update(Long id, CountryRequest countryRequest) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Country.class, id));
        countryMapper.updateCountry(countryRequest, country);
        return countryMapper.toCountryResponse(countryRepository.save(country));
    }

    @Override
    public void deleteById(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException(Country.class, id);
        }
        countryRepository.deleteById(id);
    }
}
