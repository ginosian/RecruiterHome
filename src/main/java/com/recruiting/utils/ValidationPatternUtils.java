package com.recruiting.utils;

/**
 * Created by Martha on 5/8/2017.
 */
public class ValidationPatternUtils {

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String EMAIL_PATTERN_DEVELOPMENT = "^[^@]+@[a-zA-Z0-9._-]+\\.+[a-z._-]+$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,64}$";


    public static final String ALPHABET_PATTERN = "[a-zA-Z]+";
    public static final String ALPHABET_SPACE_PATTERN = "[a-zA-Z]+([\\s][a-zA-Z]+)*";
    public static final String USA_PHONE_NUMBER_PATTERN = "(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]\u200C\u200B)\\s*)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)([2-9]1[02-9]\u200C\u200B|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
    public static final String USA_ZIP_CODE_PATTERN = "(^\\d{5}$)|(^\\d{5}-\\d{4}$)";
    public static final String RANGE_1_99_PATTERN = "[1-99]";
    public static final String INTEGER_POSIIVE_PATTERN = "^[1-9]\\d*$";
    public static final int FULL_NAME_LENGTH = 100;
    public static final int COMPANY_NAME_LENGTH = 100;
    public static final int EMAIL_LENGHT = 62;
    public static final int ADDRESS_LENGTH = 95;
    public static final int CITY_LENGHT = 35;


}
