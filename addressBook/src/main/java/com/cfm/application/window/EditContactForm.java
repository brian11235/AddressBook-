package com.cfm.application.window;

import org.apache.wicket.AttributeModifier;
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
 * This form use for edit window, edit the existed window
 * @author linbrian
 *
 */
public class EditContactForm extends Form{
	
	
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
	
	public EditContactForm(final PageReference modalWindowPage,final ModalWindow window,String id) {
		super(id);
		this.modalWindowPage = modalWindowPage;
		this.window = window;
		setDefaultModel(new CompoundPropertyModel(this));
		Contact contact = (Contact) ((Model) window.getDefaultModel().getObject()).getObject();
		add(new TextField("firstName").add(new AttributeModifier("value", contact.getFirstName())));
        
        add(new TextField("lastName").add(new AttributeModifier("value", contact.getLastName())));
        
        add(new EmailTextField("email").add(new AttributeModifier("value", contact.getEmail()))); 
        
        add(new TextField("company").add(new AttributeModifier("value", contact.getCompany())));

        add(new TextField("phone").add(new PhoneValidator()).add(new AttributeModifier("value", contact.getPhone())));
       
        add(new TextField("country").add(new AttributeModifier("value", contact.getCountry())));

        add(new TextField("address").add(new AttributeModifier("value", contact.getAddress())));
        
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
				Contact contact = (Contact) ((Model) window.getDefaultModel().getObject()).getObject();
				c.setId(contact.getId());
				window.getSession().setAttribute("model", c);
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
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
