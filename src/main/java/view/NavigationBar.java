package view;

import interface_adaptor.ViewManagerViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class NavigationBar extends JPanel {
    private final ViewManagerViewModel viewManagerViewModel;
    private final Map<String, JButton> navigationButtons;

    private static final int SIDEBAR_WIDTH = 250;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 40;
    private static final int VERTICAL_SPACING = 10;
    private static final int TOP_PADDING = 20;
    private static final Color BUTTON_BACKGROUND = new Color(240, 240, 240);
    private static final Color BORDER_COLOR = Color.LIGHT_GRAY;

    public NavigationBar(ViewManagerViewModel viewManagerViewModel) {
        this.viewManagerViewModel = viewManagerViewModel;
        this.navigationButtons = new LinkedHashMap<>();

        initializeUI();
        createButtons();
        setupLayout();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));
        setBackground(Color.WHITE);
        add(Box.createRigidArea(new Dimension(0, TOP_PADDING)));
    }

    private void createButtons() {
        addButton("Add Transaction", "addTransaction");
        addButton("Monthly Summary", "monthlySummary");
        addButton("Add Asset/Liability", "addAssetAndLiability");
        addButton("Asset/Liability List", "assetAndLiabilityApplyRate");
        addButton("Add Account", "addAccount");
        addButton("View Accounts", "viewAccounts");
        addButton("Monthly Report", "monthlyReport");
        addButton("Currency Converter", "currencyConverter");
        addButton("Net Worth Table", "netWorthTable");
    }

    private void addButton(String buttonText, String viewState) {
        JButton button = createStyledButton(buttonText);
        button.addActionListener(e -> navigateToView(viewState));
        navigationButtons.put(viewState, button);
        addButtonToLayout(button);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH + 20, BUTTON_HEIGHT));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBackground(BUTTON_BACKGROUND);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(5, 15, 5, 15));

        return button;
    }

    private void addButtonToLayout(JButton button) {
        add(button);
        add(Box.createRigidArea(new Dimension(0, VERTICAL_SPACING)));
    }

    private void setupLayout() {
        if (getComponentCount() > 0) {
            remove(getComponentCount() - 1);
        }

        add(Box.createVerticalGlue());
    }

    private void navigateToView(String viewState) {
        viewManagerViewModel.setState(viewState);
        viewManagerViewModel.firePropertyChange("viewChange");
    }

    public void setButtonEnabled(String viewState, boolean enabled) {
        JButton button = navigationButtons.get(viewState);
        if (button != null) {
            button.setEnabled(enabled);
        }
    }

    public void setActiveButton(String viewState) {
        navigationButtons.forEach((state, button) -> {
            boolean isActive = state.equals(viewState);
            button.setBackground(isActive ? Color.LIGHT_GRAY : BUTTON_BACKGROUND);
            button.setFont(button.getFont().deriveFont(
                    isActive ? Font.BOLD : Font.PLAIN
            ));
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIDEBAR_WIDTH, super.getPreferredSize().height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(SIDEBAR_WIDTH, super.getMinimumSize().height);
    }
}