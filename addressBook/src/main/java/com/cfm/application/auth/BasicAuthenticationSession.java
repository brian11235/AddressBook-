package com.cfm.application.auth;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.cfm.entity.User;
/**
 * 
 * Login authority 
 * @author linbrian
 *
 */
public class BasicAuthenticationSession extends AuthenticatedWebSession{
	
	
	private static final long serialVersionUID = 1L;

	public BasicAuthenticationSession(Request request) {
		super(request);
	}

	@Override
	protected boolean authenticate(String username, String password) {
		User user = (User)this.getAttribute("info");
		if(user != null && username.equals(user.getId()) && password.equals(user.getPassword())) return true;
		return false;
	}

	@Override
	public Roles getRoles() {
		return null;
	}

}
