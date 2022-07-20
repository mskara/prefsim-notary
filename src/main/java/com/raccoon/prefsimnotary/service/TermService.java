package com.raccoon.prefsimnotary.service;

import com.raccoon.prefsimnotary.model.entity.Term;

import java.util.List;

public interface TermService {

    List<Term> getTermList();

    Term getActiveTerm();

    Term addTerm(Term term);

}