package interface_adaptor.net_worth_table;

import entity.AssetAndLiability;
import use_case.net_worth_table.NetWorthTableOutputBoundary;
import use_case.net_worth_table.NetWorthTableOutputData;

import java.util.ArrayList;
import java.util.List;

public class NetWorthTablePresenter implements NetWorthTableOutputBoundary {

    private  NetWorthTableViewModel viewModel;

    public NetWorthTablePresenter(NetWorthTableViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(NetWorthTableOutputData netWorthTableOutputData) {
        List<NetWorthTableRow> assetRows = new ArrayList<>();
        for (AssetAndLiability a : netWorthTableOutputData.getAllAssets()) {
            assetRows.add(new NetWorthTableRow(a.getName(), a.getAmount()));
        }

        List<NetWorthTableRow> liabilityRows = new ArrayList<>();
        for (AssetAndLiability l : netWorthTableOutputData.getAllLiabilities()) {
            liabilityRows.add(new NetWorthTableRow(l.getName(), l.getAmount()));
        }

        NetWorthTableState newState = new NetWorthTableState();
        newState.setAssetRows(assetRows);
        newState.setLiabilityRows(liabilityRows);
        newState.setTotalAssets(netWorthTableOutputData.getTotalAssets());
        newState.setTotalLiabilities(netWorthTableOutputData.getTotalLiabilities());
        newState.setNetWorth(netWorthTableOutputData.getNetWorth());

        viewModel.setState(newState);
    }

    public NetWorthTableViewModel getViewModel() {
        return viewModel;
    }
}
