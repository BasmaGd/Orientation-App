package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Filiere;
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
public class FiliereRepositoryWithBagRelationshipsImpl implements FiliereRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Filiere> fetchBagRelationships(Optional<Filiere> filiere) {
        return filiere.map(this::fetchNomCours);
    }

    @Override
    public Page<Filiere> fetchBagRelationships(Page<Filiere> filieres) {
        return new PageImpl<>(fetchBagRelationships(filieres.getContent()), filieres.getPageable(), filieres.getTotalElements());
    }

    @Override
    public List<Filiere> fetchBagRelationships(List<Filiere> filieres) {
        return Optional.of(filieres).map(this::fetchNomCours).orElse(Collections.emptyList());
    }

    Filiere fetchNomCours(Filiere result) {
        return entityManager
            .createQuery("select filiere from Filiere filiere left join fetch filiere.nomCours where filiere.id = :id", Filiere.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Filiere> fetchNomCours(List<Filiere> filieres) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, filieres.size()).forEach(index -> order.put(filieres.get(index).getId(), index));
        List<Filiere> result = entityManager
            .createQuery("select filiere from Filiere filiere left join fetch filiere.nomCours where filiere in :filieres", Filiere.class)
            .setParameter("filieres", filieres)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.