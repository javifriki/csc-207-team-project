package interface_adaptor.add_asset_and_liability;

import entity.AssetAndLiability;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateState;

import java.util.ArrayList;
import java.util.List;

public class AddAssetAndLiabilityState {
    private List<AssetAndLiability> allAssetAndLiabilityList = new ArrayList<>();
    private String popupMessage = "";

    public List<AssetAndLiability> getAllAssetAndLiabilityList() {
        return this.allAssetAndLiabilityList;
    }

    public String getPopupMessage() {
        return this.popupMessage;
    }

    public void addAssetAndLiabilityToList(AssetAndLiability assetAndLiability) {
        this.allAssetAndLiabilityList.add(assetAndLiability);
    }

    public void setAllAssetAndLiabilityList(List<AssetAndLiability> allAssetAndLiabilityList) {
        this.allAssetAndLiabilityList = allAssetAndLiabilityList;
    }

    public void setPopupMessage(String message) {
        this.popupMessage = message;
    }
}
