/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import model.Account;
import model.Customer;

/**
 *
 * @author herrerg
 */

public class BankServiceDAO {
       
    public static Map<String, Customer> getBankAccounts(){
        Customer c = new Customer();
        c.setName("pepe");
        c.setCorrespondanceAddress("email");
        c.setEmail("email");
        c.setPassword("pwd");
        Account account = new Account();
        Account a2 = new Account();
        a2.setAccountNumber(UUID.fromString("1af7cdfa-9223-48dc-a051-3adc04c88a26"));
        account.setAccountNumber(UUID.fromString("1af7cdfa-9223-48dc-a051-3adc04c88a25"));
        c.setAccountsList(Arrays.asList(account, a2));
        Map<String, Customer> bank = new HashMap();
        bank.put("pepe", c);
    return bank;
    }
       
}
