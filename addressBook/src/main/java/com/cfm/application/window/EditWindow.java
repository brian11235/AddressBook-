package com.cfm.application.window;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.cfm.entity.Contact;
import com.cfm.entity.User;
import com.cfm.service.AddressBookService;

/**
 * 
 * After the edit button, this window opens
 * @author linbrian
 *
 */
public class EditWindow extends WebPage{
	
	public EditWindow(final PageReference modalWindowPage,final ModalWindow window) {
		
		//set window size 
		window.setTitle("Edit");
		window.setInitialHeight(500);
		window.setInitialWidth(850);
		add(new EditContactForm(modalWindowPage,window,"editForm"));
    }	
	
}
