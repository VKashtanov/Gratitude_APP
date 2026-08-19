package ru.kashtanov.user_service.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author Viktor Кashtanov
 */
@Service
public class TestService {
    public int doFavorReturnOne(int i) {
        System.out.println("doFavorReturnOne");
        return i+1;
    }
}
