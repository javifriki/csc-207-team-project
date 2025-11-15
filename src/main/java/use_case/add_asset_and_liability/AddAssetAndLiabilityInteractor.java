package use_case.add_asset_and_liability;

import data_access.AccountDataAccessObject;
import entity.Account;
import entity.AssetAndLiability;

import java.time.LocalDate;

public class AddAssetAndLiabilityInteractor implements AddAssetAndLiabilityInputBoundary {

    private final AccountDataAccessObject accountDataAccessObject;
    // presenter

    public AddAssetAndLiabilityInteractor (AccountDataAccessObject accountDataAccessObject) {
        this.accountDataAccessObject = accountDataAccessObject;
    }

    @Override
    public void execute(AddAssetAndLiabilityInputData addAssetAndLiabilityInputData) {
        final String assetAndLiabilityName = addAssetAndLiabilityInputData.getName();
        final AssetAndLiability.Type assetAndLiabilityType = addAssetAndLiabilityInputData.getType();
        final double assetAndLiabilityAmount = addAssetAndLiabilityInputData.getAmount();
        final String assetAndLiabilityID = addAssetAndLiabilityInputData.getID();
        final LocalDate dateCreated = addAssetAndLiabilityInputData.getDateCreated();
        final double interestRate =  addAssetAndLiabilityInputData.getInterestRate();

//        if (account == null) {
//            // account does not exist
//        } else {
            AssetAndLiability assetAndLiability = new AssetAndLiability( // Ben: should implement Builder design pattern in the future
                    assetAndLiabilityName,
                    assetAndLiabilityType,
                    assetAndLiabilityAmount,
                    assetAndLiabilityID,
                    dateCreated,
                    interestRate
            );

            final AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData = new AddAssetAndLiabilityOutputData(
                    "successful",
                    assetAndLiabilityType,
                    assetAndLiabilityAmount,
                    interestRate
            );

            // presenter call
//        }

    }
}