package interface_adaptor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewModel<T> {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private T state;

    private final String viewName;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public String getViewName() {
        return this.viewName;
    }

    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
