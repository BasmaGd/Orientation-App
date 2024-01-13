package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cours;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CoursRepositoryWithBagRelationshipsImpl implements CoursRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cours> fetchBagRelationships(Optional<Cours> cours) {
        return cours.map(this::fetchFilieres);
    }

    @Override
    public Page<Cours> fetchBagRelationships(Page<Cours> cours) {
        return new PageImpl<>(fetchBagRelationships(cours.getContent()), cours.getPageable(), cours.getTotalElements());
    }

    @Override
    public List<Cours> fetchBagRelationships(List<Cours> cours) {
        return Optional.of(cours).map(this::fetchFilieres).orElse(Collections.emptyList());
    }

    Cours fetchFilieres(Cours result) {
        return entityManager
            .createQuery("select cours from Cours cours left join fetch cours.filieres where cours.id = :id", Cours.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Cours> fetchFilieres(List<Cours> cours) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cours.size()).forEach(index -> order.put(cours.get(index).getId(), index));
        List<Cours> result = entityManager
            .createQuery("select cours from Cours cours left join fetch cours.filieres where cours in :cours", Cours.class)
            .setParameter("cours", cours)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
