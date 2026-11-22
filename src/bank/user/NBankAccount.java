package bank.user;

import bank.overdraft.OverdraftPolicy;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

public class NBankAccount {
    private final OverdraftPolicy policy;
    private final double limitOverdraftFacility;
    private double balance;
    private final ID id;

    public NBankAccount(int id, double balance, OverdraftPolicy policy){
        this.balance = balance < 0 ? 0 : balance; //se o valor depositado for negativo ele inicia a conta zerada.
        this.policy = policy;
        this.limitOverdraftFacility = policy.limitFor(this.balance);
        this.id = new ID(id);
    }

    public double getBalance(){
        return balance;
    }

    public ID getId() {
        return id;
    }

    //consultar saldo
    public double availableBalance(){
        return balance + limitOverdraftFacility;
    }

    //consultar cheque especial
    public double getOverdraftFacility(){
        return limitOverdraftFacility;
    }

    //Depositar o dinheiro
    public void depositMoney(double money) throws InvalidAmountException {
        if(money<1){
            throw new InvalidAmountException("Valor do depósito inválido");
        }else if(balance < 0){ //usando o cheque especial.
            double usado = -balance;
            double tax = policy.feeFor(usado);
            balance += money;
            if(balance >= tax){ //cobrando a taxa de 20% assim que o valor do depósito for maior que a taxa.
                balance -= tax;
            }
        }else balance += money; //deposita normalmente, pois não está usando o limite
    }

    //sacar dinheiro
    public void withdrawMoney(double money) throws InvalidAmountException, InsufficientFundsException {
        if(money<1){
            throw new InvalidAmountException("Valor do saque inválido");
        }
        if(money > availableBalance()){
            throw new InsufficientFundsException("Saldo insuficiente");
        }
        balance -= money;
    }

    //pagar boleto
    public boolean paymentSlip(double money) throws InvalidAmountException, InsufficientFundsException {
        withdrawMoney(money);
        return isUsingOF();
    }

    //verificar se conta utiliza cheque especial
    public boolean isUsingOF(){
        return balance < 0;
    }
}
