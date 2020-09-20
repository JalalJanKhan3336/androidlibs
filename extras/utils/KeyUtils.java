package com.thesoftparrot.delivery.tcsapp.extra.utils;

public class KeyUtils {

    private KeyUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot be instantiated");
    }

    // Keys
    public static final String KEY_PHONE = "phone";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_CUSTOMER = "customer";
    public static final String KEY_STAFF = "staff";

    // Messages
    public static final String UPDATE_SUCCESS_MSG = "Updated Successfully!";
    public static final String PHONE_VERIFICATION_MSG = "Please wait... while your phone number is being verified";

    // Collections
    public static final String COLLECTION_CUSTOMER_PROFILE = "Customer Profile";
    public static final String COLLECTION_STAFF_PROFILE = "Staff Profile";

}
