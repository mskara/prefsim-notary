package com.raccoon.prefsimnotary.service;

import com.raccoon.prefsimnotary.model.document.NotaryOffice;

import java.util.List;
import java.util.Set;

public interface NotaryOfficeService {

    List<NotaryOffice> getNotaryOfficeList();

    List<NotaryOffice> getActiveNotaryOfficeList();

    NotaryOffice getNotaryOffice(String notaryOfficeCode);

    List<NotaryOffice> getActiveNotaryOfficeList(Set<String> id);

    void fetchNotaryOfficeListFromTnb(Integer year);
}