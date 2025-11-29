package interface_adaptor.monthly_report;

import interface_adaptor.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MonthlyReportViewModel extends ViewModel {

    public static final String VIEW_NAME = "monthlyReport";

    private MonthlyReportState state = new MonthlyReportState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public MonthlyReportViewModel() {
        super(VIEW_NAME);
    }

    public MonthlyReportState getState() {
        return state;
    }

    public void setState(MonthlyReportState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("monthlyReport", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
