/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sandramalwina.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Account;
import model.Customer;
import dao.BankServiceDAO;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import model.BalanceRequest;
import model.CreateAccountRequest;
import model.TransferRequest;
import model.WithdrawRequest;

/**
 *
 * @author herrerg
 */
@Path("bank")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankController {

    private static Map<String, Customer> bankAccounts = BankServiceDAO.getBankAccounts();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/withdraw")
    public Response withdraw(WithdrawRequest request) {

        Customer currentCustomer = bankAccounts.get(request.getEmail());
        List<Account> currentCustomerAccounts = currentCustomer.getAccountsList();

        for (int i = 0; i < currentCustomerAccounts.size(); i++) {

            if (UUID.fromString(request.getAccountNumber()).equals(currentCustomerAccounts.get(i).getAccountNumber())) {

                double balance = currentCustomerAccounts.get(i).getBalance();
                currentCustomerAccounts.get(i).setBalance(balance - request.getAmount());
            }

        }
        return Response.status(200).entity(currentCustomer).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lodgement")
    public Response lodgment(WithdrawRequest request) {

        Customer currentCustomer = bankAccounts.get(request.getEmail());
        List<Account> currentCustomerAccounts = currentCustomer.getAccountsList();

        for (int i = 0; i < currentCustomerAccounts.size(); i++) {

            if (UUID.fromString(request.getAccountNumber()).equals(currentCustomerAccounts.get(i).getAccountNumber())) {

                double balance = currentCustomerAccounts.get(i).getBalance();
                currentCustomerAccounts.get(i).setBalance(balance + request.getAmount());
            }

        }
        return Response.status(200).entity(currentCustomer).build();

    }

    @POST
    @Path("/transfer")
    public Response transfer(TransferRequest request) {
        Customer currentCustomer = bankAccounts.get(request.getEmailOrigin());
        Customer receivingCustomer = bankAccounts.get(request.getEmailDestination());
        List<Account> currentCustomerAccounts = currentCustomer.getAccountsList();
        List<Account> receivingCustomerAccounts = receivingCustomer.getAccountsList();

        //Initialized to -1 as uknown value, as it will represent an array index [0- infinite]
        int indexAccountOrigin = -1;

        //In this first for we dont extract the money yet, as this will be done after finding the destination account in the second loop, using 
        //the variable indexAccountOrigin
        for (int i = 0; i < currentCustomerAccounts.size(); i++) {
            if (UUID.fromString(request.getAccountOrigin()).equals(currentCustomerAccounts.get(i).getAccountNumber())) {
                double balance = currentCustomerAccounts.get(i).getBalance();
                indexAccountOrigin = i;
            }

        }

        for (int i = 0; i < receivingCustomerAccounts.size(); i++) {
            if (UUID.fromString(request.getAccountDestination()).equals(receivingCustomerAccounts.get(i).getAccountNumber())) {
                double balance = receivingCustomerAccounts.get(i).getBalance();
                receivingCustomerAccounts.get(i).setBalance(balance + request.getAmount());
                currentCustomerAccounts.get(indexAccountOrigin).setBalance(balance - request.getAmount());
            }
        }
        return Response.status(200).entity(currentCustomer).build();

    }

    @POST
    @Path("/create")
    public Response createUserAccount(CreateAccountRequest request) {

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setCorrespondanceAddress(request.getAddress());

        Account account = new Account();
        account.setAccountName(request.getAccountName());
        customer.setAccountsList(Arrays.asList(account));
        bankAccounts.put(request.getEmail(), customer);
        System.out.println(bankAccounts.size());

        return Response.status(200).entity(customer).build();
    }

    @POST
    @Path("/balance")
    public Response balance(BalanceRequest request) {
        Customer currentCustomer = bankAccounts.get(request.getEmail());

        for (int i = 0; i < currentCustomer.getAccountsList().size(); i++) {
            if (UUID.fromString(request.getAccountNumber()).equals(currentCustomer.getAccountsList().get(i).getAccountNumber())) {
                return Response.status(200).entity(currentCustomer.getAccountsList().get(i).getBalance()).build();
            }
        }

        return Response.status(404).entity("Account number " + request.getAccountNumber() + " not found").build();
    }

}
