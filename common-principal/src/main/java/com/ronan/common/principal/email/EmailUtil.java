package com.ronan.common.principal.email;

/**
 * @author L.J.Ran
 */
public class EmailUtil {

    private EmailUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isEmail(String email) {
        return EmailPrincipal.EMAIL_PATTERN.matcher(email).matches();
    }
}
