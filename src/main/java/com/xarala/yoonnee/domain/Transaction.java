package com.xarala.yoonnee.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Transaction.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String transactionId;

    @Field("dateOfIssue")
    private String dateOfIssue;

    @Field("dateOfReceipt")
    private String dateOfReceipt;

    @Field("sender")
    private String sender;

    @Field("receiver")
    private String receiver;

    @Field("sender agency")
    private String senderAgency;

    @Field("receiver agency")
    private String receiverAgency;

    @Field("amountToSend")
    private Double amountToSend;

    @Field("receivedAmount")
    private Double receivedAmount;

    @Field("commission")
    private Double commission;

}
