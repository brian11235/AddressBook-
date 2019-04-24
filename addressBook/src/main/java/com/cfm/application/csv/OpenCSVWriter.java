package com.cfm.application.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.cfm.entity.Contact;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
/**
 * 
 * CSV generator 
 * @author linbrian
 *
 */
public class OpenCSVWriter {
	private final String OBJECT_LIST_SAMPLE = "/Desktop/output.csv";
	
	
	public void generateCSV(List<Contact> contactList)	throws IOException,CsvDataTypeMismatchException,CsvRequiredFieldEmptyException, IllegalArgumentException, IllegalAccessException {
		String userhome = System.getProperty("user.home");
		String path = userhome + OBJECT_LIST_SAMPLE;
		CSVWriter writer  = new CSVWriter(new FileWriter(path));
		
		List<String[]> totalList = new ArrayList<>();
		List<String> propertyList = new ArrayList<>();
		// reflect properties
		
		if(contactList.size() != 0){
			Contact c = contactList.get(0);
			for(Field f : c.getClass().getDeclaredFields()){
				String s = f.getName();
				if(!"serialVersionUID".equals(s) && ! "id".equals(s)) propertyList.add(s);
			}
			int size = propertyList.size();
			String[] str = new String[size];
			// column head
			for(int i = 0 ; i < str.length ; i++) str[i] = propertyList.get(i);
			totalList.add(str);
			// column content
			for(Contact contact : contactList){
				str = new String[size];
				Field[] fields = contact.getClass().getDeclaredFields();
				for(int i = 0 ; i < fields.length ; i++){
					fields[i].setAccessible(true);
					if("serialVersionUID".equals(fields[i].getName()) || "id".equals(fields[i].getName())) continue;
					str[i-2] = (String)fields[i].get(contact);
				}
				totalList.add(str);
			}
			for(String[] line : totalList) writer.writeNext(line);
		}
		writer.close();
	}
}
