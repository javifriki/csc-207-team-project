package use_case.capital;

import entity.AssetAndLiability;

import java.util.List;

public interface AssetAndLiabilityDataAccessInterface {
    void saveAssetAndLiability(AssetAndLiability assetAndLiability);
    AssetAndLiability getAssetAndLiability(String assetAndLiabilityID);
    List<AssetAndLiability> getAllAssetAndLiabilities();
}
