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

    @Size(min = 6, max = 20, message = "username must be at least 6 most 20 char")
    @NotBlank(message = "username can not be empty")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,8}$",
            message = "min 6 and max 8 char, least 1 number, least 1 alphabet in capitals no special char allowed")
    @NotBlank(message = "password can not be empty")
    private String password;

    @Email(message = "invalid email")
    @NotBlank(message = "email can not be empty")
    private String email;

    @NotBlank(message = "name can not be empty")
    private String name;

    @NotBlank(message = "surname can not be empty")
    private String surname;

    @Min(value = 1, message = "There must be at least 1")
    @Max(value = 4, message = "There must be at most 4")
    @NotNull(message = "notaryClass can not be null")
    private Integer notaryClass;

    @NotNull(message = "registerNumber can not be null")
    private Integer registerNumber;

}
