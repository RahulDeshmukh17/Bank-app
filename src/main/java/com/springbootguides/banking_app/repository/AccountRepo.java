package com.springbootguides.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootguides.banking_app.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>{

}
