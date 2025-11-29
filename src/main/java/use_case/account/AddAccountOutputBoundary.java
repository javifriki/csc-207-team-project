package use_case.account;

public interface AddAccountOutputBoundary {
    void prepareAccountSuccessView (AddAccountOutputData addAccountOutputData);
    void prepareAccountFailView (String errorMessage);
}
