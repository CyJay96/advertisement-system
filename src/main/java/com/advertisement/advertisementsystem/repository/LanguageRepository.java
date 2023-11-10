package com.advertisement.advertisementsystem.repository;

import com.advertisement.advertisementsystem.model.entity.Language;
import com.advertisement.advertisementsystem.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Page<Language> findAllByStatus(Status status, Pageable pageable);
}
