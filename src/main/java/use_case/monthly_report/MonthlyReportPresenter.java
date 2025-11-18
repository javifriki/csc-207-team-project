package use_case.monthly_report;
import use_case.monthly_report.MonthlyReportOutputBoundary;
import use_case.monthly_report.MonthlyReportInputBoundary;
import use_case.monthly_report.MonthlyReportViewModel;
import use_case.monthly_report.MonthlyReportViewManagerModel;

public class MonthlyReportPresenter implements MonthlyReportOutputBoundary{
    private final MonthlyReportViewModel monthlyReportViewModel;
    private final MonthlyReportViewManagerModel monthlyReportViewManagerModel;

    public MonthlyReportPresenter(MonthlyReportViewModel monthlyReportViewModel,
                                  MonthlyReportViewManagerModel monthlyReportViewManagerModel) {
        this.monthlyReportViewManagerModel = monthlyReportViewManagerModel;
        this.monthlyReportViewModel = monthlyReportViewModel;
    }


//package interface_adapter.login;
//
//import interface_adapter.ViewManagerModel;
//import interface_adapter.logged_in.LoggedInState;
//import interface_adapter.logged_in.LoggedInViewModel;
//import use_case.login.LoginOutputBoundary;
//import use_case.login.LoginOutputData;
//
/// **
// * The Presenter for the Login Use Case.
// */
//public class LoginPresenter implements LoginOutputBoundary {
//
//    private final LoginViewModel loginViewModel;
//    private final LoggedInViewModel loggedInViewModel;
//    private final ViewManagerModel viewManagerModel;
//
//    public LoginPresenter(ViewManagerModel viewManagerModel,
//                          LoggedInViewModel loggedInViewModel,
//                          LoginViewModel loginViewModel) {
//        this.viewManagerModel = viewManagerModel;
//        this.loggedInViewModel = loggedInViewModel;
//        this.loginViewModel = loginViewModel;
//    }
//
//    @Override
//    public void prepareSuccessView(LoginOutputData response) {
//        // On success, switch to the logged in view.
//
//        final LoggedInState loggedInState = loggedInViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.setState(loggedInState);
//        this.loggedInViewModel.firePropertyChanged();
//
//        this.viewManagerModel.setState(loggedInViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareFailView(String error) {
//        final LoginState loginState = loginViewModel.getState();
//        loginState.setLoginError(error);
//        loginViewModel.firePropertyChanged();
//    }
//}