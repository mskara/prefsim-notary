package com.raccoon.prefsimnotary.service.impl;

import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.dto.file.TnbNotaryFileDto;
import com.raccoon.prefsimnotary.model.dto.file.TnbNotaryOfficeIncomeFileDto;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.embedded.AnnualTransactionInfo;
import com.raccoon.prefsimnotary.model.document.embedded.Contact;
import com.raccoon.prefsimnotary.model.enums.Status;
import com.raccoon.prefsimnotary.repository.NotaryOfficeRepository;
import com.raccoon.prefsimnotary.repository.file.FileRepository;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class NotaryOfficeServiceImpl implements NotaryOfficeService {

    private final NotaryOfficeRepository notaryOfficeRepository;
    private final FileRepository<TnbNotaryFileDto> tnbNotaryFileRepository;
    private final FileRepository<TnbNotaryOfficeIncomeFileDto> tnbNotaryOfficeIncomeFileRepository;

    @Override
    public List<NotaryOffice> getNotaryOfficeList() {
        return notaryOfficeRepository.findAllByOrderByName();
    }

    @Override
    public List<NotaryOffice> getActiveNotaryOfficeList() {
        return notaryOfficeRepository.findAllByPreferenceStatusOrderByName(Status.ACTIVE);
    }

    @Override
    public NotaryOffice getNotaryOffice(String notaryOfficeCode) {
        return notaryOfficeRepository.findByNotaryOfficeCode(notaryOfficeCode)
                .orElseThrow(() -> new PrefsimException(ApiError.NOTARY_OFFICE_NOT_FOUND,
                        "Notary Office not found! :" + notaryOfficeCode));
    }

    @Override
    public List<NotaryOffice> getActiveNotaryOfficeList(Set<String> notaryOfficeCodes) {
        List<NotaryOffice> notaryOffices = notaryOfficeRepository
                .findAllByNotaryOfficeCodeInAndPreferenceStatus(notaryOfficeCodes, Status.ACTIVE);
        notaryOffices.sort(Comparator.comparing(notaryOffice -> notaryOffice.getLastAnnualTransactionInfo().getNetIncome()));
        return notaryOffices;
    }

    @Override
    public void fetchNotaryOfficeListFromTnb(Integer year) {

        final List<TnbNotaryFileDto> tnbNotaryFile = tnbNotaryFileRepository.process();
        final List<TnbNotaryOfficeIncomeFileDto> tnbNotaryOfficeIncomeFile = tnbNotaryOfficeIncomeFileRepository.process();

        tnbNotaryFile.forEach(line -> {

            //if notary office exists get this, else create new one
            final NotaryOffice notaryOffice = notaryOfficeRepository.findByNotaryOfficeCode(line.getNotaryOfficeCode())
                    .orElse(NotaryOffice.builder()
                            .name(line.getNotaryOfficeName())
                            .notaryOfficeClass(line.getNotaryOfficeClass())
                            .preferenceStatus(Status.ACTIVE)
                            .notaryOfficeCode(line.getNotaryOfficeCode())
                            .annualTransactionInfos(new HashSet<>())
                            .build());

            //upsert notary office contact info
            final Contact notaryOfficeContact = Contact.builder()
                    .city(line.getCity())
                    .district(line.getDistrict())
                    .address(line.getNotaryOfficeAddress())
                    .phone(line.getNotaryOfficePhone())
                    .build();
            notaryOffice.setContact(notaryOfficeContact);

            //upsert notary office transaction info, if tx is exists this year, remove and add new info
            final AnnualTransactionInfo annualTransactionInfo = tnbNotaryOfficeIncomeFile.stream()
                    .filter(tx -> tx.getNotaryOfficeCode().equals(line.getNotaryOfficeCode()))
                    .findFirst()
                    .map(tx -> AnnualTransactionInfo.createAnnualTransactionByFileInfo(year, tx))
                    .orElse(AnnualTransactionInfo.createEmptyAnnualTransaction(year));
            notaryOffice.getAnnualTransactionInfos().removeIf(tx -> tx.getYear().equals(year));
            notaryOffice.getAnnualTransactionInfos().add(annualTransactionInfo);

            notaryOfficeRepository.save(notaryOffice);
        });

    }

}