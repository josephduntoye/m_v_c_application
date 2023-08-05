<%-- 
    Document   : users
    Created on : 21-Jul-2023, 7:38:53 AM
    Author     : king_wizard
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Manage Users</h2>
        <c:if test="${message eq 'create'}"><h3>User ${actUser} Created</h3></c:if>
        <c:if test="${message eq 'edit'}"><h3>User ${actUser} Updated</h3></c:if>
        <c:if test="${message eq 'editing'}"><h3>Editing user ${selectedUser.email}</h3></c:if>
        <c:if test="${message eq 'delete'}"><h3>User ${actUser} Deleted</h3></c:if>
        <c:if test="${message eq 'error'}"><h3>~Something went wrong~</h3></c:if>
        <c:if test="${message eq 'errorDup'}"><h3>~That user already exists~</h3></c:if>
        <c:if test="${message eq 'errorInval'}"><h3>~Invalid data entered~</h3></c:if>
        <c:if test="${message eq 'errorInvalEmail'}"><h3>~Invalid data entered for Email~</h3></c:if>
        <c:if test="${message eq 'errorInvalFN'}"><h3>~Invalid data entered for First Name~</h3></c:if>
        <c:if test="${message eq 'errorInvalLN'}"><h3>~Invalid data entered for Last Name~</h3></c:if>
        <c:if test="${message eq 'errorInvalPW'}"><h3>~Invalid data entered for Password~</h3></c:if>

        <c:if test="${users.isEmpty()}">
            <h3>No users found. Please add a user.</h3>
        </c:if>
        <c:if test="${!users.isEmpty()}">
            <table border="1">
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${users}" var="users">
                    <c:url value="/users" var="urlE">
                        <c:param name="action" value="edit"></c:param>
                        <c:param name="email" value="${users.email}"></c:param>
                    </c:url>
                    <c:url value="/users" var="urlD">
                        <c:param name="action" value="delete"></c:param>
                        <c:param name="email" value="${users.email}"></c:param>
                    </c:url>
                    <tr>
                        <td>${users.email}</td>
                        <td>${users.firstName}</td>
                        <td>${users.lastName}</td>
                        <td>${users.role.name}</td>
                        <td><a href="${urlE}">Edit</a></td>
                        <td><a href="${urlD}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>


        <c:if test="${selectedUser eq null}">
            <h2>Create New User</h2>
            <form action="users" method="post">
                <label for="email">Email</label>
                <input type="email" name="email" id="email" /> <br>
                <label for="userFN">First Name</label>
                <input type="text" name="userFN" id="userFN" /> <br>
                <label for="userLN">Last Name</label>
                <input type="text" name="userLN" id="userLN" /> <br>
                <label for="userPassword">Password</label>
                <input type="password" name="userPassword" id="userPassword" /> <br>
                <label for="userRole">Role</label>
                <select name="userRole" id="userRole">
                    <option value="1">System Admin</option>
                    <option value="2">Regular User</option>
                </select> <br>
                <input type="hidden" name="action" value="create">
                <input type="submit" value="Create">
            </form>
        </c:if>

        <c:if test="${selectedUser ne null}">
            <h2>Edit User</h2>
            <form action="users" method="post">
                <p>Email: ${selectedUser.email}</p>
                <label for="userFN">First Name</label>
                <input type="text" name="userFN" id="userFN" value="${selectedUser.firstName}"> <br>
                <label for="userLN">Last Name</label>
                <input type="text" name="userLN" id="userLN" value="${selectedUser.lastName}"> <br>
                <label for="userPassword">Password</label>
                <input type="password" name="userPassword" id="userPassword" value="${selectedUser.password}" /> <br>
                <label for="userRole">Role</label>
                <select name="userRole" id="userRole">
                    <c:choose>
                        <c:when test="${selectedUser.role.name eq 'regular user'}">
                            <option value="1">System Admin</option>
                            <option value="2" selected="selected">Regular User</option>
                        </c:when>
                        <c:when test="${selectedUser.role.name eq 'system admin'}">
                            <option value="1" selected="selected">System Admin</option>
                            <option value="2">Regular User</option>
                        </c:when>
                    </c:choose>
                </select> <br>
                <input type="hidden" name="email" value="${selectedUser.email}">
                <input type="hidden" name="action" value="edit">
                <input type="submit" name="submitButton" value="Submit Edit">
                <input type="button" name="resetButton" value="Cancel Edit" onclick="location.href = 'users'">
            </form>
        </c:if>
    </body>
</html>
