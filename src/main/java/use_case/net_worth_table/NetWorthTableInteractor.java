package use_case.net_worth_table;

import data_access.AccountDataAccessObject;
import entity.Account;

public class NetWorthTableInteractor implements  NetWorthTableInputBoundary {
    private final AccountDataAccessObject accountDataAccessObject;

    public NetWorthTableInteractor (AccountDataAccessObject accountDataAccessObject) {
        this.accountDataAccessObject = accountDataAccessObject ;
    }

    @Override
    public void execute(NetWorthTableInputData netWorthTableInputData) {
        accountDataAc
    }
}
