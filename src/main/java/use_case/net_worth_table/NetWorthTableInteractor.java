package use_case.net_worth_table;

import data_access.AssetAndLiabilityDataAccessObject;
import entity.AssetAndLiability;

import java.util.ArrayList;
import java.util.List;

public class NetWorthTableInteractor implements  NetWorthTableInputBoundary {
    private final AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject;
    private final NetWorthTableOutputBoundary presenter;

    public NetWorthTableInteractor (AssetAndLiabilityDataAccessObject assetAndLiabilityDataAccessObject,
                                    NetWorthTableOutputBoundary presenter) {
        this.assetAndLiabilityDataAccessObject = assetAndLiabilityDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(NetWorthTableInputData netWorthTableInputData) {
        List<AssetAndLiability> capitals = assetAndLiabilityDataAccessObject.getAllAssetAndLiabilities();
        double totalAssets = 0.0;
        double totalLiabilities = 0.0;
        List<AssetAndLiability> allAssets = new ArrayList<>();
        List<AssetAndLiability> allLiabilities = new ArrayList<>();

        if (capitals != null) {
            for (AssetAndLiability capital : capitals) {
                switch (capital.getType()) {
                    case ASSET:
                        allAssets.add(capital);
                        totalAssets += capital.getCurrentAmount();
                        break;
                    case LIABILITY:
                        allLiabilities.add(capital);
                        totalLiabilities += capital.getCurrentAmount();
                        break;
                }
            }
        }

        NetWorthTableOutputData outputData = new NetWorthTableOutputData(
                allAssets,
                allLiabilities,
                totalAssets,
                totalLiabilities
        );

        presenter.present(outputData);
    }
}
