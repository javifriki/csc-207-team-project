package entity;
import java.time.LocalDate;

public class AssetAndLiability {

    public enum Type {
        ASSET,
        LIABILITY
    }

    public enum RatePeriod {
        MONTHLY,
        QUARTERLY,
        ANNUALLY
    }

    private Type type;
    private RatePeriod ratePeriod;
    private String name;
    private String ID;
    private double amount;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private double interestRate;

    public AssetAndLiability(String name, Type type, RatePeriod ratePeriod, double amount, String ID,
                             LocalDate dateCreated, LocalDate dateUpdated, double interestRate) {
        this.name = name;
        this.type = type;
        this.ratePeriod = ratePeriod;
        this.amount = amount;
        this.ID = ID;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.interestRate = interestRate;
    }

    public void applyRate(int num) {
        this.amount *= num;
    }

    // Getters

    public String getName() { return name; }

    public Type getType() { return type; }

    public String getID() { return ID; }

    public double getAmount() { return amount; }

    public LocalDate getDateCreated() { return dateCreated; }

    public double getInterestRate() { return interestRate; }

    public RatePeriod getRatePeriod() {
        return ratePeriod;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    // Setters

    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

}