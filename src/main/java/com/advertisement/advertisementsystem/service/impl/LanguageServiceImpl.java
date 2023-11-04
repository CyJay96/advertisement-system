package com.advertisement.advertisementsystem.service.impl;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.LanguageMapper;
import com.advertisement.advertisementsystem.model.dto.request.LanguageRequest;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Language;
import com.advertisement.advertisementsystem.repository.LanguageRepository;
import com.advertisement.advertisementsystem.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    public LanguageResponse save(LanguageRequest languageRequest) {
        Language language = languageMapper.toLanguage(languageRequest);
        return languageMapper.toLanguageResponse(languageRepository.save(language));
    }

    @Override
    public PageResponse<LanguageResponse> findAll(Pageable pageable) {
        Page<Language> languagePage = languageRepository.findAll(pageable);

        List<LanguageResponse> languageResponses = languagePage.stream()
                .map(languageMapper::toLanguageResponse)
                .toList();

        return PageResponse.<LanguageResponse>builder()
                .content(languageResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(languageResponses.size())
                .build();
    }

    @Override
    public LanguageResponse findById(Long id) {
        return languageRepository.findById(id)
                .map(languageMapper::toLanguageResponse)
                .orElseThrow(() -> new EntityNotFoundException(Language.class, id));
    }

    @Override
    public LanguageResponse update(Long id, LanguageRequest languageRequest) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Language.class, id));
        languageMapper.updateLanguage(languageRequest, language);
        return languageMapper.toLanguageResponse(languageRepository.save(language));
    }

    @Override
    public void deleteById(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new EntityNotFoundException(Language.class, id);
        }
        languageRepository.deleteById(id);
    }
}
