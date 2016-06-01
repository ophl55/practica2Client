package t3.sos.fi.upm.es.client;

import java.rmi.RemoteException;

import t3.sos.fi.upm.es.client.UserManagementWSStub.*;

public class UserManagementWSClient {
	public static void main(String args[]) throws RemoteException{
		Response response;
		
		UserManagementWSStub stub = new UserManagementWSStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
;
		
		User user = new User();
		user.setName("admin");
		user.setPwd("admin");

		stub._getServiceClient().getServiceContext().setProperty("USER", user);
		
		response = stub.login(user);
		
		System.out.println("Result login: " + response.getResponse());
		System.out.println(stub._getServiceClient().getServiceContext().getGroupName());
		

		response = stub.login(user);
		System.out.println("Result login: " + response.getResponse());
		System.out.println(stub._getServiceClient().getServiceContext().getGroupName());
		
	}
}
