package com.raccoon.prefsimnotary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.Set;

@Document
@Builder
@Data
public class NotaryOffice extends BaseEntity {

    private String notaryOfficeCode;
    private String name;
    private Integer notaryOfficeClass;
    private Status preferenceStatus;
    private Contact contact;
    private Set<AnnualTransactionInfo> annualTransactionInfos;

    @JsonIgnore
    public AnnualTransactionInfo getLastAnnualTransactionInfo() {
        return getAnnualTransactionInfos()
                .stream()
                .max(Comparator.comparingInt(AnnualTransactionInfo::getYear))
                .orElseThrow();
    }

}
