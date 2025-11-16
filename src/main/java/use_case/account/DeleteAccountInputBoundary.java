package use_case.account;

public interface DeleteAccountInputBoundary {
    void execute(String accountNumber);
}