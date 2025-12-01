package use_case.add_asset_and_liability;

import entity.AssetAndLiability;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class AddAssetAndLiabilityInteractor implements AddAssetAndLiabilityInputBoundary {

    private final AssetAndLiabilityDataAccessInterface assetAndLiabilityDataAccessObject;
    private final AddAssetAndLiabilityOutputBoundary addAssetAndLiabilityPresenter;

    private HashMap<String, AssetAndLiability.Type> assetAndLiabilityTypeMap
            = new HashMap<>();
    private HashMap<String, AssetAndLiability.RatePeriod> assetAndLiabilityRatePeriodMap
            = new HashMap<>();

    public AddAssetAndLiabilityInteractor (AssetAndLiabilityDataAccessInterface assetAndLiabilityDataAccessObject,
                                           AddAssetAndLiabilityOutputBoundary addAssetAndLiabilityPresenter) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
        this.addAssetAndLiabilityPresenter = addAssetAndLiabilityPresenter;


        for (AssetAndLiability.Type type:  AssetAndLiability.Type.values()) {
            assetAndLiabilityTypeMap.put(type.toString(), type);
        }

        for (AssetAndLiability.RatePeriod ratePeriod:  AssetAndLiability.RatePeriod.values()) {
            assetAndLiabilityRatePeriodMap.put(ratePeriod.toString(), ratePeriod);
        }
    }

    @Override
    public String execute(AddAssetAndLiabilityInputData addAssetAndLiabilityInputData) {
        String errorMessage = "";

        final String name = addAssetAndLiabilityInputData.getName();
        final AssetAndLiability.Type type = assetAndLiabilityTypeMap.get(addAssetAndLiabilityInputData.getType());
        final double initialAmount = addAssetAndLiabilityInputData.getInitialAmount();
        final String ID = addAssetAndLiabilityInputData.getID();
        final LocalDate dateCreated = addAssetAndLiabilityInputData.getDateCreated();
        final double interestRate =  addAssetAndLiabilityInputData.getInterestRate();
        final AssetAndLiability.RatePeriod ratePeriod = assetAndLiabilityRatePeriodMap.get(addAssetAndLiabilityInputData.getRatePeriod());

        if (name.isEmpty()) {
            errorMessage += "No name provided";
        }

        Map<String, AssetAndLiability> assetAndLiabilityIDToAssetAndLiability = assetAndLiabilityDataAccessObject.getAssetAndLiabilityIDToAssetAndLiability();

        if (ID.isEmpty()) {
            errorMessage += "\nNo ID provided";
        } else if (assetAndLiabilityIDToAssetAndLiability.containsKey(ID)) {
            errorMessage += "\nID already in use, choose another ID.";
        }

        if (!errorMessage.isEmpty()) {
            addAssetAndLiabilityPresenter.prepareAssetAndLiabilityFailView(errorMessage);
            return errorMessage;
        }

        AssetAndLiability assetAndLiability = new AssetAndLiability(name, type, ratePeriod, initialAmount,
                                                                    initialAmount, ID,
                                                                    dateCreated, interestRate);

        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);

        final AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData = new AddAssetAndLiabilityOutputData(
                type,
                initialAmount,
                interestRate,
                ratePeriod,
                assetAndLiabilityDataAccessObject.getAllAssetAndLiabilities(),
                assetAndLiability
        );

        addAssetAndLiabilityPresenter.prepareAssetAndLiabilitySuccessView(addAssetAndLiabilityOutputData);

        return "";
    }
}