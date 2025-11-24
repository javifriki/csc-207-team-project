package interface_adaptor.add_asset_and_liability;

import entity.AssetAndLiability;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInputBoundary;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInputData;

import java.time.LocalDate;
import java.util.HashMap;

public class AddAssetAndLiabilityController {
    private AddAssetAndLiabilityInputBoundary addAssetAndLiabilityInteractor;
    private HashMap<String, AssetAndLiability.Type> assetAndLiabilityTypeMap
            = new HashMap<>();
    private HashMap<String, AssetAndLiability.RatePeriod> assetAndLiabilityRatePeriodMap
            = new HashMap<>();

    public AddAssetAndLiabilityController(AddAssetAndLiabilityInputBoundary addAssetAndLiabilityInteractor) {
        this.addAssetAndLiabilityInteractor =  addAssetAndLiabilityInteractor;

        for (AssetAndLiability.Type type:  AssetAndLiability.Type.values()) {
            assetAndLiabilityTypeMap.put(type.toString(), type);
        }

        for (AssetAndLiability.RatePeriod ratePeriod:  AssetAndLiability.RatePeriod.values()) {
            assetAndLiabilityRatePeriodMap.put(ratePeriod.toString(), ratePeriod);
        }
    }

    public void execute(String name, String type, String ID, double initialAmount,
                        String date, String ratePeriod, double interestRate) {
        AssetAndLiability.Type assetAndLiabilityType = assetAndLiabilityTypeMap.get(type.toUpperCase());
        AssetAndLiability.RatePeriod assetAndLiabilityRatePeriod =
                assetAndLiabilityRatePeriodMap.get(ratePeriod.toUpperCase());

        LocalDate dateCreated = LocalDate.parse(date);

        final AddAssetAndLiabilityInputData addAssetAndLiabilityInputData =
                new AddAssetAndLiabilityInputData(
                        name, assetAndLiabilityType, ID,
                        dateCreated, initialAmount, interestRate,
                        assetAndLiabilityRatePeriod
                );

        addAssetAndLiabilityInteractor.execute(addAssetAndLiabilityInputData);
    }

}
