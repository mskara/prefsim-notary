package com.raccoon.prefsimnotary.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequestDto {

    @ApiModelProperty
    @NotBlank(message = "username can not be empty")
    @NotNull(message = "username can not be null")
    private String username;

    @ApiModelProperty(position = 1)
    @NotBlank(message = "password can not be empty")
    @NotNull(message = "password can not be null")
    private String password;

}
