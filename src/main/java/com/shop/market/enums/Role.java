package com.shop.market.enums;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN")
    ;

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
