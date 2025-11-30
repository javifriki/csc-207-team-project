package net_worth_table;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;
import org.junit.jupiter.api.Test;
import use_case.net_worth_table.NetWorthTableInputData;
import use_case.net_worth_table.NetWorthTableInteractor;
import use_case.net_worth_table.NetWorthTableOutputBoundary;
import use_case.net_worth_table.NetWorthTableOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NetWorthTableInteractorTest {

    //mock presenter that only retrieves output data for comparison
    static class MockPresenter implements NetWorthTableOutputBoundary {
        NetWorthTableOutputData mockOutputData;

        @Override
        public void present(NetWorthTableOutputData mockOutputData) {
            this.mockOutputData = mockOutputData;
        }
    }

    //helper method to create interactor that doesn't rely on actual DAO
    private NetWorthTableInteractor createInteractor(final List<AssetAndLiability> assetsAndLiabilities, MockPresenter presenter) {
        return new NetWorthTableInteractor(
                new AssetAndLiabilityDataAccessObject("filename") {
                    @Override
                    public List<AssetAndLiability> getAllAssetAndLiabilities() {
                        return assetsAndLiabilities;
                    }
                },
                presenter
        );
    }

    @Test
    public void testSeparateAssetAndLiability() {
        AssetAndLiability a1 = TestAssetAndLiabilityFactory.asset("stock", 2000, "AAA111", AssetAndLiability.RatePeriod.MONTHLY);
        AssetAndLiability a2 = TestAssetAndLiabilityFactory.asset("property", 100000.69, "ABC123", AssetAndLiability.RatePeriod.MONTHLY);
        AssetAndLiability a3 = TestAssetAndLiabilityFactory.asset("business", 350, "DEF456", AssetAndLiability.RatePeriod.MONTHLY);

        AssetAndLiability l1 = TestAssetAndLiabilityFactory.liability("house", 100, "ASF123", AssetAndLiability.RatePeriod.MONTHLY);
        AssetAndLiability l2 = TestAssetAndLiabilityFactory.liability("debt", 4234.69, "WER234", AssetAndLiability.RatePeriod.MONTHLY);
        AssetAndLiability l3 = TestAssetAndLiabilityFactory.liability("tuition", 1235125, "GER221", AssetAndLiability.RatePeriod.MONTHLY);
        AssetAndLiability l4 = TestAssetAndLiabilityFactory.liability("big_house", 7658, "GET123", AssetAndLiability.RatePeriod.MONTHLY);

        MockPresenter presenter = new MockPresenter();
        NetWorthTableInteractor interactor = createInteractor(List.of(a1, l1, a2, l2, l3, a3, l4), presenter);

        interactor.execute(new NetWorthTableInputData());

        NetWorthTableOutputData output = presenter.mockOutputData;
        assertNotNull(output);
        assertEquals(3, output.getAllAssets().size());
        assertEquals(4, output.getAllLiabilities().size());
        assertEquals(102350.69, output.getTotalAssets());
        assertEquals(1247117.69, output.getTotalLiabilities());
    }

    @Test
    public void testEmptyAssetAndLiability() {
        MockPresenter presenter = new MockPresenter();
        NetWorthTableInteractor interactor = createInteractor(new ArrayList<>(), presenter);

        interactor.execute(new NetWorthTableInputData());

        NetWorthTableOutputData output = presenter.mockOutputData;
        assertNotNull(output);
        assertTrue(output.getAllAssets().isEmpty());
        assertTrue(output.getAllLiabilities().isEmpty());
        assertEquals(0, output.getTotalAssets());
        assertEquals(0, output.getTotalLiabilities());
    }
}
