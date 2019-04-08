package com.xarala.yoonnee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ousmane SOW (ousmane.sow@mns-consulting.com) on 08/04/2019
 * for MNS Consulting Group - Digital Lab
 * @project yoonnee
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gerant {
    private String name, telephone, email;
}
