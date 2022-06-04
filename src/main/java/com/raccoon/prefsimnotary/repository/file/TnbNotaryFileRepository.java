package com.raccoon.prefsimnotary.repository.file;

import com.raccoon.prefsimnotary.model.dto.file.TnbNotaryFileDto;
import com.raccoon.prefsimnotary.model.enums.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TnbNotaryFileRepository extends AbstractFileRepository<TnbNotaryFileDto> {

    @Override
    protected String getFilePath() {
        return Files.TNB_NOTARY_LIST_FILE.getPath();
    }

    @Override
    protected String getFileName() {
        return Files.TNB_NOTARY_LIST_FILE.getFileName();
    }

    @Override
    protected TnbNotaryFileDto singleProcess(List<String> line) {

        return TnbNotaryFileDto.builder()
                .notaryOfficeName(line.get(0))
                .notaryName(line.get(1))
                .notaryOfficeClass(Integer.valueOf(line.get(2)))
                .notaryClass(Integer.valueOf(line.get(3)))
                .assignmentType(line.get(4))
                .notaryOfficePhone(line.get(5))
                .notaryOfficeFax(line.get(6))
                .notaryOfficeAddress(line.get(7))
                .city(getCityName(line.get(7)))
                .district(getDistrictName(line.get(7)))
                .registerNumber(Integer.valueOf(line.get(8)))
                .notaryOfficeEmail(line.get(9))
                .notaryOfficeCode(line.get(10))
                .build();
    }

    private String[] parseAddress(String address) {
        int i = StringUtils.lastOrdinalIndexOf(address.trim(), " ", 2);
        return address.substring(i + 1).split(" ");
    }

    private String getCityName(String address) {
        return parseAddress(address)[0];
    }

    private String getDistrictName(String address) {
        return parseAddress(address)[1];
    }


}