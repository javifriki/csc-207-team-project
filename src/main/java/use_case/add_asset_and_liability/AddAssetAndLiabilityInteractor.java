package use_case.add_asset_and_liability;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;
import entity.AssetAndLiabilityBuilder;
import interface_adaptor.add_asset_and_liability.AddAssetAndLiabilityPresenter;

import java.time.LocalDate;

public class AddAssetAndLiabilityInteractor implements AddAssetAndLiabilityInputBoundary {

    private final AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject;
    private final AddAssetAndLiabilityPresenter addAssetAndLiabilityPresenter;

    public AddAssetAndLiabilityInteractor (AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject,
                                           AddAssetAndLiabilityPresenter addAssetAndLiabilityPresenter) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
        this.addAssetAndLiabilityPresenter = addAssetAndLiabilityPresenter;
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

        AssetAndLiability assetAndLiability = new AssetAndLiabilityBuilder()
                                                    .addName(assetAndLiabilityName)
                                                    .addID(assetAndLiabilityID)
                                                    .addAmount(assetAndLiabilityAmount)
                                                    .addType(assetAndLiabilityType)
                                                    .addRatePeriod(assetAndLiabilityRatePeriod)
                                                    .addDateCreated(dateCreated)
                                                    .addDateUpdated(dateCreated).build();



        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);

        final AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData = new AddAssetAndLiabilityOutputData(
                assetAndLiabilityType,
                assetAndLiabilityAmount,
                interestRate,
                assetAndLiabilityRatePeriod,
                assetAndLiability
        );

        addAssetAndLiabilityPresenter.prepareAssetAndLiabilitySuccessView(addAssetAndLiabilityOutputData);

    }
}