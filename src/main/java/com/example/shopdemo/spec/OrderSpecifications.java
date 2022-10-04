package com.example.shopdemo.spec;

import com.example.shopdemo.entity.Order;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.enumtype.OrderOrderingStrategy;
import com.example.shopdemo.enumtype.OrderStatus;
import com.example.shopdemo.pojo.OrderSpecs;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import java.time.LocalDateTime;

public class OrderSpecifications {
    public static Specification<Order> sortedBy(OrderOrderingStrategy strategy) {
        if (strategy == null) return Specification.where(null);

        return (root, query, criteriaBuilder) -> {
            Expression<Order> expression;
            switch (strategy) {
                case BY_NEWEST:
                case BY_OLDEST:
                    expression = root.get("dateCreated");
                    break;
                default:
                    return null;
            }
            query.orderBy(strategy.isAscending() ? criteriaBuilder.asc(expression) : criteriaBuilder.desc(expression));
            return null;
        };
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        if (status == null) return Specification.where(null);
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Order> from(LocalDateTime from) {
        if (from == null) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreated"), from);
    }

    public static Specification<Order> to(LocalDateTime to) {
        if (to == null) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateCreated"), to);
    }

    public static Specification<Order> orderedBetween(LocalDateTime from, LocalDateTime to) {
        return from(from).and(to(to));
    }

    public static Specification<Order> matchesSpec(OrderSpecs specs) {
        return hasStatus(specs.getStatus())
                .and(orderedBetween(specs.getFrom(), specs.getTo()))
                .and(sortedBy(specs.getSortedBy()));
    }

    public static Specification<Order> ofUser(User user) {
        if (user == null) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }
}
