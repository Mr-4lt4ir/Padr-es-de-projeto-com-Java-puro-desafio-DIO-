import bank.BankFacade;
import bank.user.AccountType;
import bank.user.ID;
import exceptions.BankException;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    private final static BankFacade bank = new BankFacade();
    private final static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args){
        bank.openAccount(1500, AccountType.STANDARD);
        bank.openAccount(3200, AccountType.PREMIUM);

        int inUser;
        do{
            System.out.println("====Digite sua opção====");
            System.out.println("1 - Consultar saldo");
            System.out.println("2 - Consultar cheque especial");
            System.out.println("3 - Depositar dinheiro");
            System.out.println("4 - Sacar dinheiro");
            System.out.println("5 - Pagar um boleto");
            System.out.println("6 - Transferir valor de uma conta para outra");
            System.out.println("7 - Verificar se a conta está usando cheque especial");
            System.out.println("8 - Encerrar programa");
            System.out.println("=========================");

            inUser = scanner.nextInt();
            switch (inUser){
                case 1 -> checkBalance();
                case 2 -> checkOverdraftFacility();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> pay();
                case 6 -> transfer();
                case 7 -> checkOF();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida!");
            }
        }while(true);
    }

    private static void checkBalance(){
        System.out.println("Digite o Id da conta");
        ID id = new ID(scanner.nextInt());
        try{
            bank.checkBalance(id);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void checkOverdraftFacility(){
        System.out.println("Digite o Id da conta");
        ID id = new ID(scanner.nextInt());
        try{
            bank.checkOverdraftFacility(id);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit(){
        System.out.println("Digite o Id da conta");
        ID id = new ID(scanner.nextInt());

        System.out.println("Digite o valor a ser depositado:");
        double val = scanner.nextDouble();
        try{
            bank.deposit(id, val);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void withdraw(){
        System.out.println("Digite o Id da conta");
        ID id = new ID(scanner.nextInt());

        System.out.println("Digite o valor a ser sacado:");
        double val = scanner.nextDouble();
        try{
            bank.withdraw(id, val);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void pay(){
        System.out.println("Digite o Id da conta");
        ID id = new ID(scanner.nextInt());

        System.out.println("Digite o valor da conta a ser paga:");
        double val = scanner.nextDouble();
        try{
            bank.payBill(id, val);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void transfer(){
        System.out.println("Digite o Id da primeira conta");
        ID id1 = new ID(scanner.nextInt());
        System.out.println("Digite o Id da segunda conta");
        ID id2 = new ID(scanner.nextInt());
        System.out.println("Digite o valor a ser depositado:");
        double val = scanner.nextDouble();
        try{
            bank.transfer(id1, id2, val);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void checkOF(){
        System.out.println("Digite o Id da conta");
        ID id = new ID(scanner.nextInt());
        try{
            bank.checkOF(id);
        }catch (BankException ex){
            System.out.println(ex.getMessage());
        }
    }
}
