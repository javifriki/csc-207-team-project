package asset_and_liability_apply_rate;

import data_access.InMemoryAssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;
import org.junit.jupiter.api.Test;
import use_case.add_asset_and_liability.AssetAndLiabilityDataAccessInterface;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateInputData;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateInteractor;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateOutputBoundary;
import use_case.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateOutputData;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInputData;
import use_case.add_asset_and_liability.AddAssetAndLiabilityInteractor;
import use_case.add_asset_and_liability.AddAssetAndLiabilityOutputBoundary;
import use_case.add_asset_and_liability.AddAssetAndLiabilityOutputData;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AssetAndLiabilityApplyRateInteractorTest {
    @Test
    void assetAndLiabilitySuccessfulTest() {
        // We first add new assets and liabilities into the DAO
        AddAssetAndLiabilityInputData addAssetAndLiabilityInputData1 = new AddAssetAndLiabilityInputData(
                "House 1",
                AssetAndLiability.Type.ASSET.toString(),
                "A1000",
                LocalDate.of(2025, 06, 01),
                1105000.0,
                0.01,
                AssetAndLiability.RatePeriod.MONTHLY.toString()
        );

        AddAssetAndLiabilityInputData addAssetAndLiabilityInputData2 = new AddAssetAndLiabilityInputData(
                "Student Loan",
                AssetAndLiability.Type.LIABILITY.toString(),
                "L1000",
                LocalDate.of(2022, 01, 01),
                200000.0,
                0.04,
                AssetAndLiability.RatePeriod.QUARTERLY.toString()
        );

        AddAssetAndLiabilityInputData addAssetAndLiabilityInputData3 = new AddAssetAndLiabilityInputData(
                "Dividends",
                AssetAndLiability.Type.ASSET.toString(),
                "A1001",
                LocalDate.of(2020, 01, 01),
                30000.0,
                0.08,
                AssetAndLiability.RatePeriod.ANNUALLY.toString()
        );

        // Then, we save the assets/liabilities into the temporary DAO
        AssetAndLiabilityDataAccessInterface assetAndLiabilityDataAccessObject = new InMemoryAssetAndLiabilityDataAccessObject();

        AssetAndLiability assetAndLiability1 = new AssetAndLiability("House 1",
                AssetAndLiability.Type.ASSET,
                AssetAndLiability.RatePeriod.MONTHLY,
                1105000.0,
                1105000.0,
                "A1000",
                LocalDate.of(2025, 06, 01),
                0.01
        );

        AssetAndLiability assetAndLiability2 = new AssetAndLiability("Student Loan",
                AssetAndLiability.Type.LIABILITY,
                AssetAndLiability.RatePeriod.QUARTERLY,
                200000.0,
                200000.0,
                "L1000",
                LocalDate.of(2022, 01, 01),
                0.04
        );

        AssetAndLiability assetAndLiability3 = new AssetAndLiability("Dividends",
                AssetAndLiability.Type.ASSET,
                AssetAndLiability.RatePeriod.ANNUALLY,
                30000.0,
                30000.0,
                "A1001",
                LocalDate.of(2020, 01, 01),
                0.08
        );

        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability1);
        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability2);
        assetAndLiabilityDataAccessObject.saveAssetAndLiability(assetAndLiability3);

        // Mock Presenter for Add Use Case - no need to check output in this case
        AddAssetAndLiabilityOutputBoundary addAssetAndLiabilityPresenter = new AddAssetAndLiabilityOutputBoundary() {
            @Override
            public void prepareAssetAndLiabilitySuccessView(AddAssetAndLiabilityOutputData addAssetAndLiabilityOutputData) {}

            @Override
            public void prepareAssetAndLiabilityFailView(String errorMessage) {}
        };

        AddAssetAndLiabilityInteractor addAssetAndLiabilityInteractor =
                new AddAssetAndLiabilityInteractor(assetAndLiabilityDataAccessObject, addAssetAndLiabilityPresenter);
        addAssetAndLiabilityInteractor.execute(addAssetAndLiabilityInputData1);
        addAssetAndLiabilityInteractor.execute(addAssetAndLiabilityInputData2);
        addAssetAndLiabilityInteractor.execute(addAssetAndLiabilityInputData3);

        // Check the DAO has stored the asset/liability within it
        assertEquals("A1000", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getID());
        assertEquals("ASSET", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getType().toString());
        assertEquals("MONTHLY", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getRatePeriod().toString());
        assertEquals(1105000.0, assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getInitialAmount());

        assertEquals("L1000", assetAndLiabilityDataAccessObject.getAssetAndLiability("L1000").getID());
        assertEquals("LIABILITY", assetAndLiabilityDataAccessObject.getAssetAndLiability("L1000").getType().toString());
        assertEquals("QUARTERLY", assetAndLiabilityDataAccessObject.getAssetAndLiability("L1000").getRatePeriod().toString());
        assertEquals(200000.0, assetAndLiabilityDataAccessObject.getAssetAndLiability("L1000").getInitialAmount());

        assertEquals("A1001", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1001").getID());
        assertEquals("ASSET", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1001").getType().toString());
        assertEquals("ANNUALLY", assetAndLiabilityDataAccessObject.getAssetAndLiability("A1001").getRatePeriod().toString());
        assertEquals(30000.0, assetAndLiabilityDataAccessObject.getAssetAndLiability("A1001").getInitialAmount());

        // Now, we check the apply rate use case
        String[] IDs = new String[]{"A1000", "A1001", "L1000"};

        AssetAndLiabilityApplyRateInputData assetAndLiabilityApplyRateInputData = new AssetAndLiabilityApplyRateInputData(IDs);

        // Mock Presenter for Apply Rate Use Case - this time we check output
        AssetAndLiabilityApplyRateOutputBoundary assetAndLiabilityApplyRatePresenter = new AssetAndLiabilityApplyRateOutputBoundary() {
            @Override
            // Check if we have all three assets and liabilities applied successfully
            public void prepareAssetAndLiabilitySuccessView(AssetAndLiabilityApplyRateOutputData assetAndLiabilityApplyRateOutputData) {
                List<AssetAndLiability> assetAndLiabilityList = assetAndLiabilityApplyRateOutputData.getAssetAndLiabilityList();
                for (int i = 0; i < assetAndLiabilityList.size(); i++) {
                    assertEquals(IDs[i], assetAndLiabilityList.get(i).getID());
                }
            }
        };

        AssetAndLiabilityApplyRateInteractor assetAndLiabilityApplyRateInteractor = new AssetAndLiabilityApplyRateInteractor(
                assetAndLiabilityDataAccessObject, assetAndLiabilityApplyRatePresenter
        );
        assetAndLiabilityApplyRateInteractor.execute(assetAndLiabilityApplyRateInputData);

        // Check if rate apply works
        assertEquals(
                1105000.0 * Math.pow(1.01, Math.abs(Period.between(LocalDate.now(), assetAndLiability1.getDateCreated()).getMonths())),
                assetAndLiabilityDataAccessObject.getAssetAndLiability("A1000").getCurrentAmount()
        );
        assertEquals(
                200000.0 * Math.pow(1.04, Math.abs((Period.between(LocalDate.now(), assetAndLiability2.getDateCreated()).getMonths() / 3))),
                assetAndLiabilityDataAccessObject.getAssetAndLiability("L1000").getCurrentAmount()
        );
        assertEquals(
                30000.0 * Math.pow(1.08, Math.abs(Period.between(LocalDate.now(), assetAndLiability3.getDateCreated()).getYears())),
                assetAndLiabilityDataAccessObject.getAssetAndLiability("A1001").getCurrentAmount()
        );

    }
}