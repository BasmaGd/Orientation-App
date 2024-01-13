package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
import static com.mycompany.myapp.domain.CoursTestSamples.*;
import static com.mycompany.myapp.domain.FiliereTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cours.class);
        Cours cours1 = getCoursSample1();
        Cours cours2 = new Cours();
        assertThat(cours1).isNotEqualTo(cours2);

        cours2.setId(cours1.getId());
        assertThat(cours1).isEqualTo(cours2);

        cours2 = getCoursSample2();
        assertThat(cours1).isNotEqualTo(cours2);
    }

    @Test
    void filiereTest() throws Exception {
        Cours cours = getCoursRandomSampleGenerator();
        Filiere filiereBack = getFiliereRandomSampleGenerator();

        cours.addFiliere(filiereBack);
        assertThat(cours.getFilieres()).containsOnly(filiereBack);

        cours.removeFiliere(filiereBack);
        assertThat(cours.getFilieres()).doesNotContain(filiereBack);

        cours.filieres(new HashSet<>(Set.of(filiereBack)));
        assertThat(cours.getFilieres()).containsOnly(filiereBack);

        cours.setFilieres(new HashSet<>());
        assertThat(cours.getFilieres()).doesNotContain(filiereBack);
    }

    @Test
    void nomCarriereTest() throws Exception {
        Cours cours = getCoursRandomSampleGenerator();
        Carriere carriereBack = getCarriereRandomSampleGenerator();

        cours.addNomCarriere(carriereBack);
        assertThat(cours.getNomCarrieres()).containsOnly(carriereBack);
        assertThat(carriereBack.getCours()).containsOnly(cours);

        cours.removeNomCarriere(carriereBack);
        assertThat(cours.getNomCarrieres()).doesNotContain(carriereBack);
        assertThat(carriereBack.getCours()).doesNotContain(cours);

        cours.nomCarrieres(new HashSet<>(Set.of(carriereBack)));
        assertThat(cours.getNomCarrieres()).containsOnly(carriereBack);
        assertThat(carriereBack.getCours()).containsOnly(cours);

        cours.setNomCarrieres(new HashSet<>());
        assertThat(cours.getNomCarrieres()).doesNotContain(carriereBack);
        assertThat(carriereBack.getCours()).doesNotContain(cours);
    }
}
