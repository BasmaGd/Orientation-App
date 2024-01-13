package com.mycompany.myapp.domain;

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
    void nomFiliereTest() throws Exception {
        Cours cours = getCoursRandomSampleGenerator();
        Filiere filiereBack = getFiliereRandomSampleGenerator();

        cours.addNomFiliere(filiereBack);
        assertThat(cours.getNomFilieres()).containsOnly(filiereBack);
        assertThat(filiereBack.getNomCours()).containsOnly(cours);

        cours.removeNomFiliere(filiereBack);
        assertThat(cours.getNomFilieres()).doesNotContain(filiereBack);
        assertThat(filiereBack.getNomCours()).doesNotContain(cours);

        cours.nomFilieres(new HashSet<>(Set.of(filiereBack)));
        assertThat(cours.getNomFilieres()).containsOnly(filiereBack);
        assertThat(filiereBack.getNomCours()).containsOnly(cours);

        cours.setNomFilieres(new HashSet<>());
        assertThat(cours.getNomFilieres()).doesNotContain(filiereBack);
        assertThat(filiereBack.getNomCours()).doesNotContain(cours);
    }
}
