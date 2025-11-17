package use_case.add_asset_and_liability;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;

import java.time.LocalDate;

public class AddAssetAndLiabilityInteractor implements AddAssetAndLiabilityInputBoundary {

    private final AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject;
    // presenter

    public AddAssetAndLiabilityInteractor (AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
    }

    @Override
    public void execute(AddAssetAndLiabilityInputData addAssetAndLiabilityInputData) {
        final String assetAndLiabilityName = addAssetAndLiabilityInputData.getName();
        final AssetAndLiability.Type assetAndLiabilityType = addAssetAndLiabilityInputData.getType();
        final double assetAndLiabilityAmount = addAssetAndLiabilityInputData.getAmount();
        final String assetAndLiabilityID = addAssetAndLiabilityInputData.getID();
        final LocalDate dateCreated = addAssetAndLiabilityInputData.getDateCreated();
        final double interestRate =  addAssetAndLiabilityInputData.getInterestRate();
        final AssetAndLiability.RatePeriod assetAndLiabilityRatePeriod = addAssetAndLiabilityInputData.getRatePeriod();

        AssetAndLiability assetAndLiability = new AssetAndLiability( // Ben: should implement Builder design pattern in the future
                assetAndLiabilityName,
                assetAndLiabilityType,
                assetAndLiabilityRatePeriod,
                assetAndLiabilityAmount,
                assetAndLiabilityID,
                dateCreated,
                interestRate
        );

        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);

        final AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData = new AddAssetAndLiabilityOutputData(
                "successful",
                assetAndLiabilityType,
                assetAndLiabilityAmount,
                interestRate,
                assetAndLiabilityRatePeriod
        );

        // presenter call

    }
}