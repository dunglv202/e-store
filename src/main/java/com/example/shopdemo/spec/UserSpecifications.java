package com.example.shopdemo.spec;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.pojo.UserSpecs;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;


public class UserSpecifications {
    public static Specification<User> matchesKeyword(String keyword) {
        if (keyword == null || keyword.trim().equals("")) return Specification.where(null);
        return (root, query, criteriaBuilder) -> {
            Expression<String> fullName = criteriaBuilder.concat(criteriaBuilder.concat(root.get("details").get("firstName"), " "), root.get("details").get("lastName"));
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("username"), "%" + keyword + "%"),
                criteriaBuilder.like(fullName, "%" + keyword + "%")
            );
        };
    }

    public static Specification<User> hasAuthority(Authority authority) {
        if (authority == null) return Specification.where(null);
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(authority, root.get("authorities"));
    }
}
