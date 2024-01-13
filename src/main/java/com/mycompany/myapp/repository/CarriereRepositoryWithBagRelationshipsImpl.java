package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Carriere;
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
public class CarriereRepositoryWithBagRelationshipsImpl implements CarriereRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Carriere> fetchBagRelationships(Optional<Carriere> carriere) {
        return carriere.map(this::fetchNomFilieres);
    }

    @Override
    public Page<Carriere> fetchBagRelationships(Page<Carriere> carrieres) {
        return new PageImpl<>(fetchBagRelationships(carrieres.getContent()), carrieres.getPageable(), carrieres.getTotalElements());
    }

    @Override
    public List<Carriere> fetchBagRelationships(List<Carriere> carrieres) {
        return Optional.of(carrieres).map(this::fetchNomFilieres).orElse(Collections.emptyList());
    }

    Carriere fetchNomFilieres(Carriere result) {
        return entityManager
            .createQuery(
                "select carriere from Carriere carriere left join fetch carriere.nomFilieres where carriere.id = :id",
                Carriere.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Carriere> fetchNomFilieres(List<Carriere> carrieres) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, carrieres.size()).forEach(index -> order.put(carrieres.get(index).getId(), index));
        List<Carriere> result = entityManager
            .createQuery(
                "select carriere from Carriere carriere left join fetch carriere.nomFilieres where carriere in :carrieres",
                Carriere.class
            )
            .setParameter("carrieres", carrieres)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
