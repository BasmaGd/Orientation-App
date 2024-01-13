package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Filiere.
 */
@Entity
@Table(name = "filiere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_filiere")
    private String nomFiliere;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_filiere__nom_cours",
        joinColumns = @JoinColumn(name = "filiere_id"),
        inverseJoinColumns = @JoinColumn(name = "nom_cours_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomFilieres" }, allowSetters = true)
    private Set<Cours> nomCours = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "nomFilieres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomFilieres" }, allowSetters = true)
    private Set<Carriere> nomCarrieres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Filiere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFiliere() {
        return this.nomFiliere;
    }

    public Filiere nomFiliere(String nomFiliere) {
        this.setNomFiliere(nomFiliere);
        return this;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public String getDescription() {
        return this.description;
    }

    public Filiere description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Cours> getNomCours() {
        return this.nomCours;
    }

    public void setNomCours(Set<Cours> cours) {
        this.nomCours = cours;
    }

    public Filiere nomCours(Set<Cours> cours) {
        this.setNomCours(cours);
        return this;
    }

    public Filiere addNomCours(Cours cours) {
        this.nomCours.add(cours);
        return this;
    }

    public Filiere removeNomCours(Cours cours) {
        this.nomCours.remove(cours);
        return this;
    }

    public Set<Carriere> getNomCarrieres() {
        return this.nomCarrieres;
    }

    public void setNomCarrieres(Set<Carriere> carrieres) {
        if (this.nomCarrieres != null) {
            this.nomCarrieres.forEach(i -> i.removeNomFiliere(this));
        }
        if (carrieres != null) {
            carrieres.forEach(i -> i.addNomFiliere(this));
        }
        this.nomCarrieres = carrieres;
    }

    public Filiere nomCarrieres(Set<Carriere> carrieres) {
        this.setNomCarrieres(carrieres);
        return this;
    }

    public Filiere addNomCarriere(Carriere carriere) {
        this.nomCarrieres.add(carriere);
        carriere.getNomFilieres().add(this);
        return this;
    }

    public Filiere removeNomCarriere(Carriere carriere) {
        this.nomCarrieres.remove(carriere);
        carriere.getNomFilieres().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filiere)) {
            return false;
        }
        return getId() != null && getId().equals(((Filiere) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filiere{" +
  