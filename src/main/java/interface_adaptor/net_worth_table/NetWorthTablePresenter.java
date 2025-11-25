package interface_adaptor.net_worth_table;

import entity.AssetAndLiability;
import use_case.net_worth_table.NetWorthTableOutputBoundary;
import use_case.net_worth_table.NetWorthTableOutputData;

import java.util.List;

public class NetWorthTablePresenter implements NetWorthTableOutputBoundary {

    private  NetWorthTableViewModel viewModel;

    public NetWorthTablePresenter(NetWorthTableViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(NetWorthTableOutputData NetWorthTableOutputData) {
        List<AssetAndLiability> assets = NetWorthTableOutputData.getAllAssets();
        List<AssetAndLiability> liabilities = NetWorthTableOutputData.getAllLiabilities();

        viewModel = new NetWorthTableViewModel(
                assets,
                liabilities,
                NetWorthTableOutputData.getTotalAssets(),
                NetWorthTableOutputData.getTotalLiabilities(),
                NetWorthTableOutputData.getNetWorth()
        );
    }

    public NetWorthTableViewModel getViewModel() {
        return viewModel;
    }
}
