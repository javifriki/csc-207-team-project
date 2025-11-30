package interface_adaptor.add_asset_and_liability;

import entity.AssetAndLiability;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInputBoundary;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInputData;

import java.time.LocalDate;
import java.util.HashMap;

public class AddAssetAndLiabilityController {
    private AddAssetAndLiabilityInputBoundary addAssetAndLiabilityInteractor;

    public AddAssetAndLiabilityController(AddAssetAndLiabilityInputBoundary addAssetAndLiabilityInteractor) {
        this.addAssetAndLiabilityInteractor =  addAssetAndLiabilityInteractor;
    }

    public void execute(String name, String type, String ID, double initialAmount,
                        LocalDate date, String ratePeriod, double interestRate) {
//        AssetAndLiability.Type assetAndLiabilityType = assetAndLiabilityTypeMap.get(type.toUpperCase());
//        AssetAndLiability.RatePeriod assetAndLiabilityRatePeriod =
//                assetAndLiabilityRatePeriodMap.get(ratePeriod.toUpperCase());

        final AddAssetAndLiabilityInputData addAssetAndLiabilityInputData =
                new AddAssetAndLiabilityInputData(
                        name, type, ID,
                        date, initialAmount, interestRate,
                        ratePeriod
                );

        addAssetAndLiabilityInteractor.execute(addAssetAndLiabilityInputData);
    }

}
