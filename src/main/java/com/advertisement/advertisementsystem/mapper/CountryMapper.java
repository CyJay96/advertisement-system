package com.advertisement.advertisementsystem.mapper;

import com.advertisement.advertisementsystem.model.dto.request.CountryRequest;
import com.advertisement.advertisementsystem.model.dto.response.CountryResponse;
import com.advertisement.advertisementsystem.model.entity.Country;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface CountryMapper {

    @Mapping(target = "id", ignore = true)
    Country toCountry(CountryRequest countryRequest);

    CountryResponse toCountryResponse(Country country);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    void updateCountry(CountryRequest countryRequest, @MappingTarget Country country);
}
