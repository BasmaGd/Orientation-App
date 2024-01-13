package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Carriere;
import com.mycompany.myapp.repository.CarriereRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CarriereResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CarriereResourceIT {

    private static final String DEFAULT_NOM_CARRIERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CARRIERE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carrieres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarriereRepository carriereRepository;

    @Mock
    private CarriereRepository carriereRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarriereMockMvc;

    private Carriere carriere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carriere createEntity(EntityManager em) {
        Carriere carriere = new Carriere().nomCarriere(DEFAULT_NOM_CARRIERE).description(DEFAULT_DESCRIPTION);
        return carriere;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carriere createUpdatedEntity(EntityManager em) {
        Carriere carriere = new Carriere().nomCarriere(UPDATED_NOM_CARRIERE).description(UPDATED_DESCRIPTION);
        return carriere;
    }

    @BeforeEach
    public void initTest() {
        carriere = createEntity(em);
    }

    @Test
    @Transactional
    void createCarriere() throws Exception {
        int databaseSizeBeforeCreate = carriereRepository.findAll().size();
        // Create the Carriere
        restCarriereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isCreated());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeCreate + 1);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCarriere()).isEqualTo(DEFAULT_NOM_CARRIERE);
        assertThat(testCarriere.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCarriereWithExistingId() throws Exception {
        // Create the Carriere with an existing ID
        carriere.setId(1L);

        int databaseSizeBeforeCreate = carriereRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarriereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarrieres() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        // Get all the carriereList
        restCarriereMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carriere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCarriere").value(hasItem(DEFAULT_NOM_CARRIERE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarrieresWithEagerRelationshipsIsEnabled() throws Exception {
        when(carriereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarriereMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(carriereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarrieresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(carriereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarriereMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(carriereRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        // Get the carriere
        restCarriereMockMvc
            .perform(get(ENTITY_API_URL_ID, carriere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carriere.getId().intValue()))
            .andExpect(jsonPath("$.nomCarriere").value(DEFAULT_NOM_CARRIERE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingCarriere() throws Exception {
        // Get the carriere
        restCarriereMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere
        Carriere updatedCarriere = carriereRepository.findById(carriere.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCarriere are not directly saved in db
        em.detach(updatedCarriere);
        updatedCarriere.nomCarriere(UPDATED_NOM_CARRIERE).description(UPDATED_DESCRIPTION);

        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarriere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCarriere()).isEqualTo(UPDATED_NOM_CARRIERE);
        assertThat(testCarriere.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carriere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarriereWithPatch() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere using partial update
        Carriere partialUpdatedCarriere = new Carriere();
        partialUpdatedCarriere.setId(carriere.getId());

        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCarriere()).isEqualTo(DEFAULT_NOM_CARRIERE);
        assertThat(testCarriere.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCarriereWithPatch() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere using partial update
        Carriere partialUpdatedCarriere = new Carriere();
        partialUpdatedCarriere.setId(carriere.getId());

        partialUpdatedCarriere.nomCarriere(UPDATED_NOM_CARRIERE).description(UPDATED_DESCRIPTION);

        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCarriere()).isEqualTo(UPDATED_NOM_CARRIERE);
        assertThat(testCarriere.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeDelete = carriereRepository.findAll().size();

        // Delete the carriere
        restCarriereMockMvc
            .perform(delete(ENTITY_API_URL_ID, carriere.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
