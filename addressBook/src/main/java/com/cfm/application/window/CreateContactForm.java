package com.cfm.application.window;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.cfm.application.MainPage;
import com.cfm.entity.Contact;
import com.cfm.entity.User;
import com.cfm.validator.PhoneValidator;

/**
 * 
 * This form used in add window, add a new contact in data table
 * @author linbrian
 *
 */
public class CreateContactForm extends Form{
	
	
	private String firstName;
	private String lastName;
	private String email;
	private String company;
	private String phone;
	private String country;
	private String address;
	final PageReference modalWindowPage;
	final ModalWindow window;
	private FeedbackPanel feedbackPanel;
	
	public CreateContactForm(final PageReference modalWindowPage,final ModalWindow window,String id) {
		super(id);
		this.modalWindowPage = modalWindowPage;
		this.window = window;
		setDefaultModel(new CompoundPropertyModel(this));
		add(new TextField("firstName"));
        
        add(new TextField("lastName"));
        
        add(new EmailTextField("email")); 
        
        add(new TextField("company"));

        add(new TextField("phone").add(new PhoneValidator()));
       
        add(new TextField("country"));

        add(new TextField("address"));
        
        feedbackPanel = new FeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupId(true);
        
        add(feedbackPanel);
        
        add(new AjaxButton("yesButton",this) {
        	public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onSubmit(AjaxRequestTarget target) {
				((MainPage) modalWindowPage.getPage()).setPassValue("Yes");
				Contact c = new Contact(firstName,lastName,email,company,phone,country,address);
				((MainPage) modalWindowPage.getPage()).setContact(c);
				window.close(target);
			}
			@Override
	        protected void onError(final AjaxRequestTarget target) {
	             /*here add your feedback panel to be updated via ajax*/
	             target.add(feedbackPanel);
	        }
        });

		add(new AjaxButton("noButton",this) {
        	public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onSubmit(AjaxRequestTarget target) {
				Contact c = (Contact)this.getDefaultModelObject();
				((MainPage) modalWindowPage.getPage()).setPassValue("No");
				window.close(target);
			}
			@Override
	        protected void onError(final AjaxRequestTarget target) {
	            /*here add your feedback panel to be updated via ajax*/
				window.close(target);
	        }
        });
	}
//	public final void onSubmit() {    
//		((MainPage) modalWindowPage.getPage()).setPassValue("Yes");
//		Contact c = new Contact();
//		((MainPage) modalWindowPage.getPage()).setContact(c);
//		window.close(target);
//	}
}
