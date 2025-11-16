package interface_adaptor.net_worth_table;

import entity.AssetAndLiability;

import java.util.List;

public class NetWorthTableViewModel {

    private final Object[][] assetTableData;
    private final Object[][] liabilityTableData;
    private final double totalAssets;
    private final double totalLiabilities;
    private final double netWorth;

    public NetWorthTableViewModel(List<AssetAndLiability> assets,
                                  List<AssetAndLiability> liabilities,
                                  double totalAssets,
                                  double totalLiabilities,
                                  double netWorth) {

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

        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.netWorth = netWorth;
    }

    public Object[][] getAssetTableData() { return assetTableData; }

    public Object[][] getLiabilityTableData() { return liabilityTableData; }

    public double getTotalAssets() { return totalAssets; }

    public double getTotalLiabilities() { return totalLiabilities; }

    public double getNetWorth() { return netWorth; }
}
