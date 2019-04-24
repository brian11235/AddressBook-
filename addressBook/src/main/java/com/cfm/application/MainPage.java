package com.cfm.application;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.CSVDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.RequiredEditableTextFieldColumn;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import com.cfm.application.component.ActionPanel;
import com.cfm.application.csv.OpenCSVWriter;
import com.cfm.application.window.AddWindow;
import com.cfm.application.window.EditWindow;
import com.cfm.application.window.WarningWindow;
import com.cfm.entity.Contact;
import com.cfm.entity.User;
import com.cfm.service.AddressBookService;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
/**
 * 
 * A Table that can support CRUD for detail message
 * Only login user can check this page
 * @author linbrian
 *
 */
public class MainPage extends WebPage{
	
	@SpringBean
	private AddressBookService addressBookService;
	
	// value for knowing the operation of closing window(yes or no)
	private String passValue;
	private User user;	
	private Contact contact;
	
	private AjaxLink AddButton;
	private AjaxLink logoutButton;
	private AjaxLink csvButton;
	
	private DataTable grid;
	private FilterForm<Contact> filterForm;
	private ActionPanel actionPanel;
	
	//three window
	private ModalWindow editWindow;
	private ModalWindow warndialog;
	private ModalWindow addWindow;
	
	

	MainPage(PageParameters p){
		final User user = addressBookService.getUserData(p.get("userid").toString());
		ContactProvider contactProvider = new ContactProvider(user.getContactList());
		List<IColumn<Contact,String>> columns = new ArrayList<IColumn<Contact,String>>();
		columns.add(new AbstractColumn<Contact, String>(new Model("Actions"))
        {
			public void populateItem(Item<ICellPopulator<Contact>> cellItem, String componentId, IModel<Contact> rowModel) {
				actionPanel = new ActionPanel(cellItem,componentId, rowModel, addWindow , editWindow ,warndialog);
				cellItem.add(actionPanel);
			}
        });
		columns.add(new PropertyColumn<Contact,String>(Model.of("First Name"), "firstName", "firstName"));
		columns.add(new PropertyColumn<Contact,String>(Model.of("Last Name"), "lastName","lastName"));
		columns.add(new PropertyColumn<Contact,String>(Model.of("Email"), "email","email"));
		columns.add(new PropertyColumn<Contact,String>(Model.of("Company"), "company"));
		columns.add(new PropertyColumn<Contact,String>(Model.of("Phone"), "phone"));
		columns.add(new PropertyColumn<Contact,String>(Model.of("Country"), "country", "country"));
		columns.add(new PropertyColumn<Contact,String>(Model.of("Address"), "address"));
		
		grid = new DefaultDataTable("grid", columns, contactProvider, 100);
		grid.setOutputMarkupId(true);
		TextField t = new TextField<>("firstName", Model.of(""));
		// for filter 
		filterForm = new FilterForm("filterForm", contactProvider){
			@Override
			public void onSubmit(){
				//reset the dataProvider
				contactProvider.setContactList(user.getContactList());
				contactProvider.setCondition(t.getValue());
			}
		};
        filterForm.add(t);
        add(filterForm);
        FilterToolbar filterToolbar = new FilterToolbar(grid,filterForm);
        filterToolbar.setVisible(false);
        grid.addTopToolbar(filterToolbar);
//		grid.addBottomToolbar(new ExportToolbar(grid).addDataExporter(new CSVDataExporter(){
//			@Override
//			public <T> void exportData(IDataProvider<T> dataProvider,List<IExportableColumn<T,?>> columns,OutputStream outputStream) throws IOException{
//				dataProvider = contactProvider;
//				return;
//			}
//		}));
		filterForm.add(grid);
		
		csvButton = new AjaxLink("csvButton"){
			public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				OpenCSVWriter openCSVWriter = new OpenCSVWriter();
				try {
					// set new dataProvider
					openCSVWriter.generateCSV(contactProvider.getContactList());
				} catch (CsvDataTypeMismatchException e) {
					e.printStackTrace();
				} catch (CsvRequiredFieldEmptyException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		};
		add(csvButton);
	    
	    AddButton = new AjaxLink("addButton"){
			public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				addWindow.show(target);
				
			}
		};
		add(AddButton);
		
		logoutButton = new AjaxLink("logoutButton"){
			public MarkupContainer setDefaultModel(IModel model) {
				return null;
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
				AuthenticatedWebSession.get().invalidate();
	            setResponsePage(getApplication().getHomePage());
			}
		};
		add(logoutButton);
		
		addWindow = new ModalWindow("addWindow");
		addWindow.setPageCreator(new ModalWindow.PageCreator() {
            public Page createPage() {
                return new AddWindow(MainPage.this.getPageReference(),addWindow);
            }
        });
		addWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback(){
			public void onClose(AjaxRequestTarget target) {
				if("Yes".equals(passValue)){
					Contact c = getContact();
					user.getContactList().add(c);
					addressBookService.updateUserData(user.getId(),user);
					target.add(grid);
				}	
				passValue = "No";
			}
	    });
		add(addWindow);
		editWindow = new ModalWindow("editWindow",Model.of(Contact.class));
		editWindow.setPageCreator(new ModalWindow.PageCreator() {
            public Page createPage() {
                return new EditWindow(MainPage.this.getPageReference(),editWindow);
            }
        });
		editWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback(){
			public void onClose(AjaxRequestTarget target) {
				if("Yes".equals(passValue)){
//					int idx = (Integer) editWindow.getSession().getAttribute("idx");
					Contact cNew = (Contact) editWindow.getSession().getAttribute("model");
//					Contact cNew = (Contact)actionPanel.getDefaultModel().getObject();
					for(int i = 0 ; i < user.getContactList().size() ; i++){
						if(cNew.getId() == user.getContactList().get(i).getId()) 
							user.getContactList().set(i, cNew);
					}
					addressBookService.updateUserData(user.getId(),user);
					target.add(grid);					
				}
				passValue = "No";
			}
	    });
		add(editWindow);
		warndialog = new ModalWindow("warndialog");
		warndialog.setPageCreator(new ModalWindow.PageCreator() {
            public Page createPage() {
                return new WarningWindow(MainPage.this.getPageReference(),warndialog);
            }
        });
		warndialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback(){
			public void onClose(AjaxRequestTarget target) {
				if("Yes".equals(passValue)){
//					int idx = (Integer) editWindow.getSession().getAttribute("idx");
//					user.getContactList().remove(idx);
//					Contact cNew = getContact();
					Contact cNew = (Contact) warndialog.getSession().getAttribute("model");
					for(int i = 0 ; i < user.getContactList().size() ; i++){
						if(cNew.getId() == user.getContactList().get(i).getId()) 
							user.getContactList().remove(i);
					}
					addressBookService.updateUserData(user.getId(),user);
					target.add(grid);
				}
				passValue = "No";
			}
	    });
    	add(warndialog);
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
	    AuthenticatedWebApplication app = (AuthenticatedWebApplication)Application.get();
	    //if user is not signed in, redirect him to sign in page
	    if(!AuthenticatedWebSession.get().isSignedIn())
	       app.restartResponseAtSignInPage();
	}
	
	
	public String getPassValue() {
		return passValue;
	}

	public void setPassValue(String passValue) {
		this.passValue = passValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
}
//grid = new EditableGrid("grid", getColumns(), new EditableListDataProvider(user.getContactList()), 10, Contact.class){
//@Override
//protected void onError(AjaxRequestTarget target) {
//	
//}
//@Override
//protected void onAdded(AjaxRequestTarget target, Contact newRow) {
//	System.out.println(newRow);
//}
//@Override
//protected void onSave(AjaxRequestTarget target, IModel rowModel) {
//	System.err.println(rowModel.getObject());
//}
//};
//add(grid);
//private List<AbstractEditablePropertyColumn> getColumns() {
//List<AbstractEditablePropertyColumn> columns = new ArrayList<AbstractEditablePropertyColumn>();
//columns.add(new RequiredEditableTextFieldColumn(new Model("First Name"), "firstName"));
//columns.add(new RequiredEditableTextFieldColumn(new Model("Last Name"), "lastName"));
//columns.add(new RequiredEditableTextFieldColumn(new Model("Email"), "email"));
//columns.add(new RequiredEditableTextFieldColumn(new Model("Company"), "company"));
//columns.add(new RequiredEditableTextFieldColumn(new Model("Phone"), "phone"));
//columns.add(new RequiredEditableTextFieldColumn(new Model("Country"), "country"));
//columns.add(new RequiredEditableTextFieldColumn(new Model("Address"), "address"));
//return columns;
//}

//warndialog = new MessageDialog("warndialog", "Warning", "Do you want to submit this connection", DialogButtons.YES_NO, DialogIcon.WARN) {
//
//	private static final long serialVersionUID = 1L;
//	
//	//document make the mistake in here
//	protected void onOpen(IPartialPageRequestHandler target){
//		
//	}
//	
//	public void onClose(IPartialPageRequestHandler arg0, DialogButton arg1) {
//		
//	}
//};
//add(warndialog);


//checkBoxColumn 
//submitCancelColumn = new SubmitCancelColumn("Action",Model.of("Edit")){
//	@Override
//    protected void onCancel(AjaxRequestTarget target, IModel rowModel, WebMarkupContainer rowComponent) {
//		passValue = "delete";
//		warndialog.show(target);
//		Contact c = (Contact)rowModel.getObject();
//		if(user.getContactList().contains(c)) user.getContactList().remove(c);
//		setUser(user);
//    }
//	
//	@Override
//    protected void onSubmitted(AjaxRequestTarget target, IModel rowModel, WebMarkupContainer rowComponent) {
//		passValue = "submit";
//		warndialog.show(target);
//		Contact c = (Contact)rowModel.getObject();
//		user.getContactList().set(((ListItem)rowComponent).getIndex(), c);				
//		setUser(user);
//    }
//	
//    @Override
//    public AbstractColumn setInitialSize(int initialSize) {
//        return super.setInitialSize(60);
//    }
//};
//ListDataProvider listDataProvider = new ListDataProvider(user.getContactList());
//List<IGridColumn> cols = (List) Arrays.asList(
//		new CheckBoxColumn("checkId"),
//		new EditablePropertyColumn(new Model("First Name"), "firstName"),
//        new EditablePropertyColumn(new Model("Last Name"), "lastName"),
//        new EditablePropertyColumn(new Model("Email"), "email"),
//        new EditablePropertyColumn(new Model("Company"), "company"),
//        new EditablePropertyColumn(new Model("Phone"), "phone"),
//        new EditablePropertyColumn(new Model("Country"), "country"),
//        new EditablePropertyColumn(new Model("Address"), "address"),
//        submitCancelColumn);
//AppendableDataProviderAdapter -> can change the dataSet
//DataProviderAdapter -> fix dataSet
//grid = new DefaultDataGrid("grid",new AppendableDataProviderAdapter(listDataProvider), cols);
//{
//	@Override
//	public void addHeaderToolbar(AbstractHeaderToolbar toolbar) { 
//		
//    } 
//};

//grid.setAllowSelectMultiple(true);
//grid.setSelectToEdit(true);
//grid.setClickRowToSelect(true);
//add(grid);
