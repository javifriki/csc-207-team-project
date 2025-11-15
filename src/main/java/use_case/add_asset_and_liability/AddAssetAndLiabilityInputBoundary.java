package use_case.add_asset_and_liability;

import use_case.add_transaction.AddTransactionInputData;

public interface AddAssetAndLiabilityInputBoundary {
    void execute (AddAssetAndLiabilityInputData addAssetAndLiabilityInputData);
}
