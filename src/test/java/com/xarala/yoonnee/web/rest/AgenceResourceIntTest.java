package com.xarala.yoonnee.web.rest;

import com.xarala.yoonnee.YoonneeApp;
import com.xarala.yoonnee.domain.Agence;
import com.xarala.yoonnee.repository.AgenceRepository;
import com.xarala.yoonnee.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;

import static com.xarala.yoonnee.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgenceResource REST controller.
 *
 * @see AgenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YoonneeApp.class)
public class AgenceResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAgenceMockMvc;

    private Agence agence;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createEntity() {
        return Agence.builder()
            .nom(DEFAULT_NOM)
            .build();
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgenceResource agenceResource = new AgenceResource(agenceRepository);
        this.restAgenceMockMvc = MockMvcBuilders.standaloneSetup(agenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Before
    public void initTest() {
        agenceRepository.deleteAll();
        agence = createEntity();
    }

    @Test
    public void createAgence() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isCreated());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate + 1);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    public void createAgenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence with an existing ID
        agence.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAgences() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);

        // Get all the agenceList
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    public void getAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);

        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", agence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agence.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    public void getNonExistingAgence() throws Exception {
        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);

        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Update the agence
        Agence updatedAgence = agenceRepository.findById(agence.getId()).get();
        updatedAgence
            .setNom(UPDATED_NOM);

        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgence)))
            .andExpect(status().isOk());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    public void updateNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Create the Agence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);

        int databaseSizeBeforeDelete = agenceRepository.findAll().size();

        // Delete the agence
        restAgenceMockMvc.perform(delete("/api/agences/{id}", agence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
