/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author king_wizard
 */
public class UserDB {

    // based on the email, where the email is the 'owner'
    public List<User> getAll() throws Exception {
        // creating an empty array list for notes 
        List<User> users = new ArrayList<>();
        // getting a connection
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        // initializing or declaring PreparedStatement and ResultSet
        PreparedStatement ps = null;
        ResultSet rs = null;
        // query
        String sql = "SELECT * FROM user";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);
                int active = rs.getInt(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                //
                int role = rs.getInt(6);
                // call getRolebyID
                RoleDB rDB = new RoleDB();
                Role r = rDB.getByRole(role);
                User user = new User(email, active, firstName, lastName, password, r);
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }

    // get one note
    // based on a condition; the ip
    public User get(String email) throws Exception {
        //returns one note, so we initialize for one note
        //initializing one empty note java bean
        User user = null;
        // get connection, like getAll method above
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            // it is just one note
            if (rs.next()) {
                int active = rs.getInt(2);
                String firstName = rs.getNString(3);
                String lastName = rs.getNString(4);
                String password = rs.getNString(5);
                //
                int role = rs.getInt(6);
                // call getRolebyID
                RoleDB rDB = new RoleDB();
                Role r = rDB.getByRole(role);
                user = new User(email, active, firstName, lastName, password, r);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return user;
    }

    
    // insert a user to database
    public void insert(User user) throws Exception {
        // get connection
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email, active, firstName, lastName, password) VALUES (?, 1, ?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleId());
            ps.executeUpdate();
        } finally {
            // only closing the prepared statement b/c we don't use the result set
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
     

    public void update(User user) throws Exception {
        // get connection again
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();

        PreparedStatement ps = null;
        String sql = "UPDATE user SET firstName=?, lastName=?, password=?, role=? WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleId());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
