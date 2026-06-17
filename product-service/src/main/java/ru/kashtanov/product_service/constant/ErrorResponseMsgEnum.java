package ru.kashtanov.product_service.constant;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum ErrorResponseMsgEnum {
    PRODUCT_NOT_FOUND("Product Not Found");

    private final String name;

    ErrorResponseMsgEnum(String name) {
        this.name = name;
    }
}
