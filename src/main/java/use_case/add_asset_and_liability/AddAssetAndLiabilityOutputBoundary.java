
package use_case.add_asset_and_liability;

public interface AddAssetAndLiabilityOutputBoundary {
    void prepareAssetAndLiabilitySuccessView (AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData);
    void prepareAssetAndLiabilityFailView (String errorMessage);
}
