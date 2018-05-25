package com.houssem.poc.web.rest;

import com.houssem.poc.PocApp;

import com.houssem.poc.domain.Compte;
import com.houssem.poc.repository.CompteRepository;
import com.houssem.poc.service.CompteService;
import com.houssem.poc.service.dto.CompteDTO;
import com.houssem.poc.service.mapper.CompteMapper;
import com.houssem.poc.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.houssem.poc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.houssem.poc.domain.enumeration.CompteType;
/**
 * Test class for the CompteResource REST controller.
 *
 * @see CompteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PocApp.class)
public class CompteResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OUV = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OUV = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final CompteType DEFAULT_TYPE = CompteType.ORDINAIRE;
    private static final CompteType UPDATED_TYPE = CompteType.VIP;

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;

    @Autowired
    private CompteService compteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompteMockMvc;

    private Compte compte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteResource compteResource = new CompteResource(compteService);
        this.restCompteMockMvc = MockMvcBuilders.standaloneSetup(compteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte()
            .reference(DEFAULT_REFERENCE)
            .dateOuv(DEFAULT_DATE_OUV)
            .adresse(DEFAULT_ADRESSE)
            .type(DEFAULT_TYPE)
            .actif(DEFAULT_ACTIF);
        return compte;
    }

    @Before
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCompte.getDateOuv()).isEqualTo(DEFAULT_DATE_OUV);
        assertThat(testCompte.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testCompte.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCompte.isActif()).isEqualTo(DEFAULT_ACTIF);
    }

    @Test
    @Transactional
    public void createCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte with an existing ID
        compte.setId(1L);
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setReference(null);

        // Create the Compte, which fails.
        CompteDTO compteDTO = compteMapper.toDto(compte);

        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].dateOuv").value(hasItem(DEFAULT_DATE_OUV.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.dateOuv").value(DEFAULT_DATE_OUV.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findOne(compte.getId());
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte
            .reference(UPDATED_REFERENCE)
            .dateOuv(UPDATED_DATE_OUV)
            .adresse(UPDATED_ADRESSE)
            .type(UPDATED_TYPE)
            .actif(UPDATED_ACTIF);
        CompteDTO compteDTO = compteMapper.toDto(updatedCompte);

        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCompte.getDateOuv()).isEqualTo(UPDATED_DATE_OUV);
        assertThat(testCompte.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCompte.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCompte.isActif()).isEqualTo(UPDATED_ACTIF);
    }

    @Test
    @Transactional
    public void updateNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);
        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Get the compte
        restCompteMockMvc.perform(delete("/api/comptes/{id}", compte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compte.class);
        Compte compte1 = new Compte();
        compte1.setId(1L);
        Compte compte2 = new Compte();
        compte2.setId(compte1.getId());
        assertThat(compte1).isEqualTo(compte2);
        compte2.setId(2L);
        assertThat(compte1).isNotEqualTo(compte2);
        compte1.setId(null);
        assertThat(compte1).isNotEqualTo(compte2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteDTO.class);
        CompteDTO compteDTO1 = new CompteDTO();
        compteDTO1.setId(1L);
        CompteDTO compteDTO2 = new CompteDTO();
        assertThat(compteDTO1).isNotEqualTo(compteDTO2);
        compteDTO2.setId(compteDTO1.getId());
        assertThat(compteDTO1).isEqualTo(compteDTO2);
        compteDTO2.setId(2L);
        assertThat(compteDTO1).isNotEqualTo(compteDTO2);
        compteDTO1.setId(null);
        assertThat(compteDTO1).isNotEqualTo(compteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compteMapper.fromId(null)).isNull();
    }
}
