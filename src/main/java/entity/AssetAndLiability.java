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
    private double initialAmount;
    private double currentAmount;
    private LocalDate dateCreated;
    private double interestRate;

    public AssetAndLiability(String name, Type type, RatePeriod ratePeriod, double initialAmount,
                             double currentAmount, String ID,
                             LocalDate dateCreated, double interestRate) {
        this.name = name;
        this.type = type;
        this.ratePeriod = ratePeriod;
        this.ID = ID;
        this.initialAmount = initialAmount;
        this.currentAmount = currentAmount;
        this.dateCreated = dateCreated;
        this.interestRate = interestRate;
    }

    public void applyRate (int num) {
        currentAmount = initialAmount;
        for (int i = 0; i < num; i++) {
            currentAmount *= (1 + interestRate);
        }
        currentAmount = (Math.round(currentAmount * 100.0)) / 100.0;
    }

    // Getters

    public String getName() { return name; }

    public Type getType() { return type; }

    public String getID() { return ID; }

    public double getInitialAmount() { return initialAmount; }

    public double getCurrentAmount() { return currentAmount; }

    public LocalDate getDateCreated() { return dateCreated; }

    public double getInterestRate() { return interestRate; }

    public RatePeriod getRatePeriod() {
        return ratePeriod;
    }

    // Setters

    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; }

}