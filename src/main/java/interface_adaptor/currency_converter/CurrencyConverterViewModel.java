package interface_adaptor.currency_converter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CurrencyConverterViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String result = "";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("result", null, result);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
