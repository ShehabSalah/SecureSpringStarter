package com.shehabsalah.securespringstarter.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * Utility class for validating mobile numbers and email addresses.
 *
 * <p>
 * This class provides methods for validating mobile numbers and email addresses based on specific rules and patterns.
 *
 * <p>
 * It uses the Google's libphonenumber library for mobile number validation.
 *
 * <p>
 * The class also includes a method to check if a mobile number is a fake number.
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Slf4j
public class ValidateUtil {

    /**
     * Checks the validity of a mobile number.
     * This method verifies if the given mobile number is valid and not a fake number.
     *
     * @param str The mobile number to be validated.
     * @return true if the mobile number is valid, false otherwise.
     * @throws RuntimeException if the mobile number is missing or doesn't have a country key.
     */
    public boolean checkMobileNumberValidation(String str) throws RuntimeException {
        boolean isValid = false;

        if(str == null || str.isEmpty())
            throw new RuntimeException("The mobile is missing.");

        //Check the country code
        if(!(str.trim().startsWith("00") || str.trim().startsWith("+"))) {
            log.warn("The mobile: " + str + " is missing the country key.");
            throw new RuntimeException("The mobile: " + str + " is missing the country key.");
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(str, "EG");
            isValid = phoneUtil.isValidNumber(numberProto) && phoneUtil.getNumberType(numberProto) != PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
        } catch (NumberParseException e) {
            log.warn("NumberParseException was thrown: " + e.toString());
        }
        return isValid;
    }

    /**
     * Checks the validity of an email address.
     * This method verifies if the given email address meets the required criteria.
     *
     * @param str The email address to be validated.
     * @return true if the email address is valid, false otherwise.
     */
    public boolean checkEmailValidation(String str) {
        if (!(str == null || str.trim().isEmpty() || str.trim().contains("\u0000") || str.trim().length() > 255)) {
            Pattern email = Pattern.compile(
                    "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
            return email.matcher(str).matches();
        }
        return false;
    }
}
