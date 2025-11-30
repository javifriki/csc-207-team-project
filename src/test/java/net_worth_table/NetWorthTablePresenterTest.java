package net_worth_table;

import entity.AssetAndLiability;
import interface_adaptor.net_worth_table.NetWorthTablePresenter;
import interface_adaptor.net_worth_table.NetWorthTableViewModel;
import org.junit.jupiter.api.Test;
import use_case.net_worth_table.NetWorthTableOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NetWorthTablePresenterTest {

    @Test
    public void testPresenter() {
        AssetAndLiability a1 = TestAssetAndLiabilityFactory.asset("stock", 2000, "AAA111");
        AssetAndLiability a2 = TestAssetAndLiabilityFactory.asset("property", 100000.69, "ABC123");
        AssetAndLiability a3 = TestAssetAndLiabilityFactory.asset("business", 350, "DEF456");

        AssetAndLiability l1 = TestAssetAndLiabilityFactory.liability("house", 100, "ASF123");
        AssetAndLiability l2 = TestAssetAndLiabilityFactory.liability("debt", 4234.69, "WER234");

        List<AssetAndLiability> assets = List.of(a1, a2, a3);
        List<AssetAndLiability> liabilities = List.of(l1, l2);

        NetWorthTableOutputData outputData = new NetWorthTableOutputData(assets, liabilities, 102350.69, 4334.69);
        NetWorthTableViewModel viewModel = new NetWorthTableViewModel();
        NetWorthTablePresenter presenter = new NetWorthTablePresenter(viewModel);

        presenter.present(outputData);

        //assets
        assertEquals(3, viewModel.getState().getAssetRows().size());
        assertEquals("stock", viewModel.getState().getAssetRows().get(0).getName());
        assertEquals("$2000.00", viewModel.getState().getAssetRows().get(0).getAmount());
        assertEquals("property", viewModel.getState().getAssetRows().get(1).getName());
        assertEquals("$100000.69", viewModel.getState().getAssetRows().get(1).getAmount());
        assertEquals("business", viewModel.getState().getAssetRows().get(2).getName());
        assertEquals("$350.00", viewModel.getState().getAssetRows().get(2).getAmount());

        //liabilities
        assertEquals(2, viewModel.getState().getLiabilityRows().size());
        assertEquals("house", viewModel.getState().getLiabilityRows().get(0).getName());
        assertEquals("$100.00", viewModel.getState().getLiabilityRows().get(0).getAmount());
        assertEquals("debt", viewModel.getState().getLiabilityRows().get(1).getName());
        assertEquals("$4234.69", viewModel.getState().getLiabilityRows().get(1).getAmount());

        //totals
        assertEquals("$102350.69", viewModel.getState().getTotalAssets());
        assertEquals("$4334.69", viewModel.getState().getTotalLiabilities());
        assertEquals("$98016.00", viewModel.getState().getNetWorth());
    }

    @Test
    void testEmptyAssetAndLiability() {
        List<AssetAndLiability> assets = new ArrayList<>();
        List<AssetAndLiability>  liabilities = new ArrayList<>();

        NetWorthTableOutputData outputData = new NetWorthTableOutputData(assets, liabilities, 0, 0);
        NetWorthTableViewModel viewModel = new NetWorthTableViewModel();
        NetWorthTablePresenter presenter = new NetWorthTablePresenter(viewModel);

        presenter.present(outputData);

        assertTrue(viewModel.getState().getAssetRows().isEmpty());
        assertTrue(viewModel.getState().getLiabilityRows().isEmpty());
        assertEquals(0, viewModel.getState().getTotalAssets());
        assertEquals(0, viewModel.getState().getTotalLiabilities());
        assertEquals(0, viewModel.getState().getNetWorth());
    }

}
