package use_case.add_asset_and_liability;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;
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
        final String name = addAssetAndLiabilityInputData.getName();
        final AssetAndLiability.Type type = addAssetAndLiabilityInputData.getType();
        final double initialAmount = addAssetAndLiabilityInputData.getInitialAmount();
        final String ID = addAssetAndLiabilityInputData.getID();
        final LocalDate dateCreated = addAssetAndLiabilityInputData.getDateCreated();
        final double interestRate =  addAssetAndLiabilityInputData.getInterestRate();
        final AssetAndLiability.RatePeriod ratePeriod = addAssetAndLiabilityInputData.getRatePeriod();

        AssetAndLiability assetAndLiability = new AssetAndLiability(name, type, ratePeriod, initialAmount,
                                                                    initialAmount, ID,
                                                                    dateCreated, interestRate);

        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);

        final AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData = new AddAssetAndLiabilityOutputData(
                type,
                initialAmount,
                interestRate,
                ratePeriod,
                assetAndLiability
        );

        addAssetAndLiabilityPresenter.prepareAssetAndLiabilitySuccessView(addAssetAndLiabilityOutputData);

    }
}