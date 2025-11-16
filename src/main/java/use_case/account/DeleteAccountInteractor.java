package use_case.account;

public class DeleteAccountInteractor implements DeleteAccountInputBoundary {
    private final AccountDataAccessInterface accountDataAccess;

    public DeleteAccountInteractor(AccountDataAccessInterface accountDataAccess) {
        this.accountDataAccess = accountDataAccess;
    }

    @Override
    public void execute(String accountNumber) {

        accountDataAccess.deleteAccount(accountNumber);
    }
}