package com.advertisement.advertisementsystem.repository;

import com.advertisement.advertisementsystem.model.entity.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long>, JpaSpecificationExecutor<Advertiser> {
}
