package com.wcd.boardservice.specs;

import com.wcd.boardservice.entity.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostSpecs {
    public enum SearchKey {
        TITLE("title"),
        CONTENT("content"),
        CATEGORY("category"),
        CLUB_ID("clubId"),
        WRITER_ID("writerId");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Specification<Post> searchWith(Map<SearchKey, Object> searchKeyword) {
        return (Specification<Post>) ((root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, Object> searchKeyword, Root<Post> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();

        if(searchKeyword == null || searchKeyword.isEmpty()) {
            return predicate;
        }

        for (SearchKey key : searchKeyword.keySet()) {
            switch (key) {
                case TITLE:
                case CONTENT:
                    predicate.add(builder.like(
                            root.get(key.value), "%" + searchKeyword.get(key).toString() + "%"
                    ));
                    break;
                case CLUB_ID:
                case WRITER_ID:
                    predicate.add(builder.equal(
                            root.get(key.value),Long.parseLong(searchKeyword.get(key).toString())
                    ));
                    break;
                case CATEGORY:
                    predicate.add(builder.equal(
                            root.get(key.value),searchKeyword.get(key)
                    ));
                    break;
            }
        }
        return predicate;
    }
}
