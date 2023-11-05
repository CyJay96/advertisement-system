package com.advertisement.advertisementsystem.repository;

import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long>, JpaSpecificationExecutor<Advertiser> {

    Page<Advertiser> findAllByStatus(Status status, Pageable pageable);
}
