package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

import java.time.LocalDate;

public class AddAssetAndLiabilityInputData {
    private final AssetAndLiability.Type type;
    private final String name;
    private final String ID;
    private final LocalDate dateCreated;
    private final double amount;
    private final double interestRate;

    public AddAssetAndLiabilityInputData(String name, AssetAndLiability.Type type, String ID,
                                         LocalDate dateCreated, double amount, double interestRate) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.ID = ID;
        this.interestRate = interestRate;
    }

    // Getters

    public String getName() { return name; }

    public AssetAndLiability.Type getType() { return type; }

    public String getID() { return ID; }

    public double getAmount() { return amount; }

    public double getInterestRate() { return interestRate; }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
}