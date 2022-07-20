package com.raccoon.prefsimnotary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raccoon.prefsimnotary.model.enums.Permission;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Document
@Setter
@Getter
public class User extends BaseEntity implements UserDetails {

    @NotBlank(message = "name can not be empty")
    private String firstName;
    @NotBlank(message = "surname can not be empty")
    private String lastName;

    @Size(min = 6, max = 20, message = "username must be at least 6 most 20 char")
    @NotBlank(message = "username can not be empty")
    private String username;

//    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,14}$",
//            message = "min 6 and max 8 char, least 1 number, least 1 alphabet in capitals no special char allowed")
    @NotBlank(message = "password can not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email(message = "invalid email")
    @NotBlank(message = "email can not be empty")
    private String email;
    @JsonIgnore
    private Status userStatus;
    @JsonIgnore
    private Set<Permission> permissions;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return userStatus.equals(Status.ACTIVE);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
