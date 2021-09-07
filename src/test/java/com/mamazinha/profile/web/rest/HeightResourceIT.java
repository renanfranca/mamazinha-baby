package com.mamazinha.profile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mamazinha.profile.IntegrationTest;
import com.mamazinha.profile.domain.Height;
import com.mamazinha.profile.repository.HeightRepository;
import com.mamazinha.profile.service.dto.HeightDTO;
import com.mamazinha.profile.service.mapper.HeightMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HeightResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeightResourceIT {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_IDEAL_WIGHT = 1F;
    private static final Float UPDATED_IDEAL_WIGHT = 2F;

    private static final String ENTITY_API_URL = "/api/heights";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HeightRepository heightRepository;

    @Autowired
    private HeightMapper heightMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeightMockMvc;

    private Height height;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Height createEntity(EntityManager em) {
        Height height = new Height().value(DEFAULT_VALUE).date(DEFAULT_DATE).idealWight(DEFAULT_IDEAL_WIGHT);
        return height;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Height createUpdatedEntity(EntityManager em) {
        Height height = new Height().value(UPDATED_VALUE).date(UPDATED_DATE).idealWight(UPDATED_IDEAL_WIGHT);
        return height;
    }

    @BeforeEach
    public void initTest() {
        height = createEntity(em);
    }

    @Test
    @Transactional
    void createHeight() throws Exception {
        int databaseSizeBeforeCreate = heightRepository.findAll().size();
        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);
        restHeightMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeCreate + 1);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testHeight.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHeight.getIdealWight()).isEqualTo(DEFAULT_IDEAL_WIGHT);
    }

    @Test
    @Transactional
    void createHeightWithExistingId() throws Exception {
        // Create the Height with an existing ID
        height.setId(1L);
        HeightDTO heightDTO = heightMapper.toDto(height);

        int databaseSizeBeforeCreate = heightRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeightMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeights() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList
        restHeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(height.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].idealWight").value(hasItem(DEFAULT_IDEAL_WIGHT.doubleValue())));
    }

    @Test
    @Transactional
    void getHeight() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get the height
        restHeightMockMvc
            .perform(get(ENTITY_API_URL_ID, height.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(height.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.idealWight").value(DEFAULT_IDEAL_WIGHT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHeight() throws Exception {
        // Get the height
        restHeightMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHeight() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeUpdate = heightRepository.findAll().size();

        // Update the height
        Height updatedHeight = heightRepository.findById(height.getId()).get();
        // Disconnect from session so that the updates on updatedHeight are not directly saved in db
        em.detach(updatedHeight);
        updatedHeight.value(UPDATED_VALUE).date(UPDATED_DATE).idealWight(UPDATED_IDEAL_WIGHT);
        HeightDTO heightDTO = heightMapper.toDto(updatedHeight);

        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heightDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isOk());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testHeight.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHeight.getIdealWight()).isEqualTo(UPDATED_IDEAL_WIGHT);
    }

    @Test
    @Transactional
    void putNonExistingHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(count.incrementAndGet());

        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heightDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(count.incrementAndGet());

        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(count.incrementAndGet());

        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeightWithPatch() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeUpdate = heightRepository.findAll().size();

        // Update the height using partial update
        Height partialUpdatedHeight = new Height();
        partialUpdatedHeight.setId(height.getId());

        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeight.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeight))
            )
            .andExpect(status().isOk());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testHeight.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHeight.getIdealWight()).isEqualTo(DEFAULT_IDEAL_WIGHT);
    }

    @Test
    @Transactional
    void fullUpdateHeightWithPatch() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeUpdate = heightRepository.findAll().size();

        // Update the height using partial update
        Height partialUpdatedHeight = new Height();
        partialUpdatedHeight.setId(height.getId());

        partialUpdatedHeight.value(UPDATED_VALUE).date(UPDATED_DATE).idealWight(UPDATED_IDEAL_WIGHT);

        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeight.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeight))
            )
            .andExpect(status().isOk());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testHeight.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHeight.getIdealWight()).isEqualTo(UPDATED_IDEAL_WIGHT);
    }

    @Test
    @Transactional
    void patchNonExistingHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(count.incrementAndGet());

        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heightDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(count.incrementAndGet());

        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(count.incrementAndGet());

        // Create the Height
        HeightDTO heightDTO = heightMapper.toDto(height);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heightDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeight() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeDelete = heightRepository.findAll().size();

        // Delete the height
        restHeightMockMvc
            .perform(delete(ENTITY_API_URL_ID, height.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
