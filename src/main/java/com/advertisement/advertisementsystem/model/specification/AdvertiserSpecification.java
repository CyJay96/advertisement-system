package com.advertisement.advertisementsystem.model.specification;

import com.advertisement.advertisementsystem.model.entity.Advertiser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertiserSpecification {

    public static Specification<Advertiser> matchTitle(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
    }

    public static Specification<Advertiser> matchDescription(String description) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
    }

    public static Specification<Advertiser> matchLocation(String location) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
    }
}
