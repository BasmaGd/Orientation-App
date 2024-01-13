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

    @Lob
    @Column(name = "image_filiere")
    private byte[] imageFiliere;

    @Column(name = "image_filiere_content_type")
    private String imageFiliereContentType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "filieres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filieres", "nomCarrieres" }, allowSetters = true)
    private Set<Cours> nomCours = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "filieres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filieres", "cours" }, allowSetters = true)
    private Set<Carriere> nomFilieres = new HashSet<>();

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

    public byte[] getImageFiliere() {
        return this.imageFiliere;
    }

    public Filiere imageFiliere(byte[] imageFiliere) {
        this.setImageFiliere(imageFiliere);
        return this;
    }

    public void setImageFiliere(byte[] imageFiliere) {
        this.imageFiliere = imageFiliere;
    }

    public String getImageFiliereContentType() {
        return this.imageFiliereContentType;
    }

    public Filiere imageFiliereContentType(String imageFiliereContentType) {
        this.imageFiliereContentType = imageFiliereContentType;
        return this;
    }

    public void setImageFiliereContentType(String imageFiliereContentType) {
        this.imageFiliereContentType = imageFiliereContentType;
    }

    public Set<Cours> getNomCours() {
        return this.nomCours;
    }

    public void setNomCours(Set<Cours> cours) {
        if (this.nomCours != null) {
            this.nomCours.forEach(i -> i.removeFiliere(this));
        }
        if (cours != null) {
            cours.forEach(i -> i.addFiliere(this));
        }
        this.nomCours = cours;
    }

    public Filiere nomCours(Set<Cours> cours) {
        this.setNomCours(cours);
        return this;
    }

    public Filiere addNomCours(Cours cours) {
        this.nomCours.add(cours);
        cours.getFilieres().add(this);
        return this;
    }

    public Filiere removeNomCours(Cours cours) {
        this.nomCours.remove(cours);
        cours.getFilieres().remove(this);
        return this;
    }

    public Set<Carriere> getNomFilieres() {
        return this.nomFilieres;
    }

    public void setNomFilieres(Set<Carriere> carrieres) {
        if (this.nomFilieres != null) {
            this.nomFilieres.forEach(i -> i.removeFiliere(this));
        }
        if (carrieres != null) {
            carrieres.forEach(i -> i.addFiliere(this));
        }
        this.nomFilieres = carrieres;
    }

    public Filiere nomFilieres(Set<Carriere> carrieres) {
        this.setNomFilieres(carrieres);
        return this;
    }

    public Filiere addNomFiliere(Carriere carriere) {
        this.nomFilieres.add(carriere);
        carriere.getFilieres().add(this);
        return this;
    }

    public Filiere removeNomFiliere(Carriere carriere) {
        this.nomFilieres.remove(carriere);
        carriere.getFilieres().remove(this);
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
            "id=" + getId() +
            ", nomFiliere='" + getNomFiliere() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageFiliere='" + getImageFiliere() + "'" +
            ", imageFiliereContentType='" + getImageFiliereContentType() + "'" +
            "}";
    }
}
