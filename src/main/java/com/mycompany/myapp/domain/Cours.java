package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_cours")
    private String nomCours;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "nomCours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCours", "nomCarrieres" }, allowSetters = true)
    private Set<Filiere> nomFilieres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCours() {
        return this.nomCours;
    }

    public Cours nomCours(String nomCours) {
        this.setNomCours(nomCours);
        return this;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getDescription() {
        return this.description;
    }

    public Cours description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Filiere> getNomFilieres() {
        return this.nomFilieres;
    }

    public void setNomFilieres(Set<Filiere> filieres) {
        if (this.nomFilieres != null) {
            this.nomFilieres.forEach(i -> i.removeNomCours(this));
        }
        if (filieres != null) {
            filieres.forEach(i -> i.addNomCours(this));
        }
        this.nomFilieres = filieres;
    }

    public Cours nomFilieres(Set<Filiere> filieres) {
        this.setNomFilieres(filieres);
        return this;
    }

    public Cours addNomFiliere(Filiere filiere) {
        this.nomFilieres.add(filiere);
        filiere.getNomCours().add(this);
        return this;
    }

    public Cours removeNomFiliere(Filiere filiere) {
        this.nomFilieres.remove(filiere);
        filiere.getNomCours().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cours)) {
            return false;
        }
        return getId() != null && getId().equals(((Cours) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
