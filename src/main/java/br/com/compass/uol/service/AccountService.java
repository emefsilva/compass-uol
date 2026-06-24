package br.com.compass.uol.service;

import br.com.compass.uol.model.Account;
import br.com.compass.uol.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
       return accountRepository.findAll();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
