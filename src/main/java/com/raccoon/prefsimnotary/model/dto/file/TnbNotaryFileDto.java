package com.raccoon.prefsimnotary.model.dto.file;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TnbNotaryFileDto {

    private String notaryOfficeName;//NOTERLİK ADI
    private String notaryName;//ADI SOYADI
    private Integer notaryOfficeClass;//NOTERLİK SINIFI
    private Integer notaryClass;//NOTER SINIFI
    private String assignmentType;//ASIL/VEKIL
    private String notaryOfficePhone;//TELEFON
    private String notaryOfficeFax;//FAKS
    private String notaryOfficeAddress;//ADRES
    private String city;
    private String district;
    private Integer registerNumber; //Sicil No
    private String notaryOfficeEmail; //E-Mail
    private String notaryOfficeCode; //Kodu
}
