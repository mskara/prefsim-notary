package com.raccoon.prefsimnotary.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.model.document.embedded.NotaryInfo;
import com.raccoon.prefsimnotary.model.enums.Permission;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Document
@Builder
@Data
public class User implements UserDetails {

    @JsonIgnore
    @Id
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    @JsonIgnore
    private Status userStatus;
    @JsonIgnore
    private Set<Permission> permissions;
    private NotaryInfo notaryInfo;

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

    @JsonIgnore
    public boolean isNotary() {
        return getNotaryInfo() != null;
    }
}
