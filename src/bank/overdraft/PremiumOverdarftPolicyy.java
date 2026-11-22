package bank.overdraft;

//cheque especial de conta premium
public class PremiumOverdarftPolicyy implements OverdraftPolicy{
    @Override
    public double limitFor(double initialBalance) {
        return initialBalance; //100% do saldo
    }

    @Override
    public double feeFor(double usedAmount) {
        return usedAmount * 0.1; //taxa menor
    }
}
