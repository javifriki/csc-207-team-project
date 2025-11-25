package view;

import entity.AssetAndLiability;
import interface_adaptor.add_asset_and_liability.AddAssetAndLiabilityController;
import interface_adaptor.add_asset_and_liability.AddAssetAndLiabilityState;
import interface_adaptor.add_asset_and_liability.AddAssetAndLiabilityViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Arrays;

// this view is contained in a separate JFrame that only pops out when we click "Add Asset/Liability" button
// in the viewpage that lists all assets and liabilities

public class AddAssetAndLiabilityView extends JPanel implements PropertyChangeListener {

    private AddAssetAndLiabilityController addAssetAndLiabilityController;
    private AddAssetAndLiabilityViewModel addAssetAndLiabilityViewModel;

    private JTextField initialAmountText = new JTextField(10);
    private JTextField interestRateText = new JTextField(10);
    private JTextField nameText = new JTextField(10);
    private JTextField IDText = new JTextField(10);
    private JComboBox<String> assetAndLiabilityTypeDropDown = new JComboBox<>(
            Arrays.stream(AssetAndLiability.Type.values())
                    .map(type -> type.toString())
                    .toArray(String[]::new));
    private JComboBox<String> assetAndLiabilityRatePeriodDropDown = new JComboBox<>(
            Arrays.stream(AssetAndLiability.RatePeriod.values())
                    .map(type -> type.toString())
                    .toArray(String[]::new));
    private JButton addAssetAndLiabilityButton = new JButton("Add Asset/Liability");

    public AddAssetAndLiabilityView(AddAssetAndLiabilityViewModel addAssetAndLiabilityViewModel) {
        this.addAssetAndLiabilityViewModel = addAssetAndLiabilityViewModel;
        this.addAssetAndLiabilityViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Asset/Liability Name: ");
        namePanel.add(nameLabel);
        namePanel.add(nameText);

        JPanel IDPanel = new JPanel();
        JLabel IDLabel = new JLabel("Asset/Liability ID: ");
        IDPanel.add(IDLabel);
        IDPanel.add(IDText);

        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Asset/Liability? ");
        typePanel.add(typeLabel);
        typePanel.add(assetAndLiabilityTypeDropDown);

        JPanel initialAmountPanel = new JPanel();
        JLabel initialAmountLabel = new JLabel("Asset/Liability Amount: ");
        initialAmountPanel.add(initialAmountLabel);
        initialAmountPanel.add(initialAmountText);

        JPanel interestRatePanel = new JPanel();
        JLabel interestRateLabel = new JLabel("InterestRate: ");
        interestRatePanel.add(interestRateLabel);
        interestRatePanel.add(interestRateText);

        JPanel ratePeriodPanel = new JPanel();
        JLabel ratePeriodLabel = new JLabel("Rate Period: ");
        ratePeriodPanel.add(ratePeriodLabel);
        ratePeriodPanel.add(assetAndLiabilityRatePeriodDropDown);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addAssetAndLiabilityButton);

        addAssetAndLiabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String type = (String) assetAndLiabilityTypeDropDown.getSelectedItem();
                String ID = IDText.getText();
                double initialAmount = Double.parseDouble(initialAmountText.getText());
                String date = LocalDate.now().toString();
                String ratePeriod = (String) assetAndLiabilityRatePeriodDropDown.getSelectedItem();
                double interestRate = Double.parseDouble(interestRateText.getText());

                addAssetAndLiabilityController.execute(
                        name,
                        type,
                        ID,
                        initialAmount,
                        date,
                        ratePeriod,
                        interestRate
                );

                nameText.setText("");
                IDText.setText("");
                initialAmountText.setText("");
                interestRateText.setText("");

                namePanel.revalidate();
                namePanel.repaint();
                IDPanel.revalidate();
                IDPanel.repaint();
                initialAmountPanel.revalidate();
                initialAmountPanel.repaint();
                interestRatePanel.revalidate();
                interestRatePanel.repaint();
            }
        });

        this.add(namePanel);
        this.add(IDPanel);
        this.add(typePanel);
        this.add(initialAmountPanel);
        this.add(interestRatePanel);
        this.add(ratePeriodPanel);
        this.add(buttonPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("asset/liability success")) {
            AddAssetAndLiabilityState addAssetAndLiabilityState = (AddAssetAndLiabilityState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, addAssetAndLiabilityState.getPopupMessage());
        } else if (evt.getPropertyName().equals("asset/liability fail")) {
            AddAssetAndLiabilityState addAssetAndLiabilityState = (AddAssetAndLiabilityState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, addAssetAndLiabilityState.getPopupMessage());
        }
    }

    public void setAddAssetAndLiabilityController(AddAssetAndLiabilityController addAssetAndLiabilityController) {
        this.addAssetAndLiabilityController = addAssetAndLiabilityController;
    }

    public String getViewName() {
        return this.addAssetAndLiabilityViewModel.getViewName();
    }
}