package model.entity;

import model.entity.enums.Roles;

import java.util.*;

public class User {
    private long id;
    private String email;
    private String password;
    private String fullName;
    private boolean enabled;
    private Roles role;
    private List<Order> orders = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private long id;
        private String email;
        private String password;
        private String fullName;
        private boolean enabled;
        private Roles role;

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

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder role(Roles role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setEnabled(enabled);
            user.setRole(role);
            return user;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && enabled == user.enabled && email.equals(user.email) && password.equals(user.password) && fullName.equals(user.fullName) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, fullName, enabled, role);
    }

    @Override
    public String toString() {
        return email;
    }
}
