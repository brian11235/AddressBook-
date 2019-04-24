package com.cfm.application;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.cfm.entity.User;
import com.cfm.service.AddressBookService;
/**
 * 
 * Auth implements in login page
 * @author linbrian
 *
 */
public class LoginForm extends Form{
	
	@SpringBean
    private AddressBookService addressBookService;
	
	private String username;
    private String password;
    private final FeedbackPanel fp;
    private String loginStatus;
    
    public LoginForm(String id) {        
        super(id);
        setDefaultModel(new CompoundPropertyModel(this));
        fp = new FeedbackPanel("feedbackPanel");
        add(fp);
        add(new Label("loginStatus"));
        add(new TextField("username"));
        add(new PasswordTextField("password"));
    }
    public final void onSubmit() {  
    	// 1. get data from redis 
    	User user = addressBookService.getUserData(username);
    	// 2. check username & password
//    	if(!password.equals(user.getPassword())){
//    		
//    	}
    	AuthenticatedWebSession.get().setAttribute("info", user);
    	boolean authResult = AuthenticatedWebSession.get().signIn(username, password);
    	if(authResult){
    		PageParameters p = new PageParameters(); 
        	p.add("userid", user.getId());
    		setResponsePage(new MainPage(p));
    	}else loginStatus = "Error ! Please check username or password !";
    	// 3. another page
//    	PageParameters p = new PageParameters(); 
//    	p.add("userid", user.getId());
//    	setResponsePage(new MainPage(p));
    }
}
