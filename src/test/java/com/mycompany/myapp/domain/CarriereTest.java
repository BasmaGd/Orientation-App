package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
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
    void nomFiliereTest() throws Exception {
        Carriere carriere = getCarriereRandomSampleGenerator();
        Filiere filiereBack = getFiliereRandomSampleGenerator();

        carriere.addNomFiliere(filiereBack);
        assertThat(carriere.getNomFilieres()).containsOnly(filiereBack);

        carriere.removeNomFiliere(filiereBack);
        assertThat(carriere.getNomFilieres()).doesNotContain(filiereBack);

        carriere.nomFilieres(new HashSet<>(Set.of(filiereBack)));
        assertThat(carriere.getNomFilieres()).containsOnly(filiereBack);

        carriere.setNomFilieres(new HashSet<>());
        assertThat(carriere.getNomFilieres()).doesNotContain(filiereBack);
    }
}
