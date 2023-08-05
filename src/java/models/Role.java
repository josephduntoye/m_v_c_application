package models;

/**
 *
 * @author king_wizard
 */
public class Role {
    private int roleId;
    private String name;

    public Role() {
    }

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.name = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }
    
    
}
