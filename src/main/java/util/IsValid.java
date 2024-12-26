package util;

public class IsValid {

    public static boolean validateEmail(String email) {
        final String CORRECT_EMAIL = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]+$";
        return validate(email, 5, 100, CORRECT_EMAIL);
    }

    public static boolean validatePassword(String password) {
        final String CORRECT_PASSWORD = "^[a-zA-Z0-9!\"#$%&'()*+,-./:;<=>?@\\\\^_`{|}~]+$";
        return validate(password, 6, 64, CORRECT_PASSWORD);
    }

    public static boolean validateUserName(String login) {
        final String CORRECT_LOGIN = "^[a-zA-Z0-9_.-]+$";
        return validate(login, 5, 60, CORRECT_LOGIN);
    }

    private static boolean validate(String s, int minLength, int maxLength, String symbols) {
        return s != null && s.length() >= minLength && s.length() <= maxLength && s.matches(symbols);
    }
}
