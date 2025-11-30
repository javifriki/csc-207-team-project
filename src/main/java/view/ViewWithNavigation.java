package view;


import interface_adaptor.ViewManagerViewModel;


import javax.swing.*;
import java.awt.*;


/**
 * Wrapper panel that combines a view with a navigation bar at the bottom
 */
public class ViewWithNavigation extends JPanel {
    private final JPanel viewPanel;
    private final NavigationBar navigationBar;


    public ViewWithNavigation(JPanel view, ViewManagerViewModel viewManagerViewModel) {
        this.viewPanel = view;
        this.navigationBar = new NavigationBar(viewManagerViewModel);


        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);
        add(navigationBar, BorderLayout.SOUTH);
    }


    public JPanel getViewPanel() {
        return viewPanel;
    }
}

