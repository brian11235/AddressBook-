package com.cfm.application;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cfm.dao.AddressBookDao;
import com.cfm.dao.AddressBookDaoImpl;
import com.cfm.entity.User;
import com.cfm.service.AddressBookService;
/**
 * 
 * Login page 
 * @author linbrian
 *
 */
public class LoginPage extends WebPage{
	
	@SpringBean
	private AddressBookService addressBookService;
	
	
	public LoginPage(final PageParameters parameters) {
        super(parameters);
        add(new LoginForm("loginForm"));
        add(new Link("createUserPage"){
			public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}

			@Override
			public void onClick() {
				setResponsePage(CreateUserPage.class);
			}
        });
    }
}
