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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worklight.server.auth.api.AuthenticationResult;
import com.worklight.server.auth.api.AuthenticationStatus;
import com.worklight.server.auth.api.MissingConfigurationOptionException;
import com.worklight.server.auth.api.UserIdentity;
import com.worklight.server.auth.api.WorkLightAuthenticator;

@SuppressWarnings("serial")
public class CustomAuthenticator implements WorkLightAuthenticator {

	private Map<String, Object> authenticationData = new HashMap<String, Object>();
	
	@Override
	public void init(Map<String, String> options) throws MissingConfigurationOptionException {
		
	}

	@Override
	public AuthenticationResult processRequest(HttpServletRequest request,
		HttpServletResponse response, boolean isAccessToProtectedResource)
		throws IOException, ServletException {
		System.out.println("processRequest with my_custom_auth_request_url");
		if (request.getRequestURI().contains("my_custom_auth_request_url")){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			if (null != username && null != password && username.length() > 0 && password.length() > 0){
				authenticationData.put("username", username);
				authenticationData.put("password", password);
				System.out.println("Authentication data username : "+username +" password "+password);
				return AuthenticationResult.createFrom(AuthenticationStatus.SUCCESS);
			} else {
				response.setContentType("application/json; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				response.getWriter().print("{\"authStatus\":\"required\", \"errorMessage\":\"Please enter username and password\"}");
				return AuthenticationResult.createFrom(AuthenticationStatus.CLIENT_INTERACTION_REQUIRED);
			}
		} 
		
		if (!isAccessToProtectedResource) 
			return AuthenticationResult.createFrom(AuthenticationStatus.REQUEST_NOT_RECOGNIZED);
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.getWriter().print("{\"authStatus\":\"required\"}");
		return AuthenticationResult.createFrom(AuthenticationStatus.CLIENT_INTERACTION_REQUIRED);
	}

	@Override
	public AuthenticationResult processRequestAlreadyAuthenticated(
		HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		System.out.println("processRequestAlreadyAuthenticated");
		return AuthenticationResult.createFrom(AuthenticationStatus.REQUEST_NOT_RECOGNIZED);
	}

	@Override
	public Map<String, Object> getAuthenticationData() {
		return authenticationData;
	}

	@Override
	public AuthenticationResult processAuthenticationFailure(
		HttpServletRequest request, HttpServletResponse response,
		String errorMessage) throws IOException, ServletException {
		System.out.println("processAuthenticationFailure");
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.getWriter().print("{\"authStatus\":\"required\", \"errorMessage\":\"" + errorMessage + "\"}");
		return AuthenticationResult.createFrom(AuthenticationStatus.CLIENT_INTERACTION_REQUIRED);
	}

	@Override
	public HttpServletRequest getRequestToProceed(
		HttpServletRequest currentRequest,
		HttpServletResponse currentResponse, UserIdentity user)
		throws IOException {
		return null;
	}

	@Override
	public boolean changeResponseOnSuccess(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		System.out.println("changeResponseOnSuccess");
		if (request.getRequestURI().contains("my_custom_auth_request_url")){
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.getWriter().print("{\"authStatus\":\"complete\"}");
			return true;
		}
		
		return false;
	}

	@Override
	public WorkLightAuthenticator clone() throws CloneNotSupportedException {
		CustomAuthenticator otherAuthenticator = (CustomAuthenticator) super.clone();
        otherAuthenticator.authenticationData = new HashMap<String, Object>(authenticationData);
        return otherAuthenticator;
	}
}