package com.advertisement.advertisementsystem.mapper;

import com.advertisement.advertisementsystem.model.dto.request.LanguageRequest;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.entity.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LanguageMapper {

    @Mapping(target = "id", ignore = true)
    Language toLanguage(LanguageRequest languageRequest);

    LanguageResponse toLanguageResponse(Language language);
}
