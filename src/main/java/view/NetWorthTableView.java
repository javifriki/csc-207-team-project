package view;

import interface_adaptor.net_worth_table.NetWorthTableController;
import interface_adaptor.net_worth_table.NetWorthTableTableModel;
import interface_adaptor.net_worth_table.NetWorthTableViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class NetWorthTableView extends JPanel {

    private NetWorthTableController netWorthTableController;
    private final NetWorthTableViewModel netWorthTableViewModel;
    private final JTable netWorthTable;
    private final JLabel assetTotal, liabilityTotal, netWorthTotal;
    private final JButton refreshButton;
    EmptyBorder padding = new EmptyBorder(30, 30, 30, 30);

    public NetWorthTableView(NetWorthTableViewModel netWorthTableViewModel) {
        this.netWorthTableViewModel = netWorthTableViewModel;

        NetWorthTableTableModel tableModel = new NetWorthTableTableModel(netWorthTableViewModel.getState());
        this.netWorthTable = new JTable(tableModel);
        refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> refreshData());
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JScrollPane table = new JScrollPane(netWorthTable);
        table.setBorder(padding);

        JPanel asset = new JPanel();
        JLabel assetLabel = new JLabel("Total Assets: ");
        assetTotal = new JLabel("0");
        asset.add(assetLabel);
        asset.add(assetTotal);

        JPanel liability = new JPanel();
        JLabel liabilityLabel = new JLabel("Total Liabilities: ");
        liabilityTotal = new JLabel("0");
        liability.add(liabilityLabel);
        liability.add(liabilityTotal);

        JPanel netWorth = new JPanel();
        JLabel netWorthLabel = new JLabel("Net Worth: ");
        netWorthTotal = new JLabel("0");
        netWorth.add(netWorthLabel);
        netWorth.add(netWorthTotal);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(asset);
        bottom.add(liability);
        bottom.add(netWorth);
        bottom.add(refreshButton);

        this.add(table);
        this.add(bottom);
    }

    public void setNetWorthTableController(NetWorthTableController netWorthTableController) {
        this.netWorthTableController = netWorthTableController;
        if (this.netWorthTableController != null) {
            refreshData();
        }
    }
    public void refreshData() {
        if (netWorthTableController != null) {
            netWorthTableController.execute();

            NetWorthTableTableModel newModel = new NetWorthTableTableModel(netWorthTableViewModel.getState());
            netWorthTable.setModel(newModel);

            //format to 2 decimal places
            DecimalFormat df = new DecimalFormat("#,##0.00");
            assetTotal.setText("$" + df.format(netWorthTableViewModel.getState().getTotalAssets()));
            liabilityTotal.setText("$" + df.format(netWorthTableViewModel.getState().getTotalLiabilities()));
            netWorthTotal.setText("$" + df.format(netWorthTableViewModel.getState().getNetWorth()));

            revalidate();
            repaint();
        }
    }

    public String getViewName() {
        return this.netWorthTableViewModel.getViewName();
    }
}