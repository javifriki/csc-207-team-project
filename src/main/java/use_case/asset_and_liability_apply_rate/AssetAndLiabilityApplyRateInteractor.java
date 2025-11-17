package use_case.asset_and_liability_apply_rate;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;

import java.time.LocalDate;
import java.time.Period;

public class AssetAndLiabilityApplyRateInteractor implements AssetAndLiabilityApplyRateInputBoundary{

    private final AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject;
    // presenter

    public AssetAndLiabilityApplyRateInteractor (AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
    }

    @Override
    public void execute(AssetAndLiabilityApplyRateInputData assetAndLiabilityApplyRateInputData) {

        final String assetAndLiabilityID = assetAndLiabilityApplyRateInputData.getID();
        final LocalDate endDate = assetAndLiabilityApplyRateInputData.getEndDate();

        AssetAndLiability assetAndLiability = assetAndLiabilityDataAccessObject.getAssetAndLiability(assetAndLiabilityID);

        LocalDate dateUpdated = assetAndLiability.getDateUpdated();
        Period period = Period.between(dateUpdated, endDate);
        AssetAndLiability.RatePeriod ratePeriod = assetAndLiability.getRatePeriod();

        if (ratePeriod == AssetAndLiability.RatePeriod.MONTHLY && period.getMonths() != 0) {
            int months = Math.abs(period.getMonths());
            for (int i = 1; i <= months; i++) {
                assetAndLiability.applyRate(months);
            }
            assetAndLiability.setDateUpdated(assetAndLiability.getDateUpdated().plusMonths(months));
        } else if (ratePeriod == AssetAndLiability.RatePeriod.QUARTERLY && (Math.abs(period.getMonths())) >= 3) {
            int quarters = (Math.abs(period.getMonths())) / 3;
            for (int i = 1; i <= quarters; i++) {
                assetAndLiability.applyRate(quarters);
            }
            assetAndLiability.setDateUpdated(assetAndLiability.getDateUpdated().plusMonths(quarters * 3));
        } else if (ratePeriod == AssetAndLiability.RatePeriod.ANNUALLY && period.getYears() != 0) {
            int years = Math.abs(period.getYears());
            for (int i = 1; i <= years; i++) {
                assetAndLiability.applyRate(years);
            }
            assetAndLiability.setDateUpdated(assetAndLiability.getDateUpdated().plusMonths(years));
        } else {
            // PRINT ERROR MESSAGE: NOT ENOUGH TIME TO APPLY RATE YET!!!
        }

        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);

        final AssetAndLiabilityApplyRateOutputData assetAndLiabilityApplyRateOutputData = new AssetAndLiabilityApplyRateOutputData(
                "successfully modified amount",
                assetAndLiability.getType(),
                assetAndLiability.getAmount(),
                dateUpdated,
                endDate,
                assetAndLiability.getInterestRate(),
                assetAndLiability.getRatePeriod()
        );

        // presenter call

    }
}
