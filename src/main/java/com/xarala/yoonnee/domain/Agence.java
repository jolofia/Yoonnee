package com.xarala.yoonnee.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

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

    private String nom;

    private String adresse;

    private String ville;

    private String pays;

    private String telephone;

    private List<Gerant> gerants;
}
