package t3.sos.fi.upm.es.client;

import java.rmi.RemoteException;

import t3.sos.fi.upm.es.client.UserManagementWSStub.*;

public class UserManagementWSClient {
	public static void main(String args[]) throws RemoteException{
		UserManagementWSStub stub = new UserManagementWSStub();
		User user = new User();
		user.setName("admin");
		user.setPwd("admin");
		Response response = stub.login(user);
		System.out.println("Result login: " + response.getResponse());
	}
}
