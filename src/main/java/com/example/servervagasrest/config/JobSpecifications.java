package com.example.servervagasrest.config;

import com.example.servervagasrest.model.Area;
import com.example.servervagasrest.model.Job;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Path;

public class JobSpecifications {

    public static Specification<Job> hasTitleContaining(String title) {
        return (root, query, builder) -> {
            if (title == null || title.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(
                    builder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Job> hasAreaContaining(String area) {
        return (root, query, builder) -> {
            if (area == null || area.isBlank()) {
                return builder.conjunction();
            }
            try {
                Area areaEnum = Area.fromName(area);
                return builder.equal(root.get("area"), areaEnum);
            } catch (IllegalArgumentException e) {
                return builder.disjunction();
            }
        };
    }

    public static Specification<Job> hasCompanyNameContaining(String companyName) {
        return (root, query, builder) -> {
            if (companyName == null || companyName.isBlank()) {
                return builder.conjunction();
            }
            return builder.conjunction();
        };
    }

    public static Specification<Job> hasState(String state) {
        return (root, query, builder) -> {
            if (state == null || state.isBlank()) {
                return builder.conjunction();
            }
            return builder.equal(builder.lower(root.get("state")), state.toLowerCase());
        };
    }

    public static Specification<Job> hasCity(String city) {
        return (root, query, builder) -> {
            if (city == null || city.isBlank()) {
                return builder.conjunction();
            }
            return builder.equal(builder.lower(root.get("city")), city.toLowerCase());
        };
    }

    public static Specification<Job> isSalaryBetween(Float min, Float max) {
        return (root, query, builder) -> {
            boolean hasMin = min != null && min > 0;
            boolean hasMax = max != null && max > 0;

            if (!hasMin && !hasMax) {
                return builder.conjunction();
            }

            Path<Float> salaryPath = root.get("salary");

            if (hasMin && hasMax) {
                return builder.between(salaryPath, min, max);
            } else if (hasMin) {
                return builder.greaterThanOrEqualTo(salaryPath, min);
            } else {
                return builder.lessThanOrEqualTo(salaryPath, max);
            }
        };
    }
}