package com.raccoon.prefsimnotary.controller;

import com.raccoon.prefsimnotary.config.SwaggerConfig;
import com.raccoon.prefsimnotary.model.dto.request.PreferenceRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.PreferenceResultResponseDto;
import com.raccoon.prefsimnotary.service.NotaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerConfig.NOTARY_TAG})
@RestController
@RequestMapping("/api/notary")
@AllArgsConstructor
public class NotaryController {

    private final NotaryService notaryService;

    @ApiOperation(value = "Create new preference")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/preference")
    @PreAuthorize("hasAuthority('PERM_CREATE_PREFERENCE')")
    public void createPreference(@Valid @RequestBody PreferenceRequestDto preferenceRequestDto) {
        notaryService.createPreference(preferenceRequestDto);
    }

    @ApiOperation(value = "Display preference results")
    @GetMapping("/preference-results")
    @PreAuthorize("hasAuthority('PERM_VIEW_PREFERENCE_RESULT')")
    public ResponseEntity<List<PreferenceResultResponseDto>> getAllUsersPreferenceResults() {
        return ResponseEntity.ok(notaryService.getPreferenceResults());
    }

}