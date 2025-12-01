package interface_adaptor.view_accounts;

import use_case.view_accounts.ViewAccountsInputBoundary;
import use_case.view_accounts.ViewAccountsInputData;

public class ViewAccountsController {
    private final ViewAccountsInputBoundary interactor;

    public ViewAccountsController(ViewAccountsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        ViewAccountsInputData inputData = new ViewAccountsInputData();
        interactor.execute(inputData);
    }
}


