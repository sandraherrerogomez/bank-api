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
    
    public Transaction(){
        
    }
    
    
    public Transaction(double prebalance, String transactionType, String description, double postbalance){
        this.balance = prebalance;
        this.transactionType = transactionType;
        this.description = description;
        this.postBalance = postbalance;
        this.date = new Date();
        
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(double postBalance) {
        this.postBalance = postBalance;
    }
    
    
}
