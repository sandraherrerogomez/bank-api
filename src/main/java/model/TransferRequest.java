/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author herrerg
 */
@XmlRootElement
public class TransferRequest {

    public String getEmailOrigin() {
        return emailOrigin;
    }

    public void setEmailOrigin(String emailOrigin) {
        this.emailOrigin = emailOrigin;
    }

    public String getEmailDestination() {
        return emailDestination;
    }

    public void setEmailDestination(String emailDestination) {
        this.emailDestination = emailDestination;
    }

    public String getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(String accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(String accountDestination) {
        this.accountDestination = accountDestination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    private String emailOrigin;
    private String emailDestination;
    private String accountOrigin;
    private String accountDestination;
    private double amount;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
