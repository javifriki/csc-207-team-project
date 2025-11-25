package interface_adaptor.monthly_summary;

import use_case.monthly_summary.MonthlySummaryOutputBoundary;
import use_case.monthly_summary.MonthlySummaryResponseModel;

import java.util.List;
import java.util.stream.Collectors;

public class MonthlySummaryPresenter implements MonthlySummaryOutputBoundary {

    private final MonthlySummaryViewModel viewModel;

    public MonthlySummaryPresenter(MonthlySummaryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(MonthlySummaryResponseModel responseModel) {
        List<MonthlySummaryViewModel.MonthBarViewModel> bars =
                responseModel.getLastSixMonths().stream()
                        .map(ms -> new MonthlySummaryViewModel.MonthBarViewModel(
                                ms.month, ms.totalIncome, ms.totalSpending))
                        .collect(Collectors.toList());

        MonthlySummaryState state = viewModel.getState();
        state.setBars(bars);
        viewModel.firePropertyChange("summaryUpdated");
    }
}
