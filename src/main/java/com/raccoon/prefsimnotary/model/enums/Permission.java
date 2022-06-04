package com.raccoon.prefsimnotary.model.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Permission implements GrantedAuthority {

    //user
    PERM_VIEW_USER_LIST,
    PERM_VIEW_USER,
    PERM_VIEW_CURRENT_USER,
    PERM_CREATE_PREFERENCE,
    PERM_VIEW_PREFERENCE_RESULT,

    //notary office
    PERM_VIEW_NOTARY_OFFICE_LIST,
    PERM_VIEW_ACTIVE_NOTARY_OFFICE_LIST,
    PERM_VIEW_NOTARY_OFFICE,

    //term
    PERM_VIEW_TERM_LIST,
    PERM_VIEW_ACTIVE_TERM,
    PERM_CREATE_TERM;

    @Override
    public String getAuthority() {
        return name();
    }

    public static Set<Permission> getSupervisorUserPermissions() {

        Set<Permission> permissionSet = getNotaryUserPermissions();

        permissionSet.add(PERM_VIEW_USER_LIST);
        permissionSet.add(PERM_VIEW_USER);
        permissionSet.add(PERM_VIEW_NOTARY_OFFICE);
        permissionSet.add(PERM_VIEW_TERM_LIST);
        permissionSet.add(PERM_CREATE_TERM);

        return permissionSet;

    }

    public static Set<Permission> getNotaryUserPermissions() {

        Set<Permission> permissionSet = new HashSet<>();

        permissionSet.add(PERM_VIEW_CURRENT_USER);
        permissionSet.add(PERM_CREATE_PREFERENCE);
        permissionSet.add(PERM_VIEW_PREFERENCE_RESULT);
        permissionSet.add(PERM_VIEW_NOTARY_OFFICE_LIST);
        permissionSet.add(PERM_VIEW_ACTIVE_NOTARY_OFFICE_LIST);
        permissionSet.add(PERM_VIEW_ACTIVE_TERM);

        return permissionSet;

    }
}