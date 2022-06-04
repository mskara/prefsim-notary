package com.raccoon.prefsimnotary.model.document.embedded;

import com.raccoon.prefsimnotary.model.dto.file.TnbNotaryOfficeIncomeFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnnualTransactionInfo {

    private Integer year;
    private Integer totalProcess;
    private Double grossIncome;
    private Double expense;
    private Double netIncome;

    public static AnnualTransactionInfo createEmptyAnnualTransaction(Integer year) {
        return new AnnualTransactionInfo(year, 0, 0.0, 0.0, 0.0);
    }

    public static AnnualTransactionInfo createAnnualTransactionByFileInfo(Integer year, TnbNotaryOfficeIncomeFileDto file) {
        return new AnnualTransactionInfo(year,
                file.getTotalProcess(),
                file.getGrossIncome(),
                file.getExpense(),
                file.getNetIncome());
    }

}