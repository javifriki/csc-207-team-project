package add_asset_and_liability;

import data_access.InMemoryAssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;
import org.junit.jupiter.api.Test;
import use_case.add_asset_and_liability.AssetAndLiabilityDataAccessInterface;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInputData;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInteractor;
import use_case.add_asset_and_liability.AddAssetAndLiabilityOutputBoundary;
import use_case.add_asset_and_liability.AddAssetAndLiabilityOutputData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddAssetAndLiabilityInteractorTest {
    @Test
    void assetAndLiabilitySuccessfulTest() {
        // Asset/Liability Input Data
        AddAssetAndLiabilityInputData addAssetAndLiabilityInputData = new AddAssetAndLiabilityInputData(
                "House 1",
                AssetAndLiability.Type.ASSET,
                "A1000",
                LocalDate.of(2020, 10, 10),
                1105000.0,
                0.01,
                AssetAndLiability.RatePeriod.MONTHLY
        );

        AssetAndLiabilityDataAccessInterface assetAndLiabilityDataAccessObject = new InMemoryAssetAndLiabilityDataAccessObject();

        // Add the Asset/Liability to temporary DAO so the transaction can be added
        AssetAndLiability assetAndLiability = new AssetAndLiability("House 1",
                AssetAndLiability.Type.ASSET,
                AssetAndLiability.RatePeriod.MONTHLY,
                1105000.0,
                1105000.0,
                "A1000",
                LocalDate.of(2020, 10, 10),
                0.01
                );

        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability);

        // Mock Presenter
        AddAssetAndLiabilityOutputBoundary addAssetAndLiabilityPresenter = new AddAssetAndLiabilityOutputBoundary() {
            @Override
            public void prepareAssetAndLiabilitySuccessView(AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData) {
                // Test the output data is correct
                assertEquals("ASSET", addAssetAndLiabilityOutputData.getType().toString());
                assertEquals(1105000.0, addAssetAndLiabilityOutputData.getInitialAmount());
                assertEquals(0.01, addAssetAndLiabilityOutputData.getInterestRate());
                assertEquals("MONTHLY", addAssetAndLiabilityOutputData.getRatePeriod().toString());

                assertEquals(1105000.0, addAssetAndLiabilityOutputData.getAddedAssetAndLiability().getCurrentAmount());
                assertEquals("A1000", addAssetAndLiabilityOutputData.getAddedAssetAndLiability().getID());
                assertEquals("House 1", addAssetAndLiabilityOutputData.getAddedAssetAndLiability().getName());
                assertEquals("House 1", addAssetAndLiabilityOutputData.getAddedAssetAndLiability().getName());
                assertEquals("2020-10-10", addAssetAndLiabilityOutputData.getAddedAssetAndLiability().getDateCreated().toString());
            }

            @Override
            public void prepareAssetAndLiabilityFailView(String errorMessage) {
                fail("Unexpected fail");
            }
        };

        AddAssetAndLiabilityInteractor addAssetAndLiabilityInteractor =
                new AddAssetAndLiabilityInteractor(assetAndLiabilityDataAccessObject, addAssetAndLiabilityPresenter);
        addAssetAndLiabilityInteractor.execute(addAssetAndLiabilityInputData);

        // Check the DAO has stored the asset/liability within it
        assertEquals("A1000", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getID());
        assertEquals("ASSET", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getType().toString());
        assertEquals("MONTHLY", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getRatePeriod().toString());
        assertEquals(1105000.0, assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getInitialAmount());

        // Once we implement the asset/liability into the user class,
        // need to check if the DAO stored the asset/liability under the user

    }
}