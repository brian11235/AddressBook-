package com.cfm.application.window;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.cfm.application.MainPage;
import com.cfm.entity.User;
import com.cfm.service.AddressBookService;
/**
 * 
 * After push delete button, this window open
 * @author linbrian
 *
 */
public class WarningWindow extends WebPage{
	
//	@SpringBean
//	private AddressBookService addressBookService;
	
	public WarningWindow(final PageReference modalWindowPage,final ModalWindow window) {

		//set window size 
		window.setTitle("Warnning");
		window.setInitialHeight(200);
		window.setInitialWidth(350);
		
        // Retrieve the passValue content for display.
        final String passValue = ((MainPage) modalWindowPage.getPage()).getPassValue();
        // get window status 
//        final boolean isSubmit = ((MainPage) modalWindowPage.getPage()).isSubmit();
        add(new Label("passValue", "Are you sure to delete ?"));
        
        AjaxLink yesButton = new AjaxLink("yesButton") {
        	public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				((MainPage) modalWindowPage.getPage()).setPassValue("Yes");
				window.close(target);
			}
        };

        AjaxLink noButton = new AjaxLink("noButton") {
        	public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				((MainPage) modalWindowPage.getPage()).setPassValue("No");
				window.close(target);
			}
        };
        add(yesButton);
        add(noButton);
    }
}
