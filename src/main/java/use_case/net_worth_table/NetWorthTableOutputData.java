package use_case.net_worth_table;

import entity.AssetAndLiability;

import java.util.List;

public class NetWorthTableOutputData {
    private final List<AssetAndLiability> allAssets;
    private final List<AssetAndLiability> allLiabilities;
    private final double totalAssets;
    private final double totalLiabilities;
    private final double netWorth;

    public NetWorthTableOutputData(List<AssetAndLiability> allAssets,
                                   List<AssetAndLiability> allLiabilities,
                                   double totalAssets,
                                   double totalLiabilities) {
        this.allAssets = allAssets;
        this.allLiabilities = allLiabilities;
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.netWorth = totalAssets - totalLiabilities;
    }

    public List<AssetAndLiability> getAllAssets() {
        return allAssets;
    }

    public List<AssetAndLiability> getAllLiabilities() {
        return allLiabilities;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public double getTotalLiabilities() {
        return totalLiabilities;
    }

    public double getNetWorth() {
        return netWorth;
    }
}
