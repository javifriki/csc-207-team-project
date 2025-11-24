package use_case.asset_and_liability_apply_rate;

import entity.AssetAndLiability;

import java.time.LocalDate;
import java.util.List;

public class AssetAndLiabilityApplyRateOutputData {
    private String message;
    private List<AssetAndLiability> assetAndLiabilityList;

    public AssetAndLiabilityApplyRateOutputData(String message, List<AssetAndLiability> assetAndLiabilityList) {
        this.message = message;
        this.assetAndLiabilityList = assetAndLiabilityList;
    }

    public String getMessage() {
        return this.message;
    }

    public List<AssetAndLiability> getAssetAndLiabilityList() {
        return assetAndLiabilityList;
    }
}
