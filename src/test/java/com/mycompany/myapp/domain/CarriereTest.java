package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
import static com.mycompany.myapp.domain.CoursTestSamples.*;
import static com.mycompany.myapp.domain.FiliereTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CarriereTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carriere.class);
        Carriere carriere1 = getCarriereSample1();
        Carriere carriere2 = new Carriere();
        assertThat(carriere1).isNotEqualTo(carriere2);

        carriere2.setId(carriere1.getId());
        assertThat(carriere1).isEqualTo(carriere2);

        carriere2 = getCarriereSample2();
        assertThat(carriere1).isNotEqualTo(carriere2);
    }

    @Test
    void filiereTest() throws Exception {
        Carriere carriere = getCarriereRandomSampleGenerator();
        Filiere filiereBack = getFiliereRandomSampleGenerator();

        carriere.addFiliere(filiereBack);
        assertThat(carriere.getFilieres()).containsOnly(filiereBack);

        carriere.removeFiliere(filiereBack);
        assertThat(carriere.getFilieres()).doesNotContain(filiereBack);

        carriere.filieres(new HashSet<>(Set.of(filiereBack)));
        assertThat(carriere.getFilieres()).containsOnly(filiereBack);

        carriere.setFilieres(new HashSet<>());
        assertThat(carriere.getFilieres()).doesNotContain(filiereBack);
    }

    @Test
    void coursTest() throws Exception {
        Carriere carriere = getCarriereRandomSampleGenerator();
        Cours coursBack = getCoursRandomSampleGenerator();

        carriere.addCours(coursBack);
        assertThat(carriere.getCours()).containsOnly(coursBack);

        carriere.removeCours(coursBack);
        assertThat(carriere.getCours()).doesNotContain(coursBack);

        carriere.cours(new HashSet<>(Set.of(coursBack)));
        assertThat(carriere.getCours()).containsOnly(coursBack);

        carriere.setCours(new HashSet<>());
        assertThat(carriere.getCours()).doesNotContain(coursBack);
    }
}
