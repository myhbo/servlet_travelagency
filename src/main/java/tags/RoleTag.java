package tags;

import model.entity.User;
import model.entity.enums.Roles;

import javax.servlet.jsp.tagext.TagSupport;


public class RoleTag extends TagSupport {
    private String role = "";

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        User user = (User) pageContext.getSession().getAttribute("user");
        if (user != null && (user.getRole().contains(Roles.ADMIN) || user.getRole().contains(Roles.valueOf(role))))
            return EVAL_BODY_INCLUDE;
        else
            return SKIP_BODY;
    }
}

