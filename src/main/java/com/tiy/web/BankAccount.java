package com.tiy.web;

/**
 * Created by dbashizi on 1/5/17.
 */
public class BankAccount {
    private String name;
    private double balance;
    private boolean overdraft;
    private AccountHolder accountHolder;

    public BankAccount(String name, double balance, boolean overdraft, AccountHolder accountHolder) {
        this.name = name;
        this.balance = balance;
        this.overdraft = overdraft;
        this.accountHolder = accountHolder;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isOverdraft() {
        return overdraft;
    }

    public void setOverdraft(boolean overdraft) {
        this.overdraft = overdraft;
    }
}
