package com.cfm.application.component;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.cfm.entity.Contact;
/**
 * 
 * The panel used in table
 * @author linbrian
 *
 */
public class ActionPanel extends Panel
{
	private Item<ICellPopulator<Contact>> cellItem;
	private String id;
	private IModel<Contact> model;
	private int idx;
	
    public ActionPanel(final Item<ICellPopulator<Contact>> cellItem,String id, 
    		final IModel<Contact> model,final ModalWindow addWindow,final ModalWindow editWindow,final ModalWindow warndialog){
    	super(id, model);
    	this.cellItem = cellItem;
    	this.id = id;
    	this.model = model;
    	
    	add(new AjaxLink("editButton"){
			public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				Item rowItem = cellItem.findParent(Item.class);
//				editWindow.getSession().setAttribute("model", model.getObject());
				editWindow.setDefaultModelObject(model);
				editWindow.show(target);
				
			}
		});
    	add(new AjaxLink("deleteButton"){
			public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				Item rowItem = cellItem.findParent(Item.class);
				warndialog.getSession().setAttribute("model", model.getObject());
//				setModel(model);
				warndialog.show(target);
			}
		});
    }

	public Item<ICellPopulator<Contact>> getCellItem() {
		return cellItem;
	}

	public void setCellItem(Item<ICellPopulator<Contact>> cellItem) {
		this.cellItem = cellItem;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IModel<Contact> getModel() {
		return model;
	}

	public void setModel(IModel<Contact> model) {
		this.model = model;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
    
}
