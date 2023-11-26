public class Author {
    private String name;
    private String email;
    private char gender;

    public Author(String name, String email, char gender) {
        if (isValidGender(gender) && isValidEmail(email)) {
            this.name = name;
            this.email = email;
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Проверьте корректность введеного email и пола");
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Некорректный email");
        }
    }

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return name + " (" + gender + ") " + email;
    }

    private boolean isValidEmail(String email) {
        return email.matches("\\S+@\\S+\\.\\S+");
    }

    private boolean isValidGender(char gender) {
        return gender == 'ж' || gender == 'м';
    }
}
