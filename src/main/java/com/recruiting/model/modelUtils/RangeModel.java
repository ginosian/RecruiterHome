package com.recruiting.model.modelUtils;

/**
 * Created by Martha on 6/5/2017.
 */
public class RangeModel<T extends Number> extends Model {

    private T originalValue;
    private T upperLimit;
    private int lowerLimitRate;
    private T lowerLimit;
    private int upperLimitRate;
    private int changeRate;

    public RangeModel() {
    }

    public RangeModel(int lowerLimitRate, int upperLimitRate, T originalValue) {
        this.lowerLimitRate = lowerLimitRate;
        this.upperLimitRate = upperLimitRate;
        this.originalValue = originalValue;
        recountAllFields(originalValue);
    }

    public void setLowerLimitRate(int lowerLimitRate) {
        this.lowerLimitRate = lowerLimitRate;
        recountAllFields(originalValue);
    }

    public void setUpperLimitRate(int upperLimitRate) {
        this.upperLimitRate = upperLimitRate;
        recountAllFields(originalValue);
    }


    public int getLowerLimitRate() {
        return lowerLimitRate;
    }

    public int getUpperLimitRate() {
        return upperLimitRate;
    }

    public int getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(int changeRate) {
        this.changeRate = changeRate;
        recountAllFields(originalValue);
    }

    public T getUpperLimit() {
        return upperLimit;
    }

    public T getLowerLimit() {
        return lowerLimit;
    }

    public T getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(T originalValue) {
        this.originalValue = originalValue;
        recountAllFields(originalValue);
    }

    private void recountAllFields(T originalValue) {
        if (originalValue instanceof Integer) {
            recountAllFields(originalValue.intValue());
        } else if (originalValue instanceof Double) {
            recountAllFields(originalValue.doubleValue());
        } else if (originalValue instanceof Long) {
            recountAllFields(originalValue.longValue());
        } else if (originalValue instanceof Float) {
            recountAllFields(originalValue.floatValue());
        }
    }

    private void recountAllFields(Integer parsedOriginalValue) {
        int upperLimitAddition = parsedOriginalValue * ((upperLimitRate + (upperLimitRate * changeRate / 100)) / 100);
        int lowerLimitReduction = parsedOriginalValue * ((lowerLimitRate + (lowerLimitRate * changeRate / 100)) / 100);
        upperLimit = (T) correctRange(parsedOriginalValue + upperLimitAddition);
        lowerLimit = (T) correctRange(parsedOriginalValue - lowerLimitReduction);
    }

    private void recountAllFields(Double parsedOriginalValue) {
        double upperLimitAddition = parsedOriginalValue * ((upperLimitRate + (upperLimitRate * changeRate / 100)) / 100);
        double lowerLimitReduction = parsedOriginalValue * ((lowerLimitRate + (lowerLimitRate * changeRate / 100)) / 100);
        upperLimit = (T) correctRange(parsedOriginalValue + upperLimitAddition);
        lowerLimit = (T) correctRange(parsedOriginalValue - lowerLimitReduction);
    }

    private void recountAllFields(Long parsedOriginalValue) {
        long upperLimitAddition = parsedOriginalValue * ((upperLimitRate + (upperLimitRate * changeRate / 100)) / 100);
        long lowerLimitReduction = parsedOriginalValue * ((lowerLimitRate + (lowerLimitRate * changeRate / 100)) / 100);
        upperLimit = (T) correctRange(parsedOriginalValue + upperLimitAddition);
        lowerLimit = (T) correctRange(parsedOriginalValue - lowerLimitReduction);
    }

    private void recountAllFields(Float parsedOriginalValue) {
        float upperLimitAddition = parsedOriginalValue * ((upperLimitRate + (upperLimitRate * changeRate / 100)) / 100);
        float lowerLimitReduction = parsedOriginalValue * ((lowerLimitRate + (lowerLimitRate * changeRate / 100)) / 100);
        upperLimit = (T) correctRange(parsedOriginalValue + upperLimitAddition);
        lowerLimit = (T) correctRange(parsedOriginalValue - lowerLimitReduction);
    }

    private Number correctRange(Number number) {
        if (number.intValue() < 1) return 1;
        if (number.intValue() > 100) return 100;
        else return number;
    }
}
