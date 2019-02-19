package com.xarala.yoonnee.web.rest;
import com.xarala.yoonnee.domain.Agence;
import com.xarala.yoonnee.repository.AgenceRepository;
import com.xarala.yoonnee.web.rest.errors.BadRequestAlertException;
import com.xarala.yoonnee.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Agence.
 */
@RestController
@RequestMapping("/api")
public class AgenceResource {

    private final Logger log = LoggerFactory.getLogger(AgenceResource.class);

    private static final String ENTITY_NAME = "agence";

    private final AgenceRepository agenceRepository;

    public AgenceResource(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    /**
     * POST  /agences : Create a new agence.
     *
     * @param agence the agence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agence, or with status 400 (Bad Request) if the agence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agences")
    public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) throws URISyntaxException {
        log.debug("REST request to save Agence : {}", agence);
        if (agence.getId() != null) {
            throw new BadRequestAlertException("A new agence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agence result = agenceRepository.save(agence);
        return ResponseEntity.created(new URI("/api/agences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agences : Updates an existing agence.
     *
     * @param agence the agence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agence,
     * or with status 400 (Bad Request) if the agence is not valid,
     * or with status 500 (Internal Server Error) if the agence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agences")
    public ResponseEntity<Agence> updateAgence(@RequestBody Agence agence) throws URISyntaxException {
        log.debug("REST request to update Agence : {}", agence);
        if (agence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agence result = agenceRepository.save(agence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agences : get all the agences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agences in body
     */
    @GetMapping("/agences")
    public List<Agence> getAllAgences() {
        log.debug("REST request to get all Agences");
        return agenceRepository.findAll();
    }

    /**
     * GET  /agences/:id : get the "id" agence.
     *
     * @param id the id of the agence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agence, or with status 404 (Not Found)
     */
    @GetMapping("/agences/{id}")
    public ResponseEntity<Agence> getAgence(@PathVariable String id) {
        log.debug("REST request to get Agence : {}", id);
        Optional<Agence> agence = agenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agence);
    }

    /**
     * DELETE  /agences/:id : delete the "id" agence.
     *
     * @param id the id of the agence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agences/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable String id) {
        log.debug("REST request to delete Agence : {}", id);
        agenceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
