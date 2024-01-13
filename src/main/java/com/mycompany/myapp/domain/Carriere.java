package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Carriere.
 */
@Entity
@Table(name = "carriere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Carriere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_carriere")
    private String nomCarriere;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_carriere__filiere",
        joinColumns = @JoinColumn(name = "carriere_id"),
        inverseJoinColumns = @JoinColumn(name = "filiere_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCours", "nomFilieres" }, allowSetters = true)
    private Set<Filiere> filieres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_carriere__cours",
        joinColumns = @JoinColumn(name = "carriere_id"),
        inverseJoinColumns = @JoinColumn(name = "cours_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filieres", "nomCarrieres" }, allowSetters = true)
    private Set<Cours> cours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Carriere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCarriere() {
        return this.nomCarriere;
    }

    public Carriere nomCarriere(String nomCarriere) {
        this.setNomCarriere(nomCarriere);
        return this;
    }

    public void setNomCarriere(String nomCarriere) {
        this.nomCarriere = nomCarriere;
    }

    public String getDescription() {
        return this.description;
    }

    public Carriere description(String description) {
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

    public Carriere filieres(Set<Filiere> filieres) {
        this.setFilieres(filieres);
        return this;
    }

    public Carriere addFiliere(Filiere filiere) {
        this.filieres.add(filiere);
        return this;
    }

    public Carriere removeFiliere(Filiere filiere) {
        this.filieres.remove(filiere);
        return this;
    }

    public Set<Cours> getCours() {
        return this.cours;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }

    public Carriere cours(Set<Cours> cours) {
        this.setCours(cours);
        return this;
    }

    public Carriere addCours(Cours cours) {
        this.cours.add(cours);
        return this;
    }

    public Carriere removeCours(Cours cours) {
        this.cours.remove(cours);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carriere)) {
            return false;
        }
        return getId() != null && getId().equals(((Carriere) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carriere{" +
            "id=" + getId() +
            ", nomCarriere='" + getNomCarriere() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
