package interface_adaptor.add_transaction;

import use_case.net_worth_table.NetWorthTableOutputBoundary;
import use_case.net_worth_table.NetWorthTableOutputData;

public class NetWorthTablePresenter implements NetWorthTableOutputBoundary {

    @Override
    public void present(NetWorthTableOutputData netWorthTableOutputData) {
        // display the damn table
    }
}
