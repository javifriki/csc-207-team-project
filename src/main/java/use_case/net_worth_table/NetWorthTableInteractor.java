package use_case.net_worth_table;

import data_access.AccountDataAccessObject;
import data_access.AssetAndLiabilityDataAccessObject;
import entity.Account;
import entity.AssetAndLiability;

import java.util.ArrayList;
import java.util.List;

public class NetWorthTableInteractor implements  NetWorthTableInputBoundary {
    private final AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject;

    public NetWorthTableInteractor (AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
    }

    @Override
    public void execute(NetWorthTableInputData netWorthTableInputData) {
        List<AssetAndLiability> capital = assetAndLiabilityDataAccessObject.getAllAssetAndLiabilities();
        double totalAssets = 0.0;
        double totalLiabilities = 0.0;
        List<AssetAndLiability> allAssets = new ArrayList<>();
        List<AssetAndLiability> allLiabilities = new ArrayList<>();

        //sort assests and liabilities into separate arrays
        //get the total somehow ??? look at entity methods

        NetWorthTableOutputData outputData = new NetWorthTableOutputData(
                allAssets,
                allLiabilities,
                totalAssets,
                totalLiabilities
        );

    }

    //presnt
}
