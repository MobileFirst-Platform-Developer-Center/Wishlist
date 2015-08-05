
/* JavaScript content from js/Page2.js in folder common */

/* JavaScript content from js/Page2.js in folder common */

/* JavaScript content from js/Page2.js in folder common */

/* JavaScript content from js/Page2.js in folder common */

/* JavaScript content from js/Page2.js in folder common */
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

currentPage={};

currentPage.init = function() {
};

currentPage.buttonClick = function() {
	WL.SimpleDialog.show("Page2","Button on Page2 was clicked",[{text:'OK'}]);
};

currentPage.loadPage1 = function(){
	pagesHistory.push(path + "pages/Page2.html");
	$("#pagePort").load(path + "pages/Page1.html", function(){
		$.getScript(path + "js/Page1.js", function() {
			if (currentPage.init) {
				currentPage.init();
			}
		});
	});
};

currentPage.back = function(){
	$("#pagePort").load(pagesHistory.pop());
};

currentPage.loadPage = function(pageIndex){
    $("#pagePort").load(path + "pages/Page" + pageIndex + ".html");
    if(pageIndex == "1"){
        $("#AppHeader").html("Catalog");
    }else if(pageIndex == "2"){
        $("#AppHeader").html("Wish List");
    }else if(pageIndex == "3"){
        $("#AppHeader").html("Settings");
    }else if(pageIndex == "4"){
        $("#AppHeader").html("Add Item");
    }
};

currentPage.loadHome = function(){
    $("#pagePort").load(path + "pages/MainPage.html");
    $("#AppHeader").html("Home");
};