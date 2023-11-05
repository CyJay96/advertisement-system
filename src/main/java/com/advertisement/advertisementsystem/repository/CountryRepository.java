package com.advertisement.advertisementsystem.repository;

import com.advertisement.advertisementsystem.model.entity.Country;
import com.advertisement.advertisementsystem.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Page<Country> findAllByStatus(Status status, Pageable pageable);
}
