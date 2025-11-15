package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

public class AddAssetAndLiabilityOutputData {
    private String message;
    private String accountNumber;
    private AssetAndLiability.Type type;
    private double assetAndLiabilityAmount;
    private double interestRate;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AddAssetAndLiabilityOutputData(String message, String accountNumber, AssetAndLiability.Type type,
                                          double assetAndLiabilityAmount, double interestRate) {
        this.message = message;
        this.accountNumber = accountNumber;
        this.type = type;
        this.assetAndLiabilityAmount = assetAndLiabilityAmount;
        this.interestRate = interestRate;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAccountNumber() {
        return this.accountNumber;
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
