package bank.overdraft;

public class StandardOverdraftPolicy implements OverdraftPolicy {

    @Override
    public double limitFor(double initialBalance) {
        return initialBalance <= 500 ? 50 : initialBalance / 2;
    }

    @Override
    public double feeFor(double usedAmount) {
        return usedAmount * 0.2;
    }
}
