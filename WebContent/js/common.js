
// Bookmark page
function bookmark(url, description) { 
	if (window.external) { 
		window.external.AddFavorite(url, description) 
	} 
	else { 
		alert("Your browser doesn't support this function  :("); 
	}
}

function notEmpty(str)
{
	return str && str!=null && str != '' && str != 'null'
}

function show(id) {
	$(id).style.visibility='visible';
	$(id).style.display='inline';
}

function hide(id) {
	$(id).style.visibility='hidden';
	$(id).style.display='none';
}

// Appends a new option to a select element
function appendOptionToSelectElement(select, value, text, selected)
{
	if(!selectContainsValue(select, value)) {
		if (select!=null && select.options!=null) {
			select.options[select.options.length] = new Option(text, value, false, selected);
		}
	}
}

function selectContainsValue(select, value) {
	for (var i=0; i < select.options.length; i++) { 
		if(value == select.options[i].value)
			return true;
	}
	return false;
}

function removeElementsFromSelect(select, startPosit)
{
	if(select.length > startPosit) {
		for(i = startPosit; i < select.length;) {
			select.options[i] = null;
		}
	}
}

function getURLParameter(name)
{
	name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
	var regexS = "[\\?&]"+name+"=([^&#]*)";
	var regex = new RegExp( regexS );
	var results = regex.exec( window.location.href );
	if( results == null )
		return "";
	else
		return results[1];
}

/***** These functions are used for the voting buttons **/
var thumbsUpImageString = '<img src="/images/thumbs_up_green.jpg">';
var thumbsDownImageString = '<img src="/images/thumbs_down_red.jpg">';
function vote(storyId, vote, idToUpdate, updateStats) {
	var url = '/servlet/VoteHelper?storyId='+storyId+'&vote='+vote;
	new Ajax.Request(url,
	{
    	method:'get',
    	onSuccess: function(transport){
			if(updateStats) {
				var statsUrl = '/includes/widgets/storyStats.jsp?storyId='+storyId;
				new Ajax.Updater('story_stats_'+storyId, statsUrl, {
					method: 'post'
				})
			}
		},
    	onFailure: function(){ alert('Unable to connect to server...') }
	});
}

var saveStartPosition = 0;
function refreshRSSFeed() {
	refreshRSSFeed(saveStartPosition);
}

function refreshRSSFeed(start) {
	var url = '/includes/widgets/nationalFeed.jsp?rssStart='+start;
	var teamId = getURLParameter('teamId');
	var categoryId = getURLParameter('catId');
	if(teamId > 0)
		url = url+'&teamId='+teamId;
	else if(categoryId > 0)
		url = url+'&catId='+categoryId;
	new Ajax.Updater('national_feeds', url, {
		method: 'get' 
	})
	saveStartPosition = start;
}

function voteFromRSSFeed(storyId, vote) {
	var url = '/servlet/VoteHelper?storyId='+storyId+'&vote='+vote;
	new Ajax.Request(url,
	{
    	method:'get',
    	onSuccess: function(transport){
			refreshRSSFeed();
		},
    	onFailure: function(){ alert('Unable to connect to server...') }
	});

}
/******* End voting button functions *****/
 
 
function grayOut(vis, options) {
  // Pass true to gray out screen, false to ungray
  // options are optional.  This is a JSON object with the following (optional) properties
  // opacity:0-100         // Lower number = less grayout higher = more of a blackout 
  // zindex: #             // HTML elements with a higher zindex appear on top of the gray out
  // bgcolor: (#xxxxxx)    // Standard RGB Hex color code
  // grayOut(true, {'zindex':'50', 'bgcolor':'#0000FF', 'opacity':'70'});
  // Because options is JSON opacity/zindex/bgcolor are all optional and can appear
  // in any order.  Pass only the properties you need to set.
  var options = options || {}; 
  var zindex = options.zindex || 50;
  var opacity = options.opacity || 70;
  var opaque = (opacity / 100);
  var bgcolor = options.bgcolor || '#000000';
  var dark=document.getElementById('darkenScreenObject');
  if (!dark) {
    // The dark layer doesn't exist, it's never been created.  So we'll
    // create it here and apply some basic styles.
    // If you are getting errors in IE see: http://support.microsoft.com/default.aspx/kb/927917
    var tbody = document.getElementsByTagName("body")[0];
    var tnode = document.createElement('div');           // Create the layer.
        tnode.style.position='absolute';                 // Position absolutely
        tnode.style.top='0px';                           // In the top
        tnode.style.left='0px';                          // Left corner of the page
        tnode.style.overflow='hidden';                   // Try to avoid making scroll bars            
        tnode.style.display='none';                      // Start out Hidden
        tnode.id='darkenScreenObject';                   // Name it so we can find it later
    tbody.appendChild(tnode);                            // Add it to the web page
    dark=document.getElementById('darkenScreenObject');  // Get the object.
  }
  if (vis) {
    // Calculate the page width and height 
    if( document.body && ( document.body.scrollWidth || document.body.scrollHeight ) ) {
        var pageWidth = document.body.scrollWidth+'px';
        var pageHeight = document.body.scrollHeight+'px';
    } else if( document.body.offsetWidth ) {
      var pageWidth = document.body.offsetWidth+'px';
      var pageHeight = document.body.offsetHeight+'px';
    } else {
       var pageWidth='100%';
       var pageHeight='100%';
    }   
    //set the shader to cover the entire page and make it visible.
    dark.style.opacity=opaque;                      
    dark.style.MozOpacity=opaque;                   
    dark.style.filter='alpha(opacity='+opacity+')'; 
    dark.style.zIndex=zindex;        
    dark.style.backgroundColor=bgcolor;  
    dark.style.width= pageWidth;
    dark.style.height= pageHeight;
    dark.style.display='block';                          
  } else {
     dark.style.display='none';
  }
}


function absoluteCenter(divId) {
	var IpopTop = (document.body.clientHeight - $(divId).offsetHeight) / 2;
	var IpopLeft = (document.body.clientWidth - $(divId).offsetWidth) / 2;
	$(divId).style.left=IpopLeft + document.body.scrollLeft;
	$(divId).style.top=IpopTop + document.body.scrollTop;
	$(divId).style.display = "block";
}


function emailStory(storyId, toId, fromId, msgId) {
	var url = '/includes/widgets/emailStory.jsp?emailStoryId='+storyId+'&to='+$(toId).value+'&from='+$(fromId).value+'&msg='+$(msgId).value;
	new Ajax.Updater('emailBox'+storyId, url, {
		method: 'post'
	})
}

function postToFacebookPopup(url, divId) {
	var url = '/includes/widgets/postToFacebook.jsp?url='+url;
	new Ajax.Updater(divId, url, {
		method: 'post'
	})
	show(divId);
	grayOut(true,'');
	absoluteCenter(divId);
}

function popitup(url) {
	newwindow=window.open(url,'name','height=350,width=500,left=200,top=100');
	if (window.focus) {newwindow.focus()}
	return false;
}

function buildFacebookShareUrl(storyId, storyTitle) {
	var link = 'http://www.facebook.com/sharer.php?u=';
	link = link + 'http://www.sportsbreaker.com/story.jsp?id='+storyId;
	link = link + '&t=SportsBreaker - '+storyTitle;
	return link;
}


