package com.cfm.application;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
/**
 * create user page
 * @author linbrian
 *
 */
public class CreateUserPage extends WebPage{
	
	public CreateUserPage() {
        add(new CreateForm("CreateForm"));
    }
}
