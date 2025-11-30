package view;


import interface_adaptor.currency_converter.CurrencyConverterController;
import interface_adaptor.currency_converter.CurrencyConverterViewModel;


import javax.swing.*;
import java.awt.*;


public class CurrencyConverterView extends JPanel {


    private CurrencyConverterController controller;
    private final CurrencyConverterViewModel viewModel;


    private final JTextField amountField = new JTextField();
    private final String[] currencies = {
            "CAD", "USD", "EUR", "GBP", "JPY", "CNY", "AUD", "CHF",
            "HKD", "SGD", "NZD", "MXN", "INR", "BRL", "ZAR", "KRW",
            "SEK", "NOK", "DKK", "PLN", "RUB", "TRY", "THB", "MYR"
    };
    private final JComboBox<String> fromCurrency =
            new JComboBox<>(currencies);
    private final JComboBox<String> toCurrency =
            new JComboBox<>(currencies);
    private final JLabel resultLabel = new JLabel("");


    public CurrencyConverterView(CurrencyConverterViewModel viewModel) {
        this.viewModel = viewModel;
        setupUI();


        viewModel.addPropertyChangeListener(evt -> {
            if ("result".equals(evt.getPropertyName())) {
                resultLabel.setText(viewModel.getResult());
            }
        });
    }




    /** Required by AppBuilder */
    public String getViewName() {
        return "currencyConverter";
    }


    /** Used in AppBuilder to connect controller */
    public void setCurrencyConverterController(CurrencyConverterController controller) {
        this.controller = controller;
    }


    private void setupUI() {
        setLayout(new BorderLayout());


        JPanel content = new JPanel();
        content.setLayout(new GridLayout(6, 2, 10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));


        content.add(new JLabel("Amount:"));
        content.add(amountField);


        content.add(new JLabel("From Currency:"));
        content.add(fromCurrency);


        content.add(new JLabel("To Currency:"));
        content.add(toCurrency);


        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                String from = (String) fromCurrency.getSelectedItem();
                String to = (String) toCurrency.getSelectedItem();


                controller.convert(amt, from, to);
                resultLabel.setText(viewModel.getResult());
            } catch (Exception ex) {
                resultLabel.setText("Invalid input");
            }
        });


        content.add(new JLabel(""));
        content.add(convertButton);


        content.add(new JLabel("Result:"));
        content.add(resultLabel);


        add(content, BorderLayout.CENTER);
    }
}

