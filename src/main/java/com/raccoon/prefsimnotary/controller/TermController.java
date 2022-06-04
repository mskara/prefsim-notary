package com.raccoon.prefsimnotary.controller;

import com.raccoon.prefsimnotary.config.SwaggerConfig;
import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.service.TermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerConfig.TERM_TAG})
@RestController
@RequestMapping("/api/term")
@AllArgsConstructor
public class TermController {

    private final TermService termService;

    @ApiOperation(value = "Display all term list")
    @GetMapping
    @PreAuthorize("hasAuthority('PERM_VIEW_TERM_LIST')")
    public ResponseEntity<List<Term>> getTermList() {
        return ResponseEntity.ok(termService.getTermList());
    }

    @ApiOperation(value = "Display active term")
    @GetMapping("/active")
    @PreAuthorize("hasAuthority('PERM_VIEW_ACTIVE_TERM')")
    public ResponseEntity<Term> getActiveTerm() {
        return ResponseEntity.ok(termService.getActiveTerm());
    }

    @ApiOperation(value = "Create new term")
    @PostMapping
    @PreAuthorize("hasAuthority('PERM_CREATE_TERM')")
    public ResponseEntity<Term> addTerm(@Valid @RequestBody Term term) {
        return ResponseEntity.ok(termService.addTerm(term));
    }


}
