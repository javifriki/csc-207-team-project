package use_case.asset_and_liability_apply_rate;

import entity.AssetAndLiability;

import java.time.LocalDate;

public class AssetAndLiabilityApplyRateInputData {
    private final String[] IDs;

    public AssetAndLiabilityApplyRateInputData(String[] IDs) {
        this.IDs = IDs;
    }

    // Getters
    public String[] getIDs() { return IDs; }
}
