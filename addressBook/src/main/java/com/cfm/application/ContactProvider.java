package com.cfm.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.cfm.entity.Contact;
/**
 * 
 * Used for rendering data in maintable and also filter for table
 * @author linbrian
 *
 */
public class ContactProvider extends SortableDataProvider implements IFilterStateLocator<Contact>{
	
	private List<Contact> contactList;
	private String condition;

	public ContactProvider(List<Contact> contactList){
		this.contactList = contactList;
		setSort("firstName", SortOrder.ASCENDING);
	}

	public Iterator iterator(long first, long count) {
		Iterator<Contact> contactIterator = null;
		//filter 
		if(condition != null && !condition.isEmpty()){
			List<Contact> newContactList = new ArrayList<>();
			for(Contact c : contactList){
				if(c.getFirstName().toLowerCase().startsWith((condition.toLowerCase()))){
					newContactList.add(c);
				}
			}
			setContactList(newContactList);
			contactIterator = newContactList.iterator();
		}else{
			setContactList(contactList);
			contactIterator = contactList.iterator();
		}
		
		return contactIterator;
	}

	public long size() {
		return contactList.size();
	}

	public IModel model(Object object) {
		Contact c = (Contact)object;
		return new Model(c);
	}

	@Override
	public Contact getFilterState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilterState(Contact state) {
		// TODO Auto-generated method stub
		
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	
	

}
