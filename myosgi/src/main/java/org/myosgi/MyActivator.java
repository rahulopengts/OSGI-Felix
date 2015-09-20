package org.myosgi;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class MyActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		 Hashtable<String, String> props = new Hashtable<String, String>();
	        props.put("Language", "English");
	        context.registerService(
	            DictionaryService.class.getName(), new DictionaryImpl(), props);	

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
