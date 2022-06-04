package com.raccoon.prefsimnotary.repository.file;

import com.raccoon.prefsimnotary.model.dto.file.TnbNotaryOfficeIncomeFileDto;
import com.raccoon.prefsimnotary.model.enums.Files;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TnbNotaryOfficeIncomeFileRepository extends AbstractFileRepository<TnbNotaryOfficeIncomeFileDto> {

    @Override
    protected String getFilePath() {
        return Files.TNB_INCOME_LIST_FILE.getPath();
    }

    @Override
    protected String getFileName() {
        return Files.TNB_INCOME_LIST_FILE.getFileName();
    }

    @Override
    protected TnbNotaryOfficeIncomeFileDto singleProcess(List<String> line) {

        return TnbNotaryOfficeIncomeFileDto.builder()
                .notaryOfficeCode(line.get(0))
                .notaryOfficeName(line.get(1))
                .totalProcess(Integer.valueOf(line.get(2)))
                .grossIncome(Double.valueOf(line.get(3)))
                .expense(Double.valueOf(line.get(4)))
                .netIncome(Double.valueOf(line.get(5)))
                .build();
    }

}
