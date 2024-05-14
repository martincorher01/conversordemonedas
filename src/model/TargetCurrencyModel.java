package model;

import com.google.gson.annotations.SerializedName;

public class TargetCurrencyModel extends CurrencyModel {
    @SerializedName("time_last_update_unix")
    private long timeLastUpdateUnix;
    @SerializedName("conversion_rate")
    private double conversionRate;
    @SerializedName("conversion_result")
    private double conversionResult;

    public double getConversionResult() {
        return conversionResult;
    }

    @Override
    public String toString() {
        return "TargetCurrencyModel{" +
                "timeLastUpdateUnix=" + timeLastUpdateUnix +
                ", conversionRate=" + conversionRate +
                ", conversionResult=" + conversionResult +
                '}';
    }
}
