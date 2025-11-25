package interface_adaptor.net_worth_table;

import entity.AssetAndLiability;

import java.util.ArrayList;
import java.util.List;

public class NetWorthTableViewModel {

    private final List<NetWorthTableRow> assetRows;
    private final List<NetWorthTableRow> liabilityRows;
    private final double totalAssets;
    private final double totalLiabilities;
    private final double netWorth;

    public NetWorthTableViewModel(List<AssetAndLiability> assets,
                                  List<AssetAndLiability> liabilities,
                                  double totalAssets,
                                  double totalLiabilities,
                                  double netWorth) {

        this.assetRows = new ArrayList<>();
        this.liabilityRows = new  ArrayList<>();

        for (AssetAndLiability asset : assets) {
            NetWorthTableRow row = new NetWorthTableRow(asset.getName(), asset.getCurrentAmount());
            this.assetRows.add(row);
        }

        for (AssetAndLiability liability : liabilities) {
            NetWorthTableRow row = new NetWorthTableRow(liability.getName(), liability.getCurrentAmount());
            this.liabilityRows.add(row);
        }
/*
        this.assetTableData = new Object[assets.size()][2];
        for (int i = 0; i < assets.size(); i++) {
            AssetAndLiability a = assets.get(i);
            assetTableData[i][0] = a.getName();
            assetTableData[i][1] = a.getAmount();
        }

        this.liabilityTableData = new Object[liabilities.size()][2];
        for (int i = 0; i < liabilities.size(); i++) {
            AssetAndLiability l = liabilities.get(i);
            liabilityTableData[i][0] = l.getName();
            liabilityTableData[i][1] = l.getAmount();
        }
*/
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.netWorth = netWorth;
    }

    public List<NetWorthTableRow> getAssetRows() { return assetRows; }

    public List<NetWorthTableRow> getLiabilityRows() { return liabilityRows; }

    public double getTotalAssets() { return totalAssets; }

    public double getTotalLiabilities() { return totalLiabilities; }

    public double getNetWorth() { return netWorth; }
}
