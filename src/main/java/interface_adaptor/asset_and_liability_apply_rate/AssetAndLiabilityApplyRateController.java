package interface_adaptor.asset_and_liability_apply_rate;

import entity.AssetAndLiability;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateInputBoundary;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateInputData;

import java.time.LocalDate;
import java.util.HashMap;

public class AssetAndLiabilityApplyRateController {
    private AssetAndLiabilityApplyRateInputBoundary assetAndLiabilityApplyRateInteractor;

    public AssetAndLiabilityApplyRateController(AssetAndLiabilityApplyRateInputBoundary assetAndLiabilityApplyRateInteractor) {
        this.assetAndLiabilityApplyRateInteractor =  assetAndLiabilityApplyRateInteractor;
    }

    public void execute(String[] IDs) {
        final AssetAndLiabilityApplyRateInputData assetAndLiabilityApplyRateInputData =
                new AssetAndLiabilityApplyRateInputData(IDs);

        assetAndLiabilityApplyRateInteractor.execute(assetAndLiabilityApplyRateInputData);
    }

}
