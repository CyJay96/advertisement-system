package com.advertisement.advertisementsystem.repository;

import com.advertisement.advertisementsystem.model.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CampaignRepository extends JpaRepository<Campaign, Long>, JpaSpecificationExecutor<Campaign> {

    Page<Campaign> findAllByAdvertiserId(Long advertiserId, Pageable pageable);
}
