
package data_access;

import entity.AssetAndLiability;
import use_case.add_asset_and_liability.AssetAndLiabilityDataAccessInterface;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetAndLiabilityDataAccessObject implements AssetAndLiabilityDataAccessInterface {
    // Temporary Storage DAO, can incorporate persistent storage later by storing ALL ACCOUNT class fields in JSON
    private Map<String, AssetAndLiability> assetAndLiabilityIDToAssetAndLiability = new HashMap<>();
    private Map<String, AssetAndLiability.Type> assetAndLiabilityTypeHashMap = new HashMap<>();
    private Map<String, AssetAndLiability.RatePeriod> assetAndLiabilityRatePeriodHashMap = new HashMap<>();
    private String filename;

    public AssetAndLiabilityDataAccessObject(String filename) {
        this.filename = filename;

        for (AssetAndLiability.Type type : AssetAndLiability.Type.values()) {
            assetAndLiabilityTypeHashMap.put(type.toString(), type);
        }

        for (AssetAndLiability.RatePeriod ratePeriod : AssetAndLiability.RatePeriod.values()) {
            assetAndLiabilityRatePeriodHashMap.put(ratePeriod.toString(), ratePeriod);
        }

        loadAllAssetAndLiabilityData();
    }

    /*
    {
        "A0001": {
            "name": "Housing",
            "type": ASSET,
            "initialAmount": 200000.0,
            "currentAmount": 200000.0
            "dateCreated": "2025-10-01",
            "interestRate": 0.02,
            "ratePeriod": ANNUALLY
        },
        "L0002": {
            "name": "Student Loan",
            "type": LIABILITY,
            "initialAmount": 200000000000.0,
            "currentAmount": 40000000000000.0
            "dateCreated": "2025-10-09",
            "interestRate": 0.10,
            "ratePeriod": MONTHLY
        }
    }
    */

    private void loadAllAssetAndLiabilityData() {
        try {
            String contents = Files.readString(Path.of(this.filename)); // in JSON format
            JSONObject assetAndLiabilityIDToAssetAndLiability = new JSONObject(contents);
            for (String IDKey : assetAndLiabilityIDToAssetAndLiability.keySet()) {
                JSONObject assetAndLiabilityObj = assetAndLiabilityIDToAssetAndLiability.
                        getJSONObject(IDKey);

                String name = assetAndLiabilityObj.getString("name");

                AssetAndLiability.Type type = this.assetAndLiabilityTypeHashMap.
                        get(assetAndLiabilityObj.getString("type"));

                AssetAndLiability.RatePeriod ratePeriod = this.assetAndLiabilityRatePeriodHashMap.
                        get(assetAndLiabilityObj.getString("ratePeriod"));

                double initialAmount = assetAndLiabilityObj.getDouble("initialAmount");

                double currentAmount = assetAndLiabilityObj.getDouble("currentAmount");

                LocalDate dateCreated = LocalDate.parse(assetAndLiabilityObj.getString("dateCreated"));

                double interestRate = assetAndLiabilityObj.getDouble("interestRate");

                AssetAndLiability assetAndLiability = new AssetAndLiability(name, type, ratePeriod, initialAmount,
                                                            currentAmount, IDKey,
                                                            dateCreated, interestRate);

                this.assetAndLiabilityIDToAssetAndLiability.put(IDKey, assetAndLiability);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to Read Account Data JSON file");
        }
    }

    // Save all added assets/liabilities into JSON file
    private void saveAssetAndLiabilityData() {
        JSONObject baseRoot = new JSONObject();
        try {
            for (String IDKey : this.assetAndLiabilityIDToAssetAndLiability.keySet()) {
                AssetAndLiability assetAndLiability = this.assetAndLiabilityIDToAssetAndLiability.get(IDKey);
                String name = assetAndLiability.getName();
                double initialAmount = assetAndLiability.getInitialAmount();
                double currentAmount = assetAndLiability.getCurrentAmount();
                AssetAndLiability.Type type = assetAndLiability.getType();
                AssetAndLiability.RatePeriod ratePeriod = assetAndLiability.getRatePeriod();
                String dateCreated = assetAndLiability.getDateCreated().toString();
                double interestRate = assetAndLiability.getInterestRate();

                JSONObject assetAndLiabilityObj = new JSONObject();
                assetAndLiabilityObj.put("name", name);
                assetAndLiabilityObj.put("type", type);
                assetAndLiabilityObj.put("ratePeriod", ratePeriod);
                assetAndLiabilityObj.put("initialAmount", initialAmount);
                assetAndLiabilityObj.put("currentAmount", currentAmount);
                assetAndLiabilityObj.put("dateCreated", dateCreated);
                assetAndLiabilityObj.put("interestRate", interestRate);

                baseRoot.put(IDKey, assetAndLiabilityObj);
            }
            Files.writeString(Path.of(this.filename), baseRoot.toString(2));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Asset/Liability Data to the JSON file");
        }
    }

    // Adds a new Account
    @Override
    public void saveAssetAndLiability(AssetAndLiability assetAndLiability) {
        this.assetAndLiabilityIDToAssetAndLiability.put(assetAndLiability.getID(), assetAndLiability);
        this.saveAssetAndLiabilityData();
    }

    @Override
    public AssetAndLiability getAssetAndLiability(String assetAndLiabilityID) {
        return this.assetAndLiabilityIDToAssetAndLiability.get(assetAndLiabilityID);
    }

    @Override
    public List<AssetAndLiability> getAllAssetAndLiabilities() {
        return new ArrayList<>(assetAndLiabilityIDToAssetAndLiability.values());
    }

}
