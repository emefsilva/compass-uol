package br.com.compass.uol.controller;

import br.com.compass.uol.model.Account;
import br.com.compass.uol.service.AccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AccountGraphQLController {


    private final AccountService accountService;

    public AccountGraphQLController(AccountService accountService) {
        this.accountService = accountService;
    }

    @QueryMapping
    public List<Account> accounts() {
        return accountService.findAll();
    }

    @QueryMapping
    public Account account(@Argument Long id) {
        return accountService.findById(id);
    }
}
