package com.cfm.application;

import java.net.URL;

import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.resource.IResourceStream;
/**
 * 
 * Put the html file to webapp file 
 * @author linbrian
 *
 */
public class Locator extends ResourceStreamLocator{
	@Override
	public IResourceStream locate(Class<?> clazz, String path) {
		String extension = path.substring(path.lastIndexOf(".") + 1);
		String clazzName=clazz.getName();
		String fileName=clazzName.substring(clazzName.lastIndexOf(".")+1);
		URL url;
		try{
			url=WebApplication.get().getServletContext().getResource("/"+fileName+"."+extension);
			if(url!=null){
				return new UrlResourceStream(url);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return super.locate(clazz, path);
	}
}
