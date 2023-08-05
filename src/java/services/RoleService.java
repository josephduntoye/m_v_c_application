/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import models.Role;

/**
 *
 * @author king_wizard
 */
public class RoleService {
    public Role getRole(int roleId) throws Exception {
        RoleDB roleDB = new RoleDB();
        return roleDB.getByRole(roleId);
    }
}
