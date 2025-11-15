
package data_access;

import entity.AssetAndLiability;
import use_case.add_asset_and_liability.AssetAndLiabilityDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetAndLiabilityDataAccessObject implements AssetAndLiabilityDataAccessInterface {
    // Temporary Storage DAO, can incorporate persistent storage later by storing ALL ACCOUNT class fields in JSON
    private Map<String, AssetAndLiability> assetAndLiabilityIDToAssetAndLiability = new HashMap<>();

    @Override
    public void saveAssetAndLiability(AssetAndLiability assetAndLiability) {
        this.assetAndLiabilityIDToAssetAndLiability.put(assetAndLiability.getID(), assetAndLiability);
    }

    @Override
    public AssetAndLiability getAssetAndLiability(String assetAndLiabilityID) {
        return this.assetAndLiabilityIDToAssetAndLiability.get(assetAndLiabilityID);
    }

    @Override
    public List<AssetAndLiability> getAllAssetAndLiabilities() {
        return new ArrayList<>(assetAndLiabilityIDToAssetAndLiability.values());
    }

}
