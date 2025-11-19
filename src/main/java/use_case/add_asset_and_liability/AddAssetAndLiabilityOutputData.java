package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

public class AddAssetAndLiabilityOutputData {
    private AssetAndLiability.Type type;
    private double assetAndLiabilityAmount;
    private double interestRate;
    private AssetAndLiability.RatePeriod ratePeriod;
    private AssetAndLiability assetAndLiability;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AddAssetAndLiabilityOutputData(AssetAndLiability.Type type,
                                          double assetAndLiabilityAmount, double interestRate,
                                          AssetAndLiability.RatePeriod ratePeriod,
                                          AssetAndLiability assetAndLiability) {
        this.type = type;
        this.assetAndLiabilityAmount = assetAndLiabilityAmount;
        this.interestRate = interestRate;
        this.ratePeriod = ratePeriod;
        this.assetAndLiability = assetAndLiability;
    }

    public AssetAndLiability.Type getType() {
        return type;
    }

    public double getAssetAndLiabilityAmount() {
        return assetAndLiabilityAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public AssetAndLiability.RatePeriod getRatePeriod() {
        return ratePeriod;
    }

    public AssetAndLiability getAssetAndLiability() {
        return assetAndLiability;
    }
}