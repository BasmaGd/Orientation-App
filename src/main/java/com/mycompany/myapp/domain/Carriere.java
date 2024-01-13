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
        name = "rel_carriere__nom_filiere",
        joinColumns = @JoinColumn(name = "carriere_id"),
        inverseJoinColumns = @JoinColumn(name = "nom_filiere_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCours", "nomCarrieres" }, allowSetters = true)
    private Set<Filiere> nomFilieres = new HashSet<>();

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

    public Set<Filiere> getNomFilieres() {
        return this.nomFilieres;
    }

    public void setNomFilieres(Set<Filiere> filieres) {
        this.nomFilieres = filieres;
    }

    public Carriere nomFilieres(Set<Filiere> filieres) {
        this.setNomFilieres(filieres);
        return this;
    }

    public Carriere addNomFiliere(Filiere filiere) {
        this.nomFilieres.add(filiere);
        return this;
    }

    public Carriere removeNomFiliere(Filiere filiere) {
        this.nomFilieres.remove(filiere);
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
         