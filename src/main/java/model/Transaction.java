/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author herrerg
 */
import java.util.Date;

/**
 *
 * @author sandr
 */
public class Transaction {
    private double balance;
    private String transactionType;
    private Date date;
    private String description;
    private double postBalance;
    
    
    
    public void deposit(double amount){
        balance=balance+amount;
    }
    
    public void withdraw(double amount){
        balance=balance-amount;
    }
    
}
