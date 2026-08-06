package ru.kashtanov.news_service.enums;

/**
 * @author Viktor Кashtanov
 */
public enum NewsContentEnum {

    TEXT(1),
    PICTURE(2),
    VIDEO(3);

    private final int typeNum;


    NewsContentEnum(int typeNum) {
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }
}
