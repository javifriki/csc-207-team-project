package use_case.asset_and_liability_apply_rate;

import entity.AssetAndLiability;

import java.time.LocalDate;

public class AssetAndLiabilityApplyRateInputData {
    private final String ID;
    private final LocalDate endDate;

    public AssetAndLiabilityApplyRateInputData(String ID,
                                         LocalDate endDate) {
        this.ID = ID;
        this.endDate = endDate;
    }

    // Getters
    public String getID() { return ID; }
    public LocalDate getEndDate() { return endDate; }
}
