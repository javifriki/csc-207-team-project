package use_case.asset_and_liability_apply_rate;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRatePresenter;
import use_case.add_asset_and_liability.AssetAndLiabilityDataAccessInterface;

import java.time.LocalDate;
import java.time.Period;

public class AssetAndLiabilityApplyRateInteractor implements AssetAndLiabilityApplyRateInputBoundary{

    private final AssetAndLiabilityDataAccessInterface assetAndLiabilityDataAccessObject;
    private final AssetAndLiabilityApplyRateOutputBoundary  assetAndLiabilityApplyRatePresenter;

    public AssetAndLiabilityApplyRateInteractor (AssetAndLiabilityDataAccessInterface assetAndLiabilityDataAccessObject,
                                                 AssetAndLiabilityApplyRateOutputBoundary assetAndLiabilityApplyRatePresenter) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
        this.assetAndLiabilityApplyRatePresenter = assetAndLiabilityApplyRatePresenter;
    }

    @Override
    public void execute(AssetAndLiabilityApplyRateInputData assetAndLiabilityApplyRateInputData) {

        final String[] assetAndLiabilityIDs = assetAndLiabilityApplyRateInputData.getIDs();

        // Updates current amount for all assets and liabilities
        for (String ID : assetAndLiabilityIDs) {
            AssetAndLiability assetAndLiability = assetAndLiabilityDataAccessObject.getAssetAndLiability(ID);

            LocalDate dateCreated = assetAndLiability.getDateCreated();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(dateCreated, currentDate);
            AssetAndLiability.RatePeriod ratePeriod = assetAndLiability.getRatePeriod();

            if (ratePeriod == AssetAndLiability.RatePeriod.MONTHLY) {
                assetAndLiability.applyRate(Math.abs(period.getMonths()) + Math.abs(period.getYears() * 12));
            } else if (ratePeriod == AssetAndLiability.RatePeriod.QUARTERLY) {
                assetAndLiability.applyRate((Math.abs(period.getMonths()) + Math.abs(period.getYears() * 12)) / 3);
            } else {
                assetAndLiability.applyRate(Math.abs(period.getYears()));
            }

            assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);
        }

        final AssetAndLiabilityApplyRateOutputData assetAndLiabilityApplyRateOutputData = new AssetAndLiabilityApplyRateOutputData(
                "successfully modified amounts",
                assetAndLiabilityDataAccessObject.getAllAssetAndLiabilities()
        );

        assetAndLiabilityApplyRatePresenter.prepareAssetAndLiabilitySuccessView(assetAndLiabilityApplyRateOutputData);

    }
}
