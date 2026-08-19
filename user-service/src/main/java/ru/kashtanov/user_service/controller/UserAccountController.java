package ru.kashtanov.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.UserAccountDto;
import ru.kashtanov.user_service.service.impl.TransactionTestService;
import ru.kashtanov.user_service.service.impl.UserAccountService;


/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/user_account")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final TransactionTestService testService;

    public UserAccountController(UserAccountService userAccountService, TransactionTestService testService) {
        this.userAccountService = userAccountService;
        this.testService = testService;
    }

    @PostMapping("/ops1")
    public ResponseEntity<UserAccountDto> loadUpBalance1(@RequestParam(name = "id") Long id,
                                                         @RequestParam(name = "amount") Float amount) {
        UserAccountDto dto = userAccountService.updateBalanceViaSqlUpdate(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/ops2")
    public ResponseEntity<UserAccountDto> loadUpBalance2(@RequestParam(name = "id") Long id,
                                                         @RequestParam(name = "amount") Float amount) {
        UserAccountDto dto = userAccountService.updateBalanceViaPessimisticLock(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/ops3")
    public ResponseEntity<UserAccountDto> loadUpBalance3(@RequestParam(name = "id") Long id,
                                                         @RequestParam(name = "amount") Float amount) {
        UserAccountDto dto = userAccountService.updateBalanceViaOptimisticLock(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/test_il")
    public ResponseEntity<UserAccountDto> testIsolationLevels(@RequestParam(name = "id") Long id,
                                                              @RequestParam(name = "amount") Float amount) {
        testService.testExecution(id, amount);
        return ResponseEntity.ok().build();
    }


}
