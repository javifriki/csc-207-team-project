package interface_adaptor.add_asset_and_liability;

import entity.AssetAndLiability;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateState;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateViewModel;
import use_case.add_asset_and_liability.AddAssetAndLiabilityOutputBoundary;
import use_case.add_asset_and_liability.AddAssetAndLiabilityOutputData;

public class AddAssetAndLiabilityPresenter implements AddAssetAndLiabilityOutputBoundary {
    private final AddAssetAndLiabilityViewModel addAssetAndLiabilityViewModel;

    public  AddAssetAndLiabilityPresenter(AddAssetAndLiabilityViewModel addAssetAndLiabilityViewModel) {
        this.addAssetAndLiabilityViewModel = addAssetAndLiabilityViewModel;
    }

    public void prepareAssetAndLiabilitySuccessView (AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData){
        // with a popup, display the success message with info of the asset/liability
        double initialAmount = addAssetAndLiabilityOutputData.getInitialAmount();
        AssetAndLiability.Type type = addAssetAndLiabilityOutputData.getType();
        AssetAndLiability.RatePeriod ratePeriod = addAssetAndLiabilityOutputData.getRatePeriod();
        double interestRate = addAssetAndLiabilityOutputData.getInterestRate();

        AssetAndLiability assetAndLiability = addAssetAndLiabilityOutputData.getAssetAndLiability();

        AddAssetAndLiabilityState addAssetAndLiabilityState = this.addAssetAndLiabilityViewModel.getState();
        addAssetAndLiabilityState.setPopupMessage(
                type.toString() + " added successfully"
                        + "\nWith initial amount: " + initialAmount
                        + "\nRate Period: " + ratePeriod
                        + "\nInterest Rate: " + interestRate
        );

        addAssetAndLiabilityState.addAssetAndLiabilityToList(assetAndLiability);

        this.addAssetAndLiabilityViewModel.firePropertyChange("asset/liability success");
    }

    public void prepareAssetAndLiabilityFailView (String errorMessage){
        // with a popup, display the errorMessage as to why the transaction failed.
        AddAssetAndLiabilityState addAssetAndLiabilityState = this.addAssetAndLiabilityViewModel.getState();
        addAssetAndLiabilityState.setPopupMessage(errorMessage);
        this.addAssetAndLiabilityViewModel.firePropertyChange("asset/liability fail");
    }
}
