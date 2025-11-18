package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

import java.util.List;

public interface AssetAndLiabilityDataAccessInterface {
    void saveAssetAndLiability(AssetAndLiability assetAndLiability);
    AssetAndLiability getAssetAndLiability(String assetAndLiabilityID);
    List<AssetAndLiability> getAllAssetAndLiabilities();
}