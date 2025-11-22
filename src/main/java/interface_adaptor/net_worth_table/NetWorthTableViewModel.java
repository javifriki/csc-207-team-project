package interface_adaptor.net_worth_table;

import interface_adaptor.ViewModel;

public class NetWorthTableViewModel extends ViewModel<NetWorthTableState> {
    public NetWorthTableViewModel() {
        super("netWorthTable");
        setState(new NetWorthTableState());
    }
}
