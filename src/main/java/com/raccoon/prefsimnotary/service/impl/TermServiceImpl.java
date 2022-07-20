package com.raccoon.prefsimnotary.service.impl;

import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.entity.Term;
import com.raccoon.prefsimnotary.model.enums.Status;
import com.raccoon.prefsimnotary.repository.TermRepository;
import com.raccoon.prefsimnotary.service.TermService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;

    @Override
    public List<Term> getTermList() {
        return termRepository.findAll();
    }

    @Override
    public Term getActiveTerm() {
        return termRepository.findByStatus(Status.ACTIVE)
                .orElseThrow(() -> new PrefsimException(ApiError.RESOURCE_NOT_FOUND, "Active Term not found!"));
    }

    @Override
    public Term addTerm(Term term) {

        termRepository.findByStatus(Status.ACTIVE).ifPresent(activeTerm -> {
            activeTerm.setStatus(Status.INACTIVE);
            termRepository.save(activeTerm);
        });

        Integer termCode = generateTermCode(term.getStartDate());
        term.setTermCode(termCode);
        term.setStatus(Status.ACTIVE);

        termRepository.deleteByTermCode(termCode);
        return termRepository.save(term);

    }

    private Integer generateTermCode(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return Integer.valueOf(date.format(formatter));
    }

}
