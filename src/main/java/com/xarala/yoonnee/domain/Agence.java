package com.xarala.yoonnee.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Agence.
 */
@Document(collection = "agence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    private String adresse;

    private String ville;

    private String pays;
}
