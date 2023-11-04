package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.dto.request.LanguageRequest;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface LanguageService {

    LanguageResponse save(LanguageRequest languageRequest);

    PageResponse<LanguageResponse> findAll(Pageable pageable);

    LanguageResponse findById(Long id);

    LanguageResponse update(Long id, LanguageRequest languageRequest);

    void deleteById(Long id);
}
