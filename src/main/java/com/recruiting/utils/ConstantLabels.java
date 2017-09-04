package com.recruiting.utils;

import com.recruiting.model.modelUtils.StringItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martha on 5/4/2017.
 */
public class ConstantLabels {

    public static List<StringItemModel> REGISTRATION_TYPES_LIST = registrationTypes();
    public static List<StringItemModel> TIME_PERIODS_LIST = timePeriod();


    private static List<StringItemModel> registrationTypes() {
        List<StringItemModel> registrationTypes = new ArrayList<>(2);
        registrationTypes.add(new StringItemModel("Company"));
        registrationTypes.add(new StringItemModel("Candidate"));
        return registrationTypes;
    }

    private static List<StringItemModel> timePeriod() {
        List<StringItemModel> timePeriod = new ArrayList<>();
        timePeriod.add(new StringItemModel("2 Weeks"));
        timePeriod.add(new StringItemModel("2 Weeks to 1 Month"));
        timePeriod.add(new StringItemModel("1 to 2 Months"));
        timePeriod.add(new StringItemModel("2 to 3 Months"));
        timePeriod.add(new StringItemModel("3 to 6 Months"));
        timePeriod.add(new StringItemModel("6 Months to a Year"));

        return timePeriod;
    }

}
