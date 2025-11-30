package interface_adaptor.net_worth_table;

import java.util.ArrayList;
import java.util.List;

public class NetWorthTableState {

    private List<NetWorthTableRow> assetRows = new ArrayList<>();
    private List<NetWorthTableRow> liabilityRows = new ArrayList<>();
    private double totalAssets;
    private double totalLiabilities;
    private double netWorth;

    public List<NetWorthTableRow> getAssetRows() { return assetRows; }
    public void setAssetRows(List<NetWorthTableRow> assets) {this.assetRows = assets;}

    public List<NetWorthTableRow> getLiabilityRows() { return liabilityRows; }
    public void setLiabilityRows(List<NetWorthTableRow> liabilities) {this.liabilityRows = liabilities;}

    public double getTotalAssets() { return totalAssets; }
    public void setTotalAssets(double totalAssets) {this.totalAssets = totalAssets;}

    public double getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(double totalLiabilities) {this.totalLiabilities = totalLiabilities;}

    public double getNetWorth() { return netWorth; }
    public void setNetWorth(double netWorth) {this.netWorth = netWorth;}
}
