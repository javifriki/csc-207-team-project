package entity;
import java.time.LocalDate;

public class AssetAndLiability {

    public enum Type {
        ASSET,
        LIABILITY
    }

    private Type type;
    private String name;
    private String ID;
    private double amount;
    private LocalDate dateCreated;
    private double interestRate;

    // NOTE: implement Builder pattern!

    public AssetAndLiability(String name, Type type, double amount, String ID,
                             LocalDate dateCreated, double interestRate) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.ID = ID;
        this.dateCreated = dateCreated;
        this.interestRate = interestRate;
    }

    // Getters

    public String getName() { return name; }

    public Type getType() { return type; }

    public String getID() { return ID; }

    public double getAmount() { return amount; }

    public LocalDate getDateCreated() { return dateCreated; }

    public double getInterestRate() { return interestRate; }

}
