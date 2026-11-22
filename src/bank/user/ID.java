package bank.user;

//gera um id aleatório para cada conta
public record ID(int id) {
    @Override
    public String toString() {
        return "ID: " + id;
    }
}
