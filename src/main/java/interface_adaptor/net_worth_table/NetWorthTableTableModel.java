package interface_adaptor.net_worth_table;

import javax.swing.table.AbstractTableModel;
import java.util.List;

//table model for JTable
public class NetWorthTableTableModel extends AbstractTableModel {
    private final String[] columns = {"Asset", "Amount", "Liability", "Amount"};
    private final NetWorthTableState state;

    public NetWorthTableTableModel(NetWorthTableState state) {
        this.state = state;
    }

    @Override
    public int getRowCount() {
        return Math.max(state.getAssetRows().size(), state.getLiabilityRows().size());
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<NetWorthTableRow> assets = state.getAssetRows();
        List<NetWorthTableRow> liabilities = state.getLiabilityRows();

        switch (columnIndex) {
            case 0:
                return (rowIndex < assets.size()) ? assets.get(rowIndex).getName() : "";
            case 1:
                if (rowIndex < assets.size()) {
                    Object amount = assets.get(rowIndex).getAmount();
                    return (amount instanceof Double) ? String.format("$%.2f", (Double) amount) : amount;
                }
                return "";
            case 2:
                return (rowIndex < liabilities.size()) ? liabilities.get(rowIndex).getName() : "";
            case 3:
                if (rowIndex < liabilities.size()) {
                    Object amount = liabilities.get(rowIndex).getAmount();
                    return (amount instanceof Double) ? String.format("$%.2f", (Double) amount) : amount;
                }
                return "";
            default: return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}