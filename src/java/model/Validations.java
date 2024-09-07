package model;

public class Validations {

    public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }

    public static boolean isDouble(String text) {
        if (text == null) {
            return false;
        }
        return text.matches("^\\d+(\\.\\d{2})?$");
    }

    public static boolean isInteger(String text) {
        if (text == null) {
            return false;
        }
        return text.matches("^\\d+$");
    }

}
