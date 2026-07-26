package ru.kashtanov.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/vpn")
public class VpnController {


    @GetMapping("/info")
    public ResponseEntity<?> getVpnInfo() {
        return ResponseEntity.ok("JWT works");
    }
}
