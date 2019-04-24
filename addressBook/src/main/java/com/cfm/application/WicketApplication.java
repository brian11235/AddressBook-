package com.cfm.application;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.cfm.application.auth.BasicAuthenticationSession;
/**
 * 
 * start application
 * @author linbrian
 *
 */
public class WicketApplication extends AuthenticatedWebApplication{
	@Override
	public Class<? extends Page> getHomePage() {
		return LoginPage.class;
	}
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getResourceSettings().setResourceStreamLocator(new Locator());
	}
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}
	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return BasicAuthenticationSession.class;
	}

}
