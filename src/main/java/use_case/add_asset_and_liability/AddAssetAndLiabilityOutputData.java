package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

import java.util.List;

public class AddAssetAndLiabilityOutputData {
    private AssetAndLiability.Type type;
    private double initialAmount;
    private double interestRate;
    private AssetAndLiability.RatePeriod ratePeriod;
    private List<AssetAndLiability> assetAndLiabilityList;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AddAssetAndLiabilityOutputData(AssetAndLiability.Type type,
                                          double initialAmount, double interestRate,
                                          AssetAndLiability.RatePeriod ratePeriod,
                                          List<AssetAndLiability> assetAndLiabilityList) {
        this.type = type;
        this.initialAmount = initialAmount;
        this.interestRate = interestRate;
        this.ratePeriod = ratePeriod;
        this.assetAndLiabilityList = assetAndLiabilityList;
    }

    public AssetAndLiability.Type getType() {
        return type;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public AssetAndLiability.RatePeriod getRatePeriod() {
        return ratePeriod;
    }

    public  List<AssetAndLiability> getAssetAndLiabilityList() {
        return assetAndLiabilityList;
    }
}