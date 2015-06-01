/*
 *
    COPYRIGHT LICENSE: This information contains sample code provided in source code form. You may copy, modify, and distribute
    these sample programs in any form without payment to IBM® for the purposes of developing, using, marketing or distributing
    application programs conforming to the application programming interface for the operating platform for which the sample code is written.
    Notwithstanding anything to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS AND IBM DISCLAIMS ALL WARRANTIES,
    EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY,
    FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR ANY DIRECT,
    INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE.
    IBM HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR MODIFICATIONS TO THE SAMPLE SOURCE CODE.

 */
package com.ibm.sample.wishlist;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.worklight.server.auth.api.MissingConfigurationOptionException;
import com.worklight.server.auth.api.UserIdentity;
import com.worklight.server.auth.api.WorkLightAuthLoginModule;
import com.worklight.server.auth.api.WorkLightLoginModuleBase;

@SuppressWarnings("serial")
public class PropertiesFileLoginModule implements WorkLightAuthLoginModule {

	private String userName;
	private String password;
	 
	private Properties props;
	
	@Override
	public boolean login(Map<String, Object> authenticationData) {
		userName = (String) authenticationData.get("username");
		password = (String) authenticationData.get("password");
		System.out.println("username : "+userName + " password : "+password);
		String passwordFromProps = (String) props.get(userName); 
        return (passwordFromProps != null && passwordFromProps.equals(password)); 
	}

	@Override
	public void logout() {
		userName = null;
		password = null;
	}

	@Override
	public void abort() {
		userName = null;
		password = null;
	}

	@Override
	public void init(Map<String, String> options)
		throws MissingConfigurationOptionException {
		props = new Properties();
	 	InputStream users = 
	 		this.getClass().getClassLoader().getResourceAsStream("users.properties"); 
	 	try {
			props.load(users);
		} catch (IOException e) {
			throw new MissingConfigurationOptionException("Properties file");
		}
	}

	@Override
	public WorkLightLoginModuleBase clone() throws CloneNotSupportedException {
		return (PropertiesFileLoginModule) super.clone();
	}

	@Override
	public UserIdentity createIdentity(String loginModule) {
		System.out.println("inside createIdentity");
		HashMap<String, Object> customAttributes = new HashMap<String, Object>();
		customAttributes.put("AuthenticationDate", new Date());
		
		UserIdentity identity = new UserIdentity(loginModule, userName, null,
			null, customAttributes, password);
	
		return identity;
	}
}