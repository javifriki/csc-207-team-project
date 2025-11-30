package interface_adaptor.net_worth_table;

import entity.AssetAndLiability;
import use_case.net_worth_table.NetWorthTableOutputBoundary;
import use_case.net_worth_table.NetWorthTableOutputData;

import java.util.ArrayList;
import java.util.List;

public class NetWorthTablePresenter implements NetWorthTableOutputBoundary {

    private final NetWorthTableViewModel viewModel;

    public NetWorthTablePresenter(NetWorthTableViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(NetWorthTableOutputData netWorthTableOutputData) {
        List<NetWorthTableRow> assetRows = new ArrayList<>();
        for (AssetAndLiability asset: netWorthTableOutputData.getAllAssets()) {
            if (asset.getCurrentAmount() >= 0) {
                assetRows.add(new NetWorthTableRow(asset.getName(), asset.getCurrentAmount()));
            }
        }

        List<NetWorthTableRow> liabilityRows = new ArrayList<>();
        for (AssetAndLiability liability : netWorthTableOutputData.getAllLiabilities()) {
            if (liability.getCurrentAmount() >= 0) {
                liabilityRows.add(new NetWorthTableRow(liability.getName(), liability.getCurrentAmount()));
            }
        }

        NetWorthTableState newState = new NetWorthTableState();
        newState.setAssetRows(assetRows);
        newState.setLiabilityRows(liabilityRows);
        newState.setTotalAssets(netWorthTableOutputData.getTotalAssets());
        newState.setTotalLiabilities(netWorthTableOutputData.getTotalLiabilities());
        newState.setNetWorth(netWorthTableOutputData.getNetWorth());

        viewModel.setState(newState);
    }
}
