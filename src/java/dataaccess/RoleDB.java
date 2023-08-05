/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Role;

/**
 *
 * @author king_wizard
 */
public class RoleDB {

    public Role getByRole(int roleId) throws Exception {
        Role role = null;
        // get connection, like getAll method above
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM role WHERE role_id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            // it is just one note
            if (rs.next()) {
                int rID = rs.getInt(1);
                String roleName = rs.getString(2);
                role = new Role(rID, roleName);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return role;
    }
    //getBy iD selst * from Role where id =  id
    // create an object of tjat role
    //  return role
}
