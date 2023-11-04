package com.advertisement.advertisementsystem.repository;

import com.advertisement.advertisementsystem.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
