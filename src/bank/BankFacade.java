package bank;

import bank.overdraft.OverdraftPolicy;
import bank.overdraft.PremiumOverdarftPolicyy;
import bank.overdraft.StandardOverdraftPolicy;
import bank.user.AccountType;
import bank.user.ID;
import bank.user.NBankAccount;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

//implementação do facade
public class BankFacade {
    private final Bank bank = Bank.getInstance();

    //Abre a nova conta
    public void openAccount(double initialBalance, AccountType type){
        OverdraftPolicy policy = switch (type){
            case PREMIUM -> new PremiumOverdarftPolicyy();
            case STANDARD -> new StandardOverdraftPolicy();
        };
        NBankAccount account = bank.openAccount(initialBalance, policy);
        System.out.printf("Conta: %s aberta com R$ %.2f\n", account.getId(),
                account.getBalance());
    }

    //deposita o dinheiro na conta
    public void deposit(ID id, double amount) throws AccountNotFoundException, InvalidAmountException {
        bank.getAccount(id).depositMoney(amount);
        System.out.printf("Depósito de R$ %.2f realizado na conta %s\n", amount, bank.getAccount(id).getId());
    }

    //saca o dinheiro da conta
    public void withdraw(ID id, double amount) throws AccountNotFoundException, InvalidAmountException,
            InsufficientFundsException {
        bank.getAccount(id).withdrawMoney(amount);
        System.out.printf("Saque de R$ %.2f realizada na conta %s\n", amount, bank.getAccount(id).getId());
    }

    //paga uma conta através de uma conta
    public void payBill(ID id, double amount) throws AccountNotFoundException, InvalidAmountException,
            InsufficientFundsException {
        boolean usedOverDraft = bank.getAccount(id).paymentSlip(amount);
        System.out.printf("Conta de R$ %.2f paga com sucesso na conta %s %s\n", amount, bank.getAccount(id).getId(),
                usedOverDraft ? "utilizando o cheque especial" : "");
    }

    //transfere o dinheiro de uma conta para outra
    public void transfer(ID from, ID to, double amount) throws AccountNotFoundException, InvalidAmountException,
            InsufficientFundsException {
        if(from.equals(to)) throw new InvalidAmountException("contas idênticas para fazer transferência");
        NBankAccount origin = bank.getAccount(from);
        NBankAccount destination = bank.getAccount(to);
        origin.withdrawMoney(amount);
        destination.depositMoney(amount);
        System.out.printf("Transferência de %.2f realizada da contaa %s para %s\n", amount, origin.getId(),
                destination.getId());
    }

    //alguns estados da conta
    //imprime o valor depositado na conta e o valor total disponível
    public void checkBalance(ID id) throws AccountNotFoundException {
        System.out.printf("Saldo corrente: R$ %.2f\tSaldo total: R$ %.2f\n",
                bank.getAccount(id).getBalance(), bank.getAccount(id).availableBalance());
    }

    //imprime o valor do cheque especial
    public void checkOverdraftFacility(ID id) throws AccountNotFoundException {
        System.out.printf("Valor do cheque especial: R$ %.2f\n", bank.getAccount(id).getOverdraftFacility());
    }

    //imprime se o cheque especial está sendo utilizado
    public void checkOF(ID id) throws AccountNotFoundException {
        if(bank.getAccount(id).isUsingOF()) System.out.println("O cheque especial está sendo utilizado");
        else System.out.println("O cheque especial não está sendo utilizado");
    }
}
