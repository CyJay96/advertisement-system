package com.advertisement.advertisementsystem.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchUtil {

    public static <T> Specification<T> append(Specification<T> base, Specification<T> specification) {
        if (Objects.isNull(base)) {
            return Specification.where(specification);
        }
        return base.and(specification);
    }
}
