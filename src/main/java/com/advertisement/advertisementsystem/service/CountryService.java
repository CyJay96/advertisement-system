package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.dto.request.CountryRequest;
import com.advertisement.advertisementsystem.model.dto.response.CountryResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CountryService {

    CountryResponse save(CountryRequest countryRequest);

    PageResponse<CountryResponse> findAll(Pageable pageable);

    CountryResponse findById(Long id);

    CountryResponse update(Long id, CountryRequest countryRequest);

    CountryResponse restoreById(Long id);

    CountryResponse deleteById(Long id);
}
