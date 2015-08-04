/*
 *  Licensed Materials - Property of IBM
 *  5725-I43 (C) Copyright IBM Corp. 2011, 2013. All Rights Reserved.
 *  US Government Users Restricted Rights - Use, duplication or
 *  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

/**
 *  WL.Server.invokeHttp(parameters) accepts the following json object as an argument:
 *  
 *  {
 *  	// Mandatory 
 *  	method : 'get' , 'post', 'delete' , 'put' or 'head' 
 *  	path: value,
 *  	
 *  	// Optional 
 *  	returnedContentType: any known mime-type or one of "json", "css", "csv", "plain", "xml", "html"  
 *  	returnedContentEncoding : 'encoding', 
 *  	parameters: {name1: value1, ... }, 
 *  	headers: {name1: value1, ... }, 
 *  	cookies: {name1: value1, ... }, 
 *  	body: { 
 *  		contentType: 'text/xml; charset=utf-8' or similar value, 
 *  		content: stringValue 
 *  	}, 
 *  	transformation: { 
 *  		type: 'default', or 'xslFile', 
 *  		xslFile: fileName 
 *  	} 
 *  } 
 */

function getCatalog() {
	var jsonData = [{"productID":"00001","title":"iPad Air 2","store":"bangalore","price":"600","photo":"/images/iPadAir2.jpg"},{"productID":"00002","title":"Xbox360 500GB","store":"Raleigh","price":"250","photo":"/images/xbox360.jpg"},{"productID":"00003","title":"Samsung 65-inch LED TV","store":"New York","price":"2600","photo":"/images/samsung65tv.jpg"},{"productID":"00004","title":"MacBook Pro","store":"Los Angeles","price":"1400","photo":"/images/macbook_pro.jpg"},{"productID":"00005","title":"Galaxy S6 Edge","store":"Boston","price":"300","photo":"/images/gs6edge.png"}];
	return {getAllProductsDetailsReturn : jsonData};
} 

