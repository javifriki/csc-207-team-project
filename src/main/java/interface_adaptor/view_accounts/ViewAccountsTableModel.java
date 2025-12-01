package interface_adaptor.view_accounts;

import entity.Account;
import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.List;

public class ViewAccountsTableModel extends AbstractTableModel {
    private final String[] columns = {"Account Number", "Account Type", "Balance"};
    private final ViewAccountsState state;

    public ViewAccountsTableModel(ViewAccountsState state) {
        this.state = state;
    }

    @Override
    public int getRowCount() {
        return state.getAccounts().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Account> accounts = state.getAccounts();
        if (rowIndex >= accounts.size()) {
            return "";
        }
        
        Account account = accounts.get(rowIndex);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        switch (columnIndex) {
            case 0:
                return account.getAccountNumber();
            case 1:
                return account.getAccountType().toString();
            case 2:
                return "$" + df.format(account.getAccountBalance());
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}


