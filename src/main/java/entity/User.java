package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String userID;
    private final String password;
    private final String name;

    private final List<String> accountNumbers;
    private final List<AssetAndLiability>  assetsList;
    private final List<AssetAndLiability> liabilitiesList;

    public User(String userID, String password, String name) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.accountNumbers = new ArrayList<>();
        this.assetsList = new ArrayList<>();
        this.liabilitiesList = new ArrayList<>();
    }

    public String getName() {return name;}

    public String getUsername() {return userID;}

    public String getPassword() {return password;}

}
