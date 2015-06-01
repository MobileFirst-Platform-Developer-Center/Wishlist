/*
 *  Licensed Materials - Property of IBM
 *  5725-I43 (C) Copyright IBM Corp. 2011, 2013. All Rights Reserved.
 *  US Government Users Restricted Rights - Use, duplication or
 *  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

function getCatalog() {
	var path = 'boxstore-catalog.mybluemix.net';
	
	var input = {
	    method : 'post',
	    returnedContentType : 'xml',
	    path : '/MFPSampleWebService/services/ProductLookup', 
	    body : {
            content : 
            	'<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">\n' +
                '<soap:Body>\n' + 
                '</soap:Body>\n' +
                '</soap:Envelope>\n',
            contentType : 'text/xml; charset=utf-8'
	    } 
	};

	input['headers'] = { 'SOAPAction' : '' };
	return WL.Server.invokeHttp(input);
}