package in.reqres.models.request;

public class RegisterUserRequest {
    private String email;
    private String password;

    public RegisterUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
