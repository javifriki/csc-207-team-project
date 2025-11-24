package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

import java.time.LocalDate;

public class AddAssetAndLiabilityInputData {
    private final AssetAndLiability.Type type;
    private final String name;
    private final String ID;
    private final LocalDate dateCreated;
    private final double initialAmount;
    private final double interestRate;
    private final AssetAndLiability.RatePeriod ratePeriod;

    public AddAssetAndLiabilityInputData(String name, AssetAndLiability.Type type, String ID,
                                         LocalDate dateCreated, double initialAmount, double interestRate,
                                         AssetAndLiability.RatePeriod ratePeriod) {
        this.name = name;
        this.type = type;
        this.initialAmount = initialAmount;
        this.dateCreated = dateCreated;
        this.ID = ID;
        this.interestRate = interestRate;
        this.ratePeriod = ratePeriod;
    }

    // Getters

    public String getName() { return name; }

    public AssetAndLiability.Type getType() { return type; }

    public String getID() { return ID; }

    public double getInitialAmount() { return initialAmount; }

    public double getInterestRate() { return interestRate; }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public AssetAndLiability.RatePeriod getRatePeriod() {
        return ratePeriod;
    }
}