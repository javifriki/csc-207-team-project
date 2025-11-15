package use_case.net_worth_table;

public class NetWorthTableInputData {
    private final String userID;

    public NetWorthTableInputData(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return this.userID;
    } //necessary?
}

//from user id, account numbers should be get
