package com.example.shopdemo.spec;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.enumtype.ProductOrderingStrategy;
import com.example.shopdemo.pojo.ProductSpecs;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public class ProductSpecifications {
    public static Specification<Product> hasNameContaining(String keyword) {
        if (keyword == null || keyword.trim().equals("")) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
    }

    public static Specification<Product> hasDescContaining(String keyword) {
        if (keyword == null || keyword.trim().equals("")) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
    }

    public static Specification<Product> matchesKeyword(String keyword) {
        if (keyword == null || keyword.trim().equals("")) return Specification.where(null);
        return hasNameContaining(keyword).or(hasDescContaining(keyword));
    }

    public static Specification<Product> hasBrand(String brandName) {
        if (brandName == null)  return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("name"), brandName);
    }

    public static Specification<Product> inCategory(String categoryName) {
        if (categoryName == null) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("name"), categoryName);
    }

    public static Specification<Product> hasPriceInRange(Double min, Double max) {
        if (max == null && min == null) return Specification.where(null);
        final Double minPrice = (min == null) ? 0.0 : min;
        final Double maxPrice = (max == null) ? Double.MAX_VALUE : max;

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice),
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice)
            );
        };
    }

    public static Specification<Product> sortedBy(ProductOrderingStrategy orderType) {
        if (orderType == null) return Specification.where(null);

        return (root, query, criteriaBuilder) -> {
            Expression<Object> field;
            switch (orderType) {
                case BY_NAME_ASC:
                case BY_NAME_DESC:
                    field = root.get("name");
                    break;
                case BY_PRICE_ASC:
                case BY_PRICE_DESC:
                    field = root.get("price");
                    break;
                case BY_RATING_ASC:
                case BY_RATING_DESC:
                    field = root.get("rating");
                    break;
                default:
                    return null;
            }

            query.orderBy(orderType.isAscending() ? criteriaBuilder.asc(field) : criteriaBuilder.desc(field));

            return null;
        };
    }

    public static Specification<Product> matchesSpec(ProductSpecs specs) {
        if (specs == null) return null;
        return matchesKeyword(specs.getKeyword())
                .and(inCategory(specs.getCategory()))
                .and(hasBrand(specs.getBrand()))
                .and(hasPriceInRange(specs.getMinPrice(), specs.getMaxPrice()))
                .and(sortedBy(specs.getSortedBy()));
    }
}
