package ru.kashtanov.auth_service.constants;

/**
 * @author Viktor Кashtanov
 */
public class UriConstant {
    // todo remove hardcore later, using EUREKA SERVICE DISCOVERY!
    public static final String FETCH_USER_BY_LOGIN="http://localhost:9060/api/users?username=";
    public static final String POST_REGISTER_USER="http://localhost:9060/api/users";

 }
