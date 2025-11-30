package view;

import interface_adaptor.ViewManagerViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {

    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerViewModel viewManagerViewModel;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerViewModel viewManagerViewModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        this.viewManagerViewModel = viewManagerViewModel;
        this.viewManagerViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("viewChange")) {
            String newViewName = (String) evt.getNewValue();
            this.cardLayout.show(views, newViewName);
        }
    }
}
