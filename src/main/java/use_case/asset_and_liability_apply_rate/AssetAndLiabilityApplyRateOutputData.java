package use_case.asset_and_liability_apply_rate;

import entity.AssetAndLiability;

import java.time.LocalDate;

public class AssetAndLiabilityApplyRateOutputData {
    private String message;
    private AssetAndLiability.Type type;
    private double assetAndLiabilityAmount;
    private LocalDate dateUpdated;
    private LocalDate endDate;
    private double interestRate;
    private AssetAndLiability.RatePeriod ratePeriod;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AssetAndLiabilityApplyRateOutputData(String message, AssetAndLiability.Type type,
                                                double assetAndLiabilityAmount, LocalDate dateUpdated,
                                                LocalDate endDate, double interestRate,
                                                AssetAndLiability.RatePeriod ratePeriod) {
        this.message = message;
        this.type = type;
        this.assetAndLiabilityAmount = assetAndLiabilityAmount;
        this.dateUpdated = dateUpdated;
        this.endDate = endDate;
        this.interestRate = interestRate;
        this.ratePeriod = ratePeriod;
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

    public AssetAndLiability.RatePeriod getRatePeriod() {
        return ratePeriod;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
