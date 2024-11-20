package com.springbootguides.banking_app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootguides.banking_app.dto.AccountDto;
import com.springbootguides.banking_app.entity.Account;
import com.springbootguides.banking_app.exception.AccountException;
import com.springbootguides.banking_app.mapper.AccountMapper;
import com.springbootguides.banking_app.repository.AccountRepo;
import com.springbootguides.banking_app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired(required=true)
	private AccountRepo accountRepo;

//	public AccountServiceImpl(AccountRepo accountRepo) {
//		this.accountRepo = accountRepo;
//	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepo.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepo
				.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRepo
				.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAcc = accountRepo.save(account);
		return AccountMapper.mapToAccountDto(savedAcc);
	}

	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = accountRepo
				.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficinet amount");
		}
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAcc = accountRepo.save(account);
		return  AccountMapper.mapToAccountDto(savedAcc);
	}

	@Override
	public List<AccountDto> getAllAcounts() {
		List<Account> account = accountRepo.findAll();
		return account.stream().map((acc) -> AccountMapper.mapToAccountDto(acc)).collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		Account account = accountRepo
				.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		accountRepo.delete(account);	
	}

}
