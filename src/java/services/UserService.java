/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author king_wizard
 */
public class UserService {
    public User get(String email) throws Exception {
        // creating a note database class
        // creating an instance of notedb class
        UserDB userDB = new UserDB();
        // using the get method from the notedb class
        User user = userDB.get(email);
        return user;
    }

    // get all notes
    public List<User> getAll() throws Exception {
        // creating an instance of notedb class
        UserDB userDB = new UserDB();
        // using the getAll method from the notedb class
        List<User> users = userDB.getAll();
        return users;
    }

    public void insert(String email, String firstName, String lastName, String password, int role) throws Exception {
        UserDB userDB = new UserDB();
        RoleService roleSer = new RoleService();
        Role roleLoc = roleSer.getRole(role);
        User user = new User(email, 1, firstName, lastName, password, roleLoc);
        userDB.insert(user);
    }

    public void update(String email, String firstName, String lastName, String password, int role) throws Exception {
        UserDB userDB = new UserDB();
        RoleService roleSer = new RoleService();
        Role roleLoc = roleSer.getRole(role);
        User user = new User(email, 1, firstName, lastName, password, roleLoc);
        userDB.update(user);
    }

    public void delete(String email) throws Exception {
        // getting a note and then sending a note to the notedb and 
        // using the delete method from the notedb class to delete
        
        // creating an instance with the javabean
        User user = new User();
        // calling the setter for the id from said javabean
        user.setEmail(email);
        // creating an instance of the notedb class
        UserDB userDB = new UserDB();
        // using the delete method from the notedb class
        userDB.delete(user);
    }


}
