package com.xarala.yoonnee.repository;

import com.xarala.yoonnee.domain.Client;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

}
