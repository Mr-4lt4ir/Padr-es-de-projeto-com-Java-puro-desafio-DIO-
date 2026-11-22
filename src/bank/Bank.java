package bank;

import bank.overdraft.OverdraftPolicy;
import bank.user.ID;
import bank.user.NBankAccount;
import exceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.Map;

//implementação do singleton
public class Bank {
    private final Map<ID, NBankAccount> accounts = new HashMap<>();

    private Bank(){}

    private static class Holder{static final Bank INSTANCE = new Bank();}
    public static Bank getInstance(){
        return Holder.INSTANCE;
    }

    //cria uma nova conta e adiciona ela na memória, incrementando o nextID
    private int nextID = 1;
    public NBankAccount openAccount(double initialBalance, OverdraftPolicy policy){
        NBankAccount account = new NBankAccount(nextID, initialBalance, policy);
        accounts.put(account.getId(), account);
        nextID++;
        return account;
    }

    //busca por uma conta na memória através de um id, e se ele não encontra lança uma exceção
    public NBankAccount getAccount(ID id) throws AccountNotFoundException {
        NBankAccount account = accounts.get(id);
        if(account == null) throw new AccountNotFoundException("Conta não encontrada");
        return account;
    }
}
