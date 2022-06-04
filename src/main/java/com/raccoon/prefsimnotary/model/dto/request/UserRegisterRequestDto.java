package com.raccoon.prefsimnotary.model.dto.request;

import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.embedded.Preference;
import com.raccoon.prefsimnotary.model.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class UserRegisterRequestDto {

    /* TODO: username min/max length and  password pattern */

    @NotBlank(message = "username can not be empty")
    @NotNull(message = "username can not be null")
    private String username;

    @NotBlank(message = "password can not be empty")
    @NotNull(message = "password can not be null")
    private String password;

    @Email
    @NotBlank(message = "email can not be empty")
    @NotNull(message = "email can not be null")
    private String email;

    @NotBlank(message = "name can not be empty")
    @NotNull(message = "name can not be null")
    private String name;

    @NotBlank(message = "surname can not be empty")
    @NotNull(message = "surname can not be null")
    private String surname;

    @Min(value = 1, message = "There must be at least 1")
    @Max(value = 4, message = "There must be at most 4")
    @NotNull(message = "notaryClass can not be null")
    private Integer notaryClass;

    @NotNull(message = "registerNumber can not be null")
    private Integer registerNumber;

}
