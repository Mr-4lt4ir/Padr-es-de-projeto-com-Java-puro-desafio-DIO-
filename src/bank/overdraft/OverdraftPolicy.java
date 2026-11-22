package bank.overdraft;

public interface OverdraftPolicy {
    double limitFor(double initialBalance);   // limite concedido na abertura
    double feeFor(double usedAmount);         // taxa cobrada sobre o valor usado
}
