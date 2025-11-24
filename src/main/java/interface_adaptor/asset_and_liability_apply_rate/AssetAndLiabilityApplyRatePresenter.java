package interface_adaptor.asset_and_liability_apply_rate;

import entity.AssetAndLiability;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateOutputBoundary;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateOutputData;

import java.time.LocalDate;
import java.util.List;

public class AssetAndLiabilityApplyRatePresenter implements AssetAndLiabilityApplyRateOutputBoundary {
    private AssetAndLiabilityApplyRateViewModel assetAndLiabilityApplyRateViewModel;

    public AssetAndLiabilityApplyRatePresenter(AssetAndLiabilityApplyRateViewModel assetAndLiabilityApplyRateViewModel) {
        this.assetAndLiabilityApplyRateViewModel = assetAndLiabilityApplyRateViewModel;
    }

    @Override
    public void prepareAssetAndLiabilitySuccessView(AssetAndLiabilityApplyRateOutputData assetAndLiabilityApplyRateOutputData) {
        // with a popup, display the success message with info of the transaction
        List<AssetAndLiability> assetAndLiabilityList = assetAndLiabilityApplyRateOutputData.getAssetAndLiabilityList();

        AssetAndLiabilityApplyRateState assetAndLiabilityApplyRateState = this.assetAndLiabilityApplyRateViewModel.getState();
        assetAndLiabilityApplyRateState.setPopupMessage(
                "successfully applied rate to all assets and liabilities"
        );

        assetAndLiabilityApplyRateState.setAllAssetAndLiabilityList(assetAndLiabilityList);
        this.assetAndLiabilityApplyRateViewModel.firePropertyChange("asset/liability success");
    }

    public void prepareAssetAndLiabilityFailView(String errorMessage) {
        // with a popup, display the errorMessage as to why the transaction failed.
        AssetAndLiabilityApplyRateState assetAndLiabilityApplyRateState = this.assetAndLiabilityApplyRateViewModel.getState();
        assetAndLiabilityApplyRateState.setPopupMessage(errorMessage);
        this.assetAndLiabilityApplyRateViewModel.firePropertyChange("asset/liability fail");
    }
}
