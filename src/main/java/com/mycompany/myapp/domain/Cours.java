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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_cours__filiere",
        joinColumns = @JoinColumn(name = "cours_id"),
        inverseJoinColumns = @JoinColumn(name = "filiere_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCours", "nomFilieres" }, allowSetters = true)
    private Set<Filiere> filieres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filieres", "cours" }, allowSetters = true)
    private Set<Carriere> nomCarrieres = new HashSet<>();

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

    public Set<Filiere> getFilieres() {
        return this.filieres;
    }

    public void setFilieres(Set<Filiere> filieres) {
        this.filieres = filieres;
    }

    public Cours filieres(Set<Filiere> filieres) {
        this.setFilieres(filieres);
        return this;
    }

    public Cours addFiliere(Filiere filiere) {
        this.filieres.add(filiere);
        return this;
    }

    public Cours removeFiliere(Filiere filiere) {
        this.filieres.remove(filiere);
        return this;
    }

    public Set<Carriere> getNomCarrieres() {
        return this.nomCarrieres;
    }

    public void setNomCarrieres(Set<Carriere> carrieres) {
        if (this.nomCarrieres != null) {
            this.nomCarrieres.forEach(i -> i.removeCours(this));
        }
        if (carrieres != null) {
            carrieres.forEach(i -> i.addCours(this));
        }
        this.nomCarrieres = carrieres;
    }

    public Cours nomCarrieres(Set<Carriere> carrieres) {
        this.setNomCarrieres(carrieres);
        return this;
    }

    public Cours addNomCarriere(Carriere carriere) {
        this.nomCarrieres.add(carriere);
        carriere.getCours().add(this);
        return this;
    }

    public Cours removeNomCarriere(Carriere carriere) {
        this.nomCarrieres.remove(carriere);
        carriere.getCours().remove(this);
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
            ", nomCours='" + getNomCours() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
