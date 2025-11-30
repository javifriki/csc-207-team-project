package interface_adaptor.net_worth_table;

//helper class
public class NetWorthTableRow {
    private final String name;
    private final Double amount;

    public NetWorthTableRow(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() { return name; }

    public Object getAmount() {
        return (amount != null) ? String.format("$%.2f", amount) : "";
    }
}
