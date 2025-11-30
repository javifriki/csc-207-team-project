package interface_adaptor.net_worth_table;

import use_case.net_worth_table.NetWorthTableInputBoundary;
import use_case.net_worth_table.NetWorthTableInputData;

public class NetWorthTableController {
    private final NetWorthTableInputBoundary interactor;

    public NetWorthTableController(NetWorthTableInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        NetWorthTableInputData inputData = new NetWorthTableInputData();
        interactor.execute(inputData);
    }
}
