package com.xarala.yoonnee.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Transaction.
 */
@Document(collection = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("emetteur")
    private String emetteur;

    @Field("agence_emetteur")
    private String agenceEmetteur;

    @Field("recepteur")
    private String recepteur;

    @Field("agence_recepteur")
    private String agenceRecepteur;

    @Field("montant_envoye")
    private Double montantEnvoye;

    @Field("comission")
    private Double comission;

    @Field("montant_recu")
    private Double montantRecu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmetteur() {
        return emetteur;
    }

    public Transaction emetteur(String emetteur) {
        this.emetteur = emetteur;
        return this;
    }

    public void setEmetteur(String emetteur) {
        this.emetteur = emetteur;
    }

    public String getAgenceEmetteur() {
        return agenceEmetteur;
    }

    public Transaction agenceEmetteur(String agenceEmetteur) {
        this.agenceEmetteur = agenceEmetteur;
        return this;
    }

    public void setAgenceEmetteur(String agenceEmetteur) {
        this.agenceEmetteur = agenceEmetteur;
    }

    public String getRecepteur() {
        return recepteur;
    }

    public Transaction recepteur(String recepteur) {
        this.recepteur = recepteur;
        return this;
    }

    public void setRecepteur(String recepteur) {
        this.recepteur = recepteur;
    }

    public String getAgenceRecepteur() {
        return agenceRecepteur;
    }

    public Transaction agenceRecepteur(String agenceRecepteur) {
        this.agenceRecepteur = agenceRecepteur;
        return this;
    }

    public void setAgenceRecepteur(String agenceRecepteur) {
        this.agenceRecepteur = agenceRecepteur;
    }

    public Double getMontantEnvoye() {
        return montantEnvoye;
    }

    public Transaction montantEnvoye(Double montantEnvoye) {
        this.montantEnvoye = montantEnvoye;
        return this;
    }

    public void setMontantEnvoye(Double montantEnvoye) {
        this.montantEnvoye = montantEnvoye;
    }

    public Double getComission() {
        return comission;
    }

    public Transaction comission(Double comission) {
        this.comission = comission;
        return this;
    }

    public void setComission(Double comission) {
        this.comission = comission;
    }

    public Double getMontantRecu() {
        return montantRecu;
    }

    public Transaction montantRecu(Double montantRecu) {
        this.montantRecu = montantRecu;
        return this;
    }

    public void setMontantRecu(Double montantRecu) {
        this.montantRecu = montantRecu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", emetteur='" + getEmetteur() + "'" +
            ", agenceEmetteur='" + getAgenceEmetteur() + "'" +
            ", recepteur='" + getRecepteur() + "'" +
            ", agenceRecepteur='" + getAgenceRecepteur() + "'" +
            ", montantEnvoye=" + getMontantEnvoye() +
            ", comission=" + getComission() +
            ", montantRecu=" + getMontantRecu() +
            "}";
    }
}
