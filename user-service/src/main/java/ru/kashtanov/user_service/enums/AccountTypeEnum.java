package ru.kashtanov.user_service.enums;

/**
 * @author Viktor Кashtanov
 */
public enum AccountTypeEnum {

    DEFAULT(1),
    PERSONAL(2),
    BUSINESS(3);

    private final int typeNum;


    AccountTypeEnum(int typeNum) {
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }
}
