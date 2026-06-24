package br.com.compass.uol.controller;

import br.com.compass.uol.model.Account;
import br.com.compass.uol.repository.AccountRepository;
import br.com.compass.uol.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    ResponseEntity<List<Account>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.findAll());
    }

    @PostMapping()
    ResponseEntity<Account> save(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.save(account));
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> findById(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.findById(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@RequestParam Long id) {
        var account = accountService.findById(id);
        accountService.delete(account);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
