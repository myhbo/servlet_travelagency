package controller.dto;

import model.entity.enums.Roles;

import java.util.Set;

public class UserDTO {
    private long id;
    private String email;
    private String password;
    private String fullName;
    private Set<Roles> rolesSet;
}
