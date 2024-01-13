package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
import static com.mycompany.myapp.domain.CoursTestSamples.*;
import static com.mycompany.myapp.domain.FiliereTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FiliereTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filiere.class);
        Filiere filiere1 = getFiliereSample1();
        Filiere filiere2 = new Filiere();
        assertThat(filiere1).isNotEqualTo(filiere2);

        filiere2.setId(filiere1.getId());
        assertThat(filiere1).isEqualTo(filiere2);

        filiere2 = getFiliereSample2();
        assertThat(filiere1).isNotEqualTo(filiere2);
    }

    @Test
    void nomCoursTest() throws Exception {
        Filiere filiere = getFiliereRandomSampleGenerator();
        Cours coursBack = getCoursRandomSampleGenerator();

        filiere.addNomCours(coursBack);
        assertThat(filiere.getNomCours()).containsOnly(coursBack);

        filiere.removeNomCours(coursBack);
        assertThat(filiere.getNomCours()).doesNotContain(coursBack);

        filiere.nomCours(new HashSet<>(Set.of(coursBack)));
        assertThat(filiere.getNomCours()).containsOnly(coursBack);

        filiere.setNomCours(new HashSet<>());
        assertThat(filiere.getNomCours()).doesNotContain(coursBack);
    }

    @Test
    void nomCarriereTest() throws Exception {
        Filiere filiere = getFiliereRandomSampleGenerator();
        Carriere carriereBack = getCarriereRandomSampleGenerator();

        filiere.addNomCarriere(carriereBack);
        assertThat(filiere.getNomCarrieres()).containsOnly(carriereBack);
        assertThat(carriereBack.getNomFilieres()).containsOnly(filiere);

        filiere.removeNomCarriere(carriereBack);
        assertThat(filiere.getNomCarrieres()).doesNotContain(carriereBack);
        assertThat(carriereBack.getNomFilieres()).doesNotContain(filiere);

        filiere.nomCarrieres(new HashSet<>(Set.of(carriereBack)));
        assertThat(filiere.getNomCarrieres()).containsOnly(carriereBack);
        assertThat(carriereBack.getNomFilieres()).containsOnly(filiere);

        filiere.setNomCarrieres(new HashSet<>());
        assertThat(filiere.getNomCarrieres()).doesNotContain(carriereBack);
        assertThat(carriereBack.getNomFilieres()).doesNotContain(filiere);
    }
}
