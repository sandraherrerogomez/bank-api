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
import java.util.ArrayList;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import model.BalanceRequest;
import model.CreateAccountRequest;
import model.Transaction;
import model.TransferRequest;
import model.UserRequest;
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
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/withdraw")
    public Response withdraw(WithdrawRequest request) {

        Customer currentCustomer = bankAccounts.get(request.getEmail());

        if (currentCustomer == null) {
            return Response.status(404).entity("customer not found").build();
        }

        if (false == request.getPassword().equals(currentCustomer.getPassword())) {

            return Response.status(404).entity("Incorrect password entered").build();
        }

        List<Account> currentCustomerAccounts = currentCustomer.getAccountsList();
        for (int i = 0; i < currentCustomerAccounts.size(); i++) {

            if (UUID.fromString(request.getAccountNumber()).equals(currentCustomerAccounts.get(i).getAccountNumber())) {

                double balance = currentCustomerAccounts.get(i).getBalance();
                currentCustomerAccounts.get(i).setBalance(balance - request.getAmount());
                Transaction transaction = new Transaction(balance, "withdraw", "withdraw", balance - request.getAmount());
                currentCustomerAccounts.get(i).getTransactionsList().add(transaction);
            }
        }
        return Response.status(200).entity(currentCustomer).build();

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lodgement")
    public Response lodgment(WithdrawRequest request) {

        Customer currentCustomer = bankAccounts.get(request.getEmail());

        if (currentCustomer == null) {
            return Response.status(404).entity("customer not found").build();
        }

        if (false == request.getPassword().equals(currentCustomer.getPassword())) {

            return Response.status(404).entity("Incorrect password entered").build();
        }

        List<Account> currentCustomerAccounts = currentCustomer.getAccountsList();

        for (int i = 0; i < currentCustomerAccounts.size(); i++) {

            if (UUID.fromString(request.getAccountNumber()).equals(currentCustomerAccounts.get(i).getAccountNumber())) {

                double balance = currentCustomerAccounts.get(i).getBalance();
                currentCustomerAccounts.get(i).setBalance(balance + request.getAmount());
                Transaction transaction = new Transaction(balance, "lodgement", "lodgement", balance + request.getAmount() );
                currentCustomerAccounts.get(i).getTransactionsList().add(transaction);
            }

        }
        return Response.status(200).entity(currentCustomer).build();

    }

    @PUT
    @Path("/transfer")
    public Response transfer(TransferRequest request) {
        Customer currentCustomer = bankAccounts.get(request.getEmailOrigin());
        Customer receivingCustomer = bankAccounts.get(request.getEmailDestination());
        boolean accountOriginFound = false;
        boolean accountDestinationFound = false;

        if (currentCustomer == null || receivingCustomer == null) {
            return Response.status(404).entity("customer not found").build();
        }

        if (false == request.getPassword().equals(currentCustomer.getPassword())) {

            return Response.status(404).entity("Incorrect password entered").build();
        }

        List<Account> currentCustomerAccounts = currentCustomer.getAccountsList();
        List<Account> receivingCustomerAccounts = receivingCustomer.getAccountsList();

        //Initialized to -1 as uknown value, as it will represent an array index [0- infinite]
        int indexAccountOrigin = -1;
        double balanceOriginAccount = 0;

        //In this first for we dont extract the money yet, as this will be done after finding the destination account in the second loop, using 
        //the variable indexAccountOrigin
        for (int i = 0; i < currentCustomerAccounts.size(); i++) {
            if (UUID.fromString(request.getAccountOrigin()).equals(currentCustomerAccounts.get(i).getAccountNumber())) {
                balanceOriginAccount = currentCustomerAccounts.get(i).getBalance();
                indexAccountOrigin = i;
                accountOriginFound = true;
            }
        }

        for (int i = 0; i < receivingCustomerAccounts.size(); i++) {
            if (UUID.fromString(request.getAccountDestination()).equals(receivingCustomerAccounts.get(i).getAccountNumber())) {
                double balance = receivingCustomerAccounts.get(i).getBalance();
                
                //Updating receiving account balance and adding the transaction
                receivingCustomerAccounts.get(i).setBalance(balance + request.getAmount());
                Transaction transactionReceiving = new Transaction(balance, "transfer", "transfer from " + request.getAccountOrigin(), balance + request.getAmount() );
                receivingCustomerAccounts.get(i).getTransactionsList().add(transactionReceiving);
                
                //Updating origin account balance and adding the transaction
                currentCustomerAccounts.get(indexAccountOrigin).setBalance(balanceOriginAccount - request.getAmount());
                accountDestinationFound = true;
                Transaction transaction = new Transaction(balance, "transfer", "transfer to " + request.getAccountDestination(), balanceOriginAccount - request.getAmount() );
                currentCustomerAccounts.get(i).getTransactionsList().add(transaction);
            }
        }
        if(false == accountOriginFound || false == accountDestinationFound){
                        return Response.status(404).entity("Origin or Destination account has not been found").build();
        } else {
                    return Response.status(200).entity(currentCustomer).build();
        }
    }

    @POST
    @Path("/create")
    public Response createUserAccount(CreateAccountRequest request) {

        Customer customer = new Customer();
        if (bankAccounts.containsKey(request.getEmail())) {
            return Response.status(409).entity("This email is already in use").build();
        }

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setCorrespondanceAddress(request.getAddress());

        Account account = new Account();
        account.setAccountName(request.getAccountName());
        customer.setAccountsList(new ArrayList<Account>(Arrays.asList(account)));
        bankAccounts.put(request.getEmail(), customer);

        return Response.status(200).entity(customer).build();
    }

    @POST
    @Path("/addAccount")
    public Response addUserAccount(CreateAccountRequest request) {

        Customer customer = bankAccounts.get(request.getEmail());

        if (customer == null) {
            return Response.status(404).entity("account not found").build();
        }

        if (false == request.getPassword().equals(customer.getPassword())) {

            return Response.status(404).entity("Incorrect password entered").build();
        }
        Account account = new Account();
        account.setAccountName(request.getAccountName());
        List<Account> accounts = customer.getAccountsList();
        accounts.add(account);
        customer.setAccountsList(accounts);
        bankAccounts.put(request.getEmail(), customer);

        return Response.status(200).entity(customer).build();
    }

    @GET
    @Path("/balance")
    public Response balance(BalanceRequest request) {
        Customer currentCustomer = bankAccounts.get(request.getEmail());

        if (currentCustomer == null) {
            return Response.status(404).entity("account not found").build();
        }

        if (false == request.getPassword().equals(currentCustomer.getPassword())) {

            return Response.status(404).entity("Incorrect password entered").build();
        }

        for (int i = 0; i < currentCustomer.getAccountsList().size(); i++) {
            if (UUID.fromString(request.getAccountNumber()).equals(currentCustomer.getAccountsList().get(i).getAccountNumber())) {
                return Response.status(200).entity(currentCustomer.getAccountsList().get(i).getBalance()).build();
            }
        }

        return Response.status(404).entity("Account number " + request.getAccountNumber() + " not found").build();
    }

    @POST
    @Path("/log-in")
    public Response logIn(UserRequest request) {
        Customer currentCustomer = bankAccounts.get(request.getEmail());
        if (currentCustomer == null) {
            return Response.status(404).entity("account not found").build();
        }

        if (request.getPassword().equals(currentCustomer.getPassword())) {
            return Response.status(200).entity(currentCustomer).build();
        } else {
            return Response.status(404).entity("Incorrect password entered").build();
        }

    }

    @POST
    @Path("/customerInfo")
    public Response customerInfo(UserRequest request) {
        Customer currentCustomer = bankAccounts.get(request.getEmail());
        if (currentCustomer == null) {
            return Response.status(401).entity("account not found").build();
        }

        if (request.getPassword().equals(currentCustomer.getPassword())) {

            return Response.status(200).entity(currentCustomer).build();
        } else {
            return Response.status(401).entity("Incorrect password entered").build();

        }

    }

}
