package com.example.demo.repo;

import com.example.demo.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Item> searchByFilters(Map<String, Object> filters, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // main query
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);

        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((field, value) -> {
            if (value != null) {
                if (value instanceof String) {
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
                } else {
                    predicates.add(cb.equal(root.get(field), value));
                }
            }
        });

        cq.where(predicates.toArray(new Predicate[0]));

        // execute query
        List<Item> resultList = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Item> countRoot = countQuery.from(Item.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }
}
