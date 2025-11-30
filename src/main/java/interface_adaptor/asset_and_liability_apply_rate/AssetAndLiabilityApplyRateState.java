package interface_adaptor.asset_and_liability_apply_rate;

import entity.AssetAndLiability;

import java.util.ArrayList;
import java.util.List;

public class AssetAndLiabilityApplyRateState {
    private List<AssetAndLiability> allAssetAndLiabilityList = new ArrayList<>();
    private String popupMessage = "";

    public List<AssetAndLiability> getAllAssetAndLiabilityList() {
        return this.allAssetAndLiabilityList;
    }

    public void setAllAssetAndLiabilityList(List<AssetAndLiability> allAssetAndLiabilityList) {
        this.allAssetAndLiabilityList = allAssetAndLiabilityList;
    }

    public void setPopupMessage(String message) {
        this.popupMessage = message;
    }

    public String getPopupMessage() {
        return popupMessage;
    }
}
