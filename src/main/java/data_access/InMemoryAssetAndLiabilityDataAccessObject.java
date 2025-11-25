package data_access;

import entity.Account;
import entity.AssetAndLiability;
import use_case.add_asset_and_liability.AssetAndLiabilityDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// FOR UNIT TESTING PURPOSES ONLY
public class InMemoryAssetAndLiabilityDataAccessObject implements AssetAndLiabilityDataAccessInterface {

    private Map<String, AssetAndLiability> assetAndLiabilityIDToAssetAndLiability = new HashMap<>();

    @Override
    public void saveAssetAndLiability(AssetAndLiability assetAndLiability) {
        this.assetAndLiabilityIDToAssetAndLiability.put(assetAndLiability.getID(), assetAndLiability);
    }

    @Override
    public AssetAndLiability getAssetAndLiability(String ID) {
        return this.assetAndLiabilityIDToAssetAndLiability.get(ID);
    }

    @Override
    public List<AssetAndLiability> getAllAssetAndLiabilities() {
        return new ArrayList<>(assetAndLiabilityIDToAssetAndLiability.values());
    }
}