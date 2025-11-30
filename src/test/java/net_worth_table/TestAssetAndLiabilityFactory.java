package net_worth_table;

import entity.AssetAndLiability;

import java.time.LocalDate;

public class TestAssetAndLiabilityFactory {

    //has fixed Date and InterestRate params because use case doesn't mess with that
    public static AssetAndLiability asset(String name, double initialAmount, String ID, AssetAndLiability.RatePeriod ratePeriod) {
        return new AssetAndLiability(name, AssetAndLiability.Type.ASSET, ratePeriod, initialAmount, initialAmount, ID, LocalDate.now(), 0.01);
    }

    public static AssetAndLiability liability(String name, double initialAmount, String ID, AssetAndLiability.RatePeriod ratePeriod) {
        return new AssetAndLiability(name, AssetAndLiability.Type.LIABILITY, ratePeriod, initialAmount, initialAmount, ID, LocalDate.now(), 0.01);
    }
}
