package entity;
import entity.AssetAndLiability;

import java.time.LocalDate;

public class AssetAndLiabilityBuilder {
    private String name;
    private String ID;
    private AssetAndLiability.Type type;
    private AssetAndLiability.RatePeriod ratePeriod;
    private double amount;
    private LocalDate dateCreated;
    private double interestRate;
    private LocalDate dateUpdated;

    public AssetAndLiabilityBuilder addName(String name){
        this.name=name;
        return this;
    }

    public AssetAndLiabilityBuilder addID(String ID){
        this.ID=ID;
        return this;
    }

    public AssetAndLiabilityBuilder addType(AssetAndLiability.Type type){
        this.type=type;
        return this;
    }

    public AssetAndLiabilityBuilder addRatePeriod(AssetAndLiability.RatePeriod ratePeriod){
        this.ratePeriod=ratePeriod;
        return this;
    }

    public AssetAndLiabilityBuilder addAmount(double amount){
        this.amount=amount;
        return this;
    }

    public AssetAndLiabilityBuilder addDateCreated(LocalDate dateCreated){
        this.dateCreated=dateCreated;
        return this;
    }

    public AssetAndLiabilityBuilder addInterestRate(double interestRate){
        this.interestRate=interestRate;
        return this;
    }

    public AssetAndLiabilityBuilder addDateUpdated(LocalDate dateUpdated){
        this.dateUpdated=dateUpdated;
        return this;
    }

    public AssetAndLiability build(){
        return new AssetAndLiability(name, type, ratePeriod, amount, ID, dateCreated,
                dateUpdated, interestRate);
    }
}
