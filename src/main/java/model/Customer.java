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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sandr
 */
public class Customer {
    
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorrespondanceAddress() {
        return correspondanceAddress;
    }

    public void setCorrespondanceAddress(String correspondanceAddress) {
        this.correspondanceAddress = correspondanceAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(List<Account> accountsList) {
        this.accountsList = accountsList;
    }
    String correspondanceAddress;
    String email;
    String password;
    List<Account> accountsList = new ArrayList();
    
}
