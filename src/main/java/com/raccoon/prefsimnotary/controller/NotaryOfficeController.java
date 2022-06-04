package com.raccoon.prefsimnotary.controller;

import com.raccoon.prefsimnotary.config.SwaggerConfig;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {SwaggerConfig.NOTARY_OFFICE_TAG})
@RestController
@RequestMapping("/api/notary-office")
@AllArgsConstructor
public class NotaryOfficeController {

    private final NotaryOfficeService notaryOfficeService;

    @ApiOperation(value = "Display all notary office list")
    @GetMapping
    @PreAuthorize("hasAuthority('PERM_VIEW_NOTARY_OFFICE_LIST')")
    public ResponseEntity<List<NotaryOffice>> getNotaryOfficeList() {
        return ResponseEntity.ok(notaryOfficeService.getNotaryOfficeList());
    }

    @ApiOperation(value = "Display active notary office list")
    @GetMapping("/active")
    @PreAuthorize("hasAuthority('PERM_VIEW_ACTIVE_NOTARY_OFFICE_LIST')")
    public ResponseEntity<List<NotaryOffice>> getActiveNotaryOfficeList() {
        return ResponseEntity.ok(notaryOfficeService.getActiveNotaryOfficeList());
    }

    @ApiOperation(value = "Display selected notary office")
    @GetMapping("/{notaryOfficeCode}")
    @PreAuthorize("hasAuthority('PERM_VIEW_NOTARY_OFFICE')")
    public ResponseEntity<NotaryOffice> getNotaryOffice(@PathVariable String notaryOfficeCode) {
        return ResponseEntity.ok(notaryOfficeService.getNotaryOffice(notaryOfficeCode));
    }

}
