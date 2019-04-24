package com.cfm.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.cfm.entity.User;
import com.cfm.service.AddressBookService;
/**
 * 
 * a Validator for phone Validate
 * @author linbrian
 *
 */
public class PhoneValidator implements IValidator<String>{
	
//	private String phone;
	public PhoneValidator(){
		super();
//		this.phone=phone;
	}
	
	public void validate(IValidatable<String> validatable) {
		String phone = validatable.getValue();
		Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	    Matcher matcher = pattern.matcher(phone);
	    if (!matcher.matches()) {
	    	ValidationError error = new ValidationError(this);
			error.setMessage("Phone Number must be in the form XXX-XXXXXXX");
			validatable.error(error);
	    }
	}
}
