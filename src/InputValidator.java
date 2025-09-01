// InputValidator.java - Input validation utility
import java.util.regex.Pattern;

class InputValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[a-zA-Z\\s]{2,50}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidAge(int age) {
        return age > 0 && age < 150;
    }

    public static boolean isValidCourse(String course) {
        return course != null && course.trim().length() >= 2 && course.trim().length() <= 100;
    }
}
