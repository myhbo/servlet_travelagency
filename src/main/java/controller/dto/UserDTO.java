package controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import model.entity.enums.Roles;

import java.util.Set;

public class UserDTO {
    private long id;
    @NotBlank
    @Email(message = "{validation.user.email.invalid}")
    private String email;
    @NotBlank
    @Size(min = 5, message = "{validation.user.password}")
    private String password;
    @NotBlank
    @Size(max = 50, message = "{validation.user.full.name}")
    private String fullName;
    private Set<Roles> roles;


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String email;
        private String password;
        private String fullName;
        private Set<Roles> roles;

        public Builder id(long id) {
            this.id = id;
            return this;
        }
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
        public Builder roles(Set<Roles> roles) {
            this.roles = roles;
            return this;
        }



        public UserDTO build() {
            UserDTO UserDTO = new UserDTO();
            UserDTO.setId(id);
            UserDTO.setEmail(email);
            UserDTO.setPassword(password);
            UserDTO.setFullName(fullName);
            UserDTO.setRole(roles);
            return UserDTO;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Roles> getRole() {
        return roles;
    }

    public void setRole(Set<Roles> roles) {
        this.roles = roles;
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

