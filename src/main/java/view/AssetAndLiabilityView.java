package view;

import entity.AssetAndLiability;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateController;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateState;
import interface_adaptor.asset_and_liability_apply_rate.AssetAndLiabilityApplyRateViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this view is contained in a separate JFrame that only pops out when we click "Add Asset/Liability" button
// in the viewpage that lists all assets and liabilities

public class AssetAndLiabilityView extends JPanel implements PropertyChangeListener {

    private AssetAndLiabilityApplyRateController assetAndLiabilityApplyRateController;
    private AssetAndLiabilityApplyRateViewModel assetAndLiabilityApplyRateViewModel;

    private JComboBox<String> assetAndLiabilityDropDown;
    private JButton addAssetAndLiabilityButton = new JButton("Add Asset/Liability");

    public AssetAndLiabilityView(AssetAndLiabilityApplyRateViewModel assetAndLiabilityApplyRateViewModel) {
        this.assetAndLiabilityApplyRateViewModel = assetAndLiabilityApplyRateViewModel;
        this.assetAndLiabilityApplyRateViewModel.addPropertyChangeListener(this);

        // this part refreshes all current amount of assets and liabilities before the window is open
        AssetAndLiabilityApplyRateState assetAndLiabilityState = assetAndLiabilityApplyRateViewModel.getState();
        List<AssetAndLiability> assetAndLiabilities = assetAndLiabilityState.getAllAssetAndLiabilityList();
        Map<String, AssetAndLiability> assetAndLiabilityIDToAssetAndLiability = new HashMap<>();

        String[] IDs = new String[assetAndLiabilities.size()];
        String[] names = new String[assetAndLiabilities.size()];
        for (int i = 0; i < assetAndLiabilities.size(); i++) {
            IDs[i] = assetAndLiabilities.get(i).getID();
            names[i] = assetAndLiabilities.get(i).getName();
            assetAndLiabilityIDToAssetAndLiability.put(IDs[i], assetAndLiabilities.get(i));
        }

        assetAndLiabilityApplyRateController.execute(IDs);

        // this part builds up the window
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Assets And Liabilities");
        titlePanel.add(title);

        assetAndLiabilityDropDown = new JComboBox<>(names);

        JPanel listPanel = new JPanel();
        JLabel listLabel = new JLabel("All Assets And Liabilities");
        listPanel.add(listLabel);
        listPanel.add(assetAndLiabilityDropDown);

        // this button will eventually open up the add asset/liability window
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addAssetAndLiabilityButton);

        // initially, no asset/liability will be displayed since we haven't selected one
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Asset/Liability Name: ");
        namePanel.add(nameLabel);
        namePanel.setVisible(false);

        JPanel IDPanel = new JPanel();
        JLabel IDLabel = new JLabel("Asset/Liability ID: ");
        IDPanel.add(IDLabel);
        IDPanel.setVisible(false);

        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Type ");
        typePanel.add(typeLabel);
        typePanel.setVisible(false);

        JPanel dateCreatedPanel = new JPanel();
        JLabel dateCreatedLabel = new JLabel("Date Created: ");
        dateCreatedPanel.add(dateCreatedLabel);
        dateCreatedPanel.setVisible(false);

        JPanel initialAmountPanel = new JPanel();
        JLabel initialAmountLabel = new JLabel("Initial Amount: ");
        initialAmountPanel.add(initialAmountLabel);
        initialAmountPanel.setVisible(false);

        JPanel currentAmountPanel = new JPanel();
        JLabel currentAmountLabel = new JLabel("Current Amount: ");
        currentAmountPanel.add(currentAmountLabel);
        currentAmountPanel.setVisible(false);

        JPanel interestRatePanel = new JPanel();
        JLabel interestRateLabel = new JLabel("Interest Rate: ");
        interestRatePanel.add(interestRateLabel);
        interestRatePanel.setVisible(false);

        JPanel ratePeriodPanel = new JPanel();
        JLabel ratePeriodLabel = new JLabel("Rate Period: ");
        ratePeriodPanel.add(ratePeriodLabel);
        ratePeriodPanel.setVisible(false);

        // listens to any asset/liability selected in the dropdown, and displays accordingly
        assetAndLiabilityDropDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String selectedID = assetAndLiabilityDropDown.getSelectedItem().toString();
                AssetAndLiability assetAndLiability = assetAndLiabilityIDToAssetAndLiability.get(selectedID);

                nameLabel.setText("Asset/Liability Name: " + assetAndLiability.getName());
                IDLabel.setText("Asset/Liability ID: " + assetAndLiability.getID());
                typeLabel.setText("Type " + assetAndLiability.getType().toString());
                dateCreatedLabel.setText("Date Created: " + assetAndLiability.getDateCreated().toString());
                initialAmountLabel.setText("Initial Amount: " + assetAndLiability.getInitialAmount());
                currentAmountLabel.setText("Current Amount: " + assetAndLiability.getCurrentAmount());
                interestRateLabel.setText("Interest Rate: " + assetAndLiability.getInterestRate());
                ratePeriodLabel.setText("Rate Period: " + assetAndLiability.getRatePeriod().toString());

                namePanel.setVisible(true);
                IDPanel.setVisible(true);
                typePanel.setVisible(true);
                dateCreatedPanel.setVisible(true);
                initialAmountPanel.setVisible(true);
                currentAmountPanel.setVisible(true);
                interestRatePanel.setVisible(true);
                ratePeriodPanel.setVisible(true);
            }
        });

        this.add(titlePanel);
        this.add(listPanel);
        this.add(namePanel);
        this.add(IDPanel);
        this.add(typePanel);
        this.add(initialAmountPanel);
        this.add(currentAmountPanel);
        this.add(interestRatePanel);
        this.add(ratePeriodPanel);
        this.add(buttonPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("asset/liability success")) {
            AssetAndLiabilityApplyRateState assetAndLiabilityApplyRateState = (AssetAndLiabilityApplyRateState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, assetAndLiabilityApplyRateState.getPopupMessage());
        } else if (evt.getPropertyName().equals("asset/liability fail")) {
            AssetAndLiabilityApplyRateState assetAndLiabilityApplyRateState = (AssetAndLiabilityApplyRateState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, assetAndLiabilityApplyRateState.getPopupMessage());
        }
    }

    public void setAddAssetAndLiabilityController(AssetAndLiabilityApplyRateController assetAndLiabilityApplyRateController) {
        this.assetAndLiabilityApplyRateController = assetAndLiabilityApplyRateController;
    }

    public String getViewName() {
        return this.assetAndLiabilityApplyRateViewModel.getViewName();
    }
}