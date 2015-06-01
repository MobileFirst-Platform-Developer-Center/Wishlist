/*
 *    Licensed Materials - Property of IBM
 *    5725-I43 (C) Copyright IBM Corp. 2015. All Rights Reserved.
 *    US Government Users Restricted Rights - Use, duplication or
 *    disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
*/

package com.ibm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.worklight.adapters.rest.api.WLServerAPI;
import com.worklight.adapters.rest.api.WLServerAPIProvider;
import com.worklight.server.auth.api.MissingConfigurationOptionException;

@Path("/localstore")
public class LocalStoreAdapterResource {
	/*
	 * For more info on JAX-RS see https://jsr311.java.net/nonav/releases/1.1/index.html
	 */
		
	Properties props;
	JsonParser parser;
	
	//Define logger (Standard java.util.Logger)
	static Logger logger = Logger.getLogger(LocalStoreAdapterResource.class.getName());

    //Define the server api to be able to perform server operations
    WLServerAPI api = WLServerAPIProvider.getWLServerAPI();
    
    private void init() throws MissingConfigurationOptionException{
    	parser = new JsonParser();
    	props = new Properties();
	 	InputStream itemData = 
	 		this.getClass().getClassLoader().getResourceAsStream("data.properties"); 
	 	try {
			props.load(itemData);
		} catch (IOException e) {
			throw new MissingConfigurationOptionException("Properties file");
		}
    }
    

	/* Path for method: "<server address>/wishlist/adapters/LocalStoreAdapter/users" */
	@GET
	@Path("/getAllItems")
	public String getAllItems() throws MissingConfigurationOptionException{
		init();
		JsonArray jsonArray = new JsonArray();
		for(Object key : props.keySet()){
			jsonArray.add(parser.parse(props.getProperty((String) key)).getAsJsonObject());
		}
		return jsonArray.toString();
	}
		
	@GET
	@Path("/addItem")
	public String addItem(@QueryParam("itemjson")String itemJson) 
			throws MissingConfigurationOptionException, URISyntaxException, IOException{
		try{
			init();
			int newKey = props.keySet().size()+1;
			props.put(String.valueOf(newKey), itemJson);
			URL url = this.getClass().getClassLoader().getResource("data.properties"); 
			File file = new File(url.toURI().getPath());
			FileOutputStream foStream = new FileOutputStream(file);
			props.store(foStream, "saving new item");
			return "true";
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return "false";
	}
	
	@GET
	@Path("/addAllItems")
	public String addAllItems(@QueryParam("allitemsjson")String itemsJson) 
			throws MissingConfigurationOptionException, URISyntaxException, IOException{
		try{
			init();
			clearAllData();
			JsonArray jsonArr = parser.parse(itemsJson).getAsJsonArray();
			for(int i=0;i<jsonArr.size();i++){
				props.put(String.valueOf(i+1), jsonArr.get(i).toString());
			}
			URL url = this.getClass().getClassLoader().getResource("data.properties"); 
			File file = new File(url.toURI().getPath());
			FileOutputStream foStream = new FileOutputStream(file);
			props.store(foStream, "saving new item");
			return "true";
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return "false";
	}
	
	@GET
	@Path("/clearAll")
	public String clearAllData() 
			throws MissingConfigurationOptionException, URISyntaxException, IOException{
			init();
			props.clear();
			return "cleared";
	}
	
}