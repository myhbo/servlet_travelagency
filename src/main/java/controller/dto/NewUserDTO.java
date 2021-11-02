package controller.dto;

public class NewUserDTO {
    private String email;
    private  String password;
    private  String fullName;

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String email;
        private String password;
        private String fullName;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public NewUserDTO build() {
            NewUserDTO newUserDTO = new NewUserDTO();
            newUserDTO.setEmail(email);
            newUserDTO.setPassword(password);
            newUserDTO.setFullName(fullName);
            return newUserDTO;
        }
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
