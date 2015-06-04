/*
 *    Licensed Materials - Property of IBM
 *    5725-I43 (C) Copyright IBM Corp. 2015. All Rights Reserved.
 *    US Government Users Restricted Rights - Use, duplication or
 *    disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
*/

package com.ibm.wishlist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.worklight.adapters.rest.api.MFPSendMessageFailedException;
import com.worklight.adapters.rest.api.MFPServerOperationException;
import com.worklight.adapters.rest.api.WLServerAPI;
import com.worklight.adapters.rest.api.WLServerAPIProvider;
import com.worklight.adapters.rest.api.push.INotification;
import com.worklight.core.auth.OAuthSecurity;

@Path("/push")
public class PushAdapterResource {
	/*
	 * For more info on JAX-RS see https://jsr311.java.net/nonav/releases/1.1/index.html
	 */

	//Define logger (Standard java.util.Logger)
	static Logger logger = Logger.getLogger(PushAdapterResource.class.getName());

    //Define the server api to be able to perform server operations
    WLServerAPI api = WLServerAPIProvider.getWLServerAPI();

    /* Path for method: "<server address>/wishlist/adapters/PushAdapter/push/wishlistItem?name=gwatch&store=bangalore&price=400" */
    @GET
    @OAuthSecurity(enabled=false)
    @Path("/wishlistItem")
    public String sendNirvanaTask(@QueryParam("name") String name,@QueryParam("store") String store,@QueryParam("price")int price){
    	//send notification 
    	INotification notification = api.getPushAPI().buildNotification();
    	notification.getTarget().setTagnames("wishlist");
    	JsonObject payload = new JsonObject();
    	payload.addProperty("name",name);
    	payload.addProperty("store",store);
    	payload.addProperty("price",price);
    	notification.getMessage().setAlert(payload.toString());
    	try{
    		api.getPushAPI().sendMessage(notification, "IOSWishList");
    		return "A wishlist item "+payload.getAsString()+" is sent to application IOSWishList";
    	}catch(MFPSendMessageFailedException e){
    		return "There was an error while sending the notification \n\n"+e.getMessage();
    	}
    }
}