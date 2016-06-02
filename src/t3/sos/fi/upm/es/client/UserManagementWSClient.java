package t3.sos.fi.upm.es.client;

import java.rmi.RemoteException;

import t3.sos.fi.upm.es.client.UserManagementWSStub.PasswordPair;
import t3.sos.fi.upm.es.client.UserManagementWSStub.Response;
import t3.sos.fi.upm.es.client.UserManagementWSStub.User;
import t3.sos.fi.upm.es.client.UserManagementWSStub.Username;

public class UserManagementWSClient {
	public static void main(String args[]) throws RemoteException {
		Response response;
		UserManagementWSStub stub = new UserManagementWSStub();

		// ----------------------------------------------------------//
		// CREATE A 1ST SESSION //
		// ----------------------------------------------------------//
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		// two times login
		User user = new User();
		user.setName("admin");
		user.setPwd("admin");

		response = stub.login(user);
		System.out.println("Login as superuser: " + response.getResponse());

		response = stub.login(user);
		System.out.println("Login a 2nd time as superuser: "
				+ response.getResponse());
		System.out.println();

		// superuser adds user
		User userToAdd = new User();
		userToAdd.setName("testuser");
		userToAdd.setPwd("testuser");

		response = stub.addUser(userToAdd);
		System.out.println("Adding a non existing user (as superuser): "
				+ response.getResponse());
		response = stub.addUser(userToAdd);
		System.out.println("Adding an already existing user (as superuser): "
				+ response.getResponse());
		System.out.println();

		// superuser changes password
		PasswordPair pwPair = new PasswordPair();
		pwPair.setOldpwd("admin");
		pwPair.setNewpwd("newpw");
		response = stub.changePassword(pwPair);
		System.out.println("Superuser changing his password "
				+ pwPair.getOldpwd() + " to " + pwPair.getNewpwd() + ": "
				+ response.getResponse());
		System.out.println();

		// superuser removes non existing user
		Username username = new Username();
		username.setUsername("nonexistingUser");
		response = stub.removeUser(username);
		System.out
				.println("Superuser trying to remove user that doesn't exist: "
						+ response.getResponse());

		// superuser removes existing user "testuser" (added before)
		username = new Username();
		username.setUsername("testuser");

		response = stub.removeUser(username);
		System.out.println("Superuser trying to remove user that exists: "
				+ response.getResponse());
		response = stub.addUser(userToAdd);
		System.out.println("Adding a non existing user again (as superuser): "
				+ response.getResponse());
		System.out.println();

		// logout superuser
		stub.logout();

		// ----------------------------------------------------------//
		// CREATE A 2ND SESSION //
		// ----------------------------------------------------------//
		stub = new UserManagementWSStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		// Login of non registered user
		User user1 = new User();
		user1.setName("user1");
		user1.setPwd("user1");
		response = stub.login(user1);
		System.out.println("Login of not registered User: " + response.getResponse());
		System.out.println();
		
		// ----------------------------------------------------------//
		// CREATE A 3RD SESSION //
		// ----------------------------------------------------------//
		stub = new UserManagementWSStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		// not logged in user tries to change his password
		pwPair = new PasswordPair();
		pwPair.setOldpwd("testuser");
		pwPair.setNewpwd("testuserNewpw");
		response = stub.changePassword(pwPair);
		System.out.println("Not logged in user trying to change his password: "
				+ response.getResponse());
		
		// Testuser logs in
		response = stub.login(userToAdd);
		System.out.println("User \"testuser\" trys to login: " + response.getResponse());

		// user changes password (with incorrect oldPassword)
		pwPair = new PasswordPair();
		pwPair.setOldpwd("wrongOldPassword");
		pwPair.setNewpwd("pw");
		response = stub.changePassword(pwPair);
		System.out
				.println("User trying to change his password (with wrong Oldpwd): "
						+ response.getResponse());

		// normal user calls addUser/removeUser
		response = stub.addUser(user);
		System.out.println("Adding new user without permission: "
				+ response.getResponse());
		response = stub.removeUser(username);
		System.out.println("Removing user without permission: "
				+ response.getResponse());
		System.out.println();

		// user logs out
		stub.logout();
	}
}
