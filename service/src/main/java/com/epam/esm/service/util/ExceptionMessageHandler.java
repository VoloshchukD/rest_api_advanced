package com.epam.esm.service.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionMessageHandler {

    public static final String CERTIFICATE_CODE = "01";

    public static final String TAG_CODE = "02";

    public static final String CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME = "message.certificate-id-empty";

    public static final String TAG_ID_NOT_PRESENT_MESSAGE_NAME = "message.tag-id-empty";

    public static final String CERTIFICATE_NOT_FOUND_MESSAGE_NAME = "message.certificate-not-found";

    public static final String TAG_NOT_FOUND_MESSAGE_NAME = "message.tag-not-found";

    public static final String SOURCE_NAME = "local";

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle(SOURCE_NAME, locale).getString(messageKey);
    }

}
