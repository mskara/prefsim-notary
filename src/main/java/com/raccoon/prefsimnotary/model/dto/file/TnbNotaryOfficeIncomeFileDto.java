package com.raccoon.prefsimnotary.model.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class TnbNotaryOfficeIncomeFileDto {

    private String notaryOfficeCode;//Noterlik Kodu
    private String notaryOfficeName;//Noterlik Adı
    private Integer totalProcess;//İşlem Toplamı
    private Double grossIncome;//Gayri Safi Gelir
    private Double expense; //Aylık Gider
    private Double netIncome;//Safi Gelir

}