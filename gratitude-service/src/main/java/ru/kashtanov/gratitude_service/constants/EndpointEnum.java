package ru.kashtanov.gratitude_service.constants;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum EndpointEnum {
    GET_LIST_PRODUCTS("http://localhost:9015/api/v1/products/pointed?ids="),
    GET_USER("http://localhost:9060/api/users/");
    private final String uri;

    EndpointEnum(String uri) {
        this.uri = uri;
    }
}
