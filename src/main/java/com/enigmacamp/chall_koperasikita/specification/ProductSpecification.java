package com.enigmacamp.chall_koperasikita.specification;

import com.enigmacamp.chall_koperasikita.dto.request.SearchProductRequest;
import com.enigmacamp.chall_koperasikita.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> getSpecification(SearchProductRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // search by name
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + request.getName().toLowerCase() + "%"));
            }
            // search by category
            if (request.getCategory() != null && !request.getCategory().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("category")),
                                "%" + request.getCategory().toLowerCase() + "%"));
            }
            // search by minPrice
            if (request.getMinPrice() != null && !(request.getMinPrice().compareTo(BigDecimal.ZERO)<0)) {
                BigDecimal searchMin = request.getMinPrice();
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("price"),
                                searchMin));
            }
            // search by maxPrice
            if (request.getMaxPrice() != null && !(request.getMaxPrice().compareTo(BigDecimal.ZERO)<0)) {
                BigDecimal searchMin = request.getMaxPrice();
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("price"),
                                searchMin));
            }
            // search by minStock
            if (request.getMinStock() != null && !(request.getMinStock()<0)) {
                Integer searchMin = request.getMinStock();
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("stock"),
                                searchMin));
            }
            // search by isAvailable
            if (request.getIsAvailable() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("isAvailable"),
                                request.getIsAvailable()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
