package com.cfm.application;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.cfm.entity.Contact;
import com.cfm.entity.User;
import com.cfm.service.AddressBookService;
import com.cfm.validator.PhoneValidator;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;
/**
 * 
 * Used for create user 
 * @author linbrian
 *
 */
public class CreateForm extends Form{
	
	@SpringBean
	private AddressBookService addressBookService;
	
	private String userId;
	private String password;
	private String repeatPassword;
	private String phone;
	private String address;
	private String email;
	
	private final RequiredTextField tf;
	private final FeedbackPanel fp;
	private final PasswordTextField p1;
	private final PasswordTextField p2;
	private AjaxLink button;
	private String createStatus;
//	private ModalWindow dialog;
//	private MessageDialog dialog;
	
	public CreateForm(String form){
		super(form);
		setDefaultModel(new CompoundPropertyModel(this));
		add(new Label("createStatus"));
		tf = new RequiredTextField("userId");
		add(tf);
		p1 = new PasswordTextField("password");
        p2 = new PasswordTextField("repeatPassword");
        add(p1,p2);
        add(new EqualPasswordInputValidator(p1,p2));
        add(new TextField("phone").add(new PhoneValidator()));
        add(new TextField("address"));
        add(new EmailTextField("email"));
        fp = new FeedbackPanel("feedbackPanel");
        add(fp);
        button = new AjaxLink("backButton"){
    		public MarkupContainer setDefaultModel(IModel model) {
    			return null;
    		}
    		@Override
    		public void onClick(AjaxRequestTarget target) {
    			setResponsePage(LoginPage.class);
    		}
    	};
    	
    	add(button);
	}
	public final void onSubmit() {    
		User newUser = new User(userId,password,phone,address,email);
		User user = addressBookService.getUserData(newUser.getId());
		if(user == null){
			try{
				addressBookService.saveUserData(newUser);
				setResponsePage(LoginPage.class);
			}
			catch(Exception e){
				createStatus = "login error !";
			}
		}
		else{
			createStatus = "This username is been used!";
		}
	}
	
}
