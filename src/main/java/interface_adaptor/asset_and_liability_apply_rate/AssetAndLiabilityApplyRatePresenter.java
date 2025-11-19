package interface_adaptor.asset_and_liability_apply_rate;

import entity.AssetAndLiability;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateOutputBoundary;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateOutputData;

import java.time.LocalDate;

public class AssetAndLiabilityApplyRatePresenter implements AssetAndLiabilityApplyRateOutputBoundary {
    private AssetAndLiabilityApplyRateViewModel assetAndLiabilityApplyRateViewModel;

    public AssetAndLiabilityApplyRatePresenter(AssetAndLiabilityApplyRateViewModel assetAndLiabilityApplyRateViewModel) {
        this.assetAndLiabilityApplyRateViewModel = assetAndLiabilityApplyRateViewModel;
    }

    @Override
    public void prepareAssetAndLiabilitySuccessView(AssetAndLiabilityApplyRateOutputData assetAndLiabilityApplyRateOutputData) {
        // with a popup, display the success message with info of the transaction
        double amount = assetAndLiabilityApplyRateOutputData.getAssetAndLiabilityAmount();
        AssetAndLiability.Type type = assetAndLiabilityApplyRateOutputData.getType();
        AssetAndLiability.RatePeriod ratePeriod = assetAndLiabilityApplyRateOutputData.getRatePeriod();
        double interestRate = assetAndLiabilityApplyRateOutputData.getInterestRate();
        LocalDate dateUpdated = assetAndLiabilityApplyRateOutputData.getDateUpdated();
        LocalDate endDate = assetAndLiabilityApplyRateOutputData.getEndDate();

        AssetAndLiabilityApplyRateState assetAndLiabilityApplyRateState = this.assetAndLiabilityApplyRateViewModel.getState();
        assetAndLiabilityApplyRateState.setPopupMessage(
                type.toString() + " successfully applied rate"
                        + "with amount " + amount
                        + "\nRate Period: " + ratePeriod
                        + "\nInterest Rate: " + interestRate
                        + "\nDate Updated From: " + dateUpdated
                        + "\nDate Updated To: " + endDate

        );

        this.assetAndLiabilityApplyRateViewModel.firePropertyChange("asset/liability success");
    }

    public void prepareAssetAndLiabilityFailView(String errorMessage) {
        // with a popup, display the errorMessage as to why the transaction failed.
        AssetAndLiabilityApplyRateState assetAndLiabilityApplyRateState = this.assetAndLiabilityApplyRateViewModel.getState();
        assetAndLiabilityApplyRateState.setPopupMessage(errorMessage);
        this.assetAndLiabilityApplyRateViewModel.firePropertyChange("asset/liability fail");
    }
}
