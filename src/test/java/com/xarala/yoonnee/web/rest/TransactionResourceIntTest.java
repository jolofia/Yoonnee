package com.xarala.yoonnee.web.rest;

import com.xarala.yoonnee.YoonneeApp;

import com.xarala.yoonnee.domain.Transaction;
import com.xarala.yoonnee.repository.TransactionRepository;
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
 * Test class for the TransactionResource REST controller.
 *
 * @see TransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YoonneeApp.class)
public class TransactionResourceIntTest {

    private static final String DEFAULT_EMETTEUR = "AAAAAAAAAA";
    private static final String UPDATED_EMETTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCE_EMETTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE_EMETTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_RECEPTEUR = "AAAAAAAAAA";
    private static final String UPDATED_RECEPTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCE_RECEPTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE_RECEPTEUR = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT_ENVOYE = 1D;
    private static final Double UPDATED_MONTANT_ENVOYE = 2D;

    private static final Double DEFAULT_COMISSION = 1D;
    private static final Double UPDATED_COMISSION = 2D;

    private static final Double DEFAULT_MONTANT_RECU = 1D;
    private static final Double UPDATED_MONTANT_RECU = 2D;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionRepository);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity() {
        Transaction transaction = new Transaction()
            .emetteur(DEFAULT_EMETTEUR)
            .agenceEmetteur(DEFAULT_AGENCE_EMETTEUR)
            .recepteur(DEFAULT_RECEPTEUR)
            .agenceRecepteur(DEFAULT_AGENCE_RECEPTEUR)
            .montantEnvoye(DEFAULT_MONTANT_ENVOYE)
            .comission(DEFAULT_COMISSION)
            .montantRecu(DEFAULT_MONTANT_RECU);
        return transaction;
    }

    @Before
    public void initTest() {
        transactionRepository.deleteAll();
        transaction = createEntity();
    }

    @Test
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getEmetteur()).isEqualTo(DEFAULT_EMETTEUR);
        assertThat(testTransaction.getAgenceEmetteur()).isEqualTo(DEFAULT_AGENCE_EMETTEUR);
        assertThat(testTransaction.getRecepteur()).isEqualTo(DEFAULT_RECEPTEUR);
        assertThat(testTransaction.getAgenceRecepteur()).isEqualTo(DEFAULT_AGENCE_RECEPTEUR);
        assertThat(testTransaction.getMontantEnvoye()).isEqualTo(DEFAULT_MONTANT_ENVOYE);
        assertThat(testTransaction.getComission()).isEqualTo(DEFAULT_COMISSION);
        assertThat(testTransaction.getMontantRecu()).isEqualTo(DEFAULT_MONTANT_RECU);
    }

    @Test
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.save(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId())))
            .andExpect(jsonPath("$.[*].emetteur").value(hasItem(DEFAULT_EMETTEUR.toString())))
            .andExpect(jsonPath("$.[*].agenceEmetteur").value(hasItem(DEFAULT_AGENCE_EMETTEUR.toString())))
            .andExpect(jsonPath("$.[*].recepteur").value(hasItem(DEFAULT_RECEPTEUR.toString())))
            .andExpect(jsonPath("$.[*].agenceRecepteur").value(hasItem(DEFAULT_AGENCE_RECEPTEUR.toString())))
            .andExpect(jsonPath("$.[*].montantEnvoye").value(hasItem(DEFAULT_MONTANT_ENVOYE.doubleValue())))
            .andExpect(jsonPath("$.[*].comission").value(hasItem(DEFAULT_COMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].montantRecu").value(hasItem(DEFAULT_MONTANT_RECU.doubleValue())));
    }
    
    @Test
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.save(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId()))
            .andExpect(jsonPath("$.emetteur").value(DEFAULT_EMETTEUR.toString()))
            .andExpect(jsonPath("$.agenceEmetteur").value(DEFAULT_AGENCE_EMETTEUR.toString()))
            .andExpect(jsonPath("$.recepteur").value(DEFAULT_RECEPTEUR.toString()))
            .andExpect(jsonPath("$.agenceRecepteur").value(DEFAULT_AGENCE_RECEPTEUR.toString()))
            .andExpect(jsonPath("$.montantEnvoye").value(DEFAULT_MONTANT_ENVOYE.doubleValue()))
            .andExpect(jsonPath("$.comission").value(DEFAULT_COMISSION.doubleValue()))
            .andExpect(jsonPath("$.montantRecu").value(DEFAULT_MONTANT_RECU.doubleValue()));
    }

    @Test
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.save(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        updatedTransaction
            .emetteur(UPDATED_EMETTEUR)
            .agenceEmetteur(UPDATED_AGENCE_EMETTEUR)
            .recepteur(UPDATED_RECEPTEUR)
            .agenceRecepteur(UPDATED_AGENCE_RECEPTEUR)
            .montantEnvoye(UPDATED_MONTANT_ENVOYE)
            .comission(UPDATED_COMISSION)
            .montantRecu(UPDATED_MONTANT_RECU);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getEmetteur()).isEqualTo(UPDATED_EMETTEUR);
        assertThat(testTransaction.getAgenceEmetteur()).isEqualTo(UPDATED_AGENCE_EMETTEUR);
        assertThat(testTransaction.getRecepteur()).isEqualTo(UPDATED_RECEPTEUR);
        assertThat(testTransaction.getAgenceRecepteur()).isEqualTo(UPDATED_AGENCE_RECEPTEUR);
        assertThat(testTransaction.getMontantEnvoye()).isEqualTo(UPDATED_MONTANT_ENVOYE);
        assertThat(testTransaction.getComission()).isEqualTo(UPDATED_COMISSION);
        assertThat(testTransaction.getMontantRecu()).isEqualTo(UPDATED_MONTANT_RECU);
    }

    @Test
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.save(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId("id1");
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId("id2");
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
