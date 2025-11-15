package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

public class AddAssetAndLiabilityOutputData {
    private String message;
    private AssetAndLiability.Type type;
    private double assetAndLiabilityAmount;
    private double interestRate;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AddAssetAndLiabilityOutputData(String message, AssetAndLiability.Type type,
                                          double assetAndLiabilityAmount, double interestRate) {
        this.message = message;
        this.type = type;
        this.assetAndLiabilityAmount = assetAndLiabilityAmount;
        this.interestRate = interestRate;
    }

    public String getMessage() {
        return this.message;
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
}