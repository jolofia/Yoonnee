package com.xarala.yoonnee.repository;

import com.xarala.yoonnee.domain.Agence;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Agence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceRepository extends MongoRepository<Agence, String> {

}
