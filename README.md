#Overview

YahooMailApiSample is a tool which developers can use to run various [Yahoo Mail web service](http://developer.yahoo.com/mail/) API's.
The tool uses Signpost OAuth library. For more information on OAuth refer [http://oauth.net](http://oauth.net) and for how Yahoo implements it refer [http://developer.yahoo.com/oauth/](http://developer.yahoo.com/oauth/).

#Code

<b>YahooMail.java</b> : The main java file which does the job is YahooMail.java. It creates an OAuth request using Signpost APIs and sends it to the JSON-RPC (http://mail.yahooapis.com/ws/mail/v1.1/jsonrpc) endpoint. The response is printed out and is also written to a log file. The code also uses [org.json](http://json.org/) library for encoding and decoding json objects.    

<b>com.centerkey.utils.BareBonesBrowserLaunch.java</b>: Launches the browser for OAuth authorization.

<b>json</b> : Refer this link [json.org](http://www.json.org/java/)

#JSON Requests

The list of request's below can give you an idea on how to form a JSON request

    CreateFolder_request={
	  "method": CreateFolder,
	  "params": [
	    {
	      "name": "TestFolder"
	    }
	  ]
	}
	
    GetUserData_request={
	  "method": GetUserData,
	  "params": [
	    {
   		}
	  ]
	}
    
    RemoveFolder_request={
	  "method": RemoveFolder,
	  "params": [
	    {
	      "fid": "TestFolder"
	    }
	  ]
	}

    
    listfolders.request={
	  "method": "ListFolders",
	  "params": [
	    {

	    }
	  ]
	}

    sendmessage.request={
	  "method": "SendMessage",
	  "params": [
	    {
	      "savecopy": true,
	      "message": {
	        "to": {
	          "email": "test@yahoo.com",
	          "name": "SendMessage Test User"
	        },
	        "from": {
	          "email": "ctest@yahoo.com",
	          "name": "SendMessage Test User"
	        },
	        "replyto": {
	          "email": "test@yahoo.com",
	          "name": "SendMessage Test User"
	        },
	        "inreplyto": "SendMessage InReplyTo Value",
	        "mailer": "YahooMailRC",
	        "subject": "Message with Stationery",
	        "simplebody": {
	          "text": "Let it snow\\n",
	          "html": "your html page"
	        }
	      }
	    }
	  ]
	}

    listmessages.request={
	  "method": "ListMessages",
	  "params": [
	    {
	      "fid": "Inbox",
	      "numInfo": 25,
	      "numMid": 25,
	      "sortKey": "date",
	      "sortOrder": "up",
	      "groupBy": "unRead"
	    }
	  ]
	}

    flagmessages.request={
	  "method": "FlagMessages",
	  "params": [
	    {
	      "fid": "Inbox",
	      "selection": {

	      },
	      "setFlags": {
	        "read": 1
	      }
	    }
	  ]
	}

    movemessages.request={
	  "method": "MoveMessages",
	  "params": [
	    {
	      "sourceFid": "Inbox",
	      "destinationFid": "perfTestFolder",
	      "selection": {

	      }
	    }
	  ]
	}

    movemessages.request={
	  "method": "MoveMessages",
	  "params": [
	    {
	      "sourceFid": "perfTestFolder",
	      "destinationFid": "Inbox",
	      "selection": {

	      }
	    }
	  ]
	}

    savemessage.request={
	  "method": "SaveMessage",
	  "params": [
	    {
	      "destination": {
	        "fid": "Inbox"
	      },
	      "message": {
	        "to": {
	          "email": "test34@yahoo.com",
	          "name": "SaveMessage Test User"
	        },
	        "from": {
	          "email": "ctest34@yahoo.com",
	          "name": "SaveMessage Test User"
	        },
	        "replyto": {
	          "email": "test34@yahoo.com",
	          "name": "SaveMessage Test User"
	        },
	        "inreplyto": "SaveMessage InReplyTo Value",
	        "subject": "SaveMessage Folder Test",
	        "body": {
	          "data": "This is a test.",
	          "type": "text",
	          "subtype": "html",
	          "charset": "us-ascii"
	        }
	      }
	    }
	  ]
	}
	
	
    getmessage.request={
	  "method": "GetMessage",
	  "params": [
	    {
	      "fid": "Inbox",
	      "message": [
	        {
	          "blockImages": "none",
	          "mid": "1_22_AKSCiGIAAMy\\\/SxWTUAgxY2Krl1M",
	          "expandCIDReferences": true,
	          "enableWarnings": true,
	          "restrictCSS": true
	        },
	        {
	          "blockImages": "none",
	          "mid": "1_2551_AKSCiGIAAMXrSxWTUwTGfiCiZtY",
	          "expandCIDReferences": true,
	          "enableWarnings": true,
	          "restrictCSS": true
	        }
	      ]
	    }
	  ]
	}

    renamefolder.request={
	  "method": "RenameFolder",
	  "params": [
	    {
	      "fid": "perfTestFolder",
	      "name": "RenamedPerfTestFolder"
	    }
	  ]
	}

    emptyfolder.request={
	  "method": "EmptyFolder",
	  "params": [
	    {
	      "fid": "test"
	    }
	  ]
	}

    deletemessages.request={
	  "method": "DeleteMessages",
	  "params": [
	    {
	      "fid": "TestFolder",
	      "mid": [
	        "1_22_AKSCiGIAAMy/SxWTUAgxY2Krl1M",
	        "1_1989_AKSCiGIAANdWSxWTUgpuIkomK"
	      ]
	    }
	  ]
	}
	
    getmessagerawheader.request={
	  "method": "GetMessageRawHeader",
	  "params": [
	    {
	      "fid": "Inbox",
	      "mid": [
	        "1_22_AKSCiGIAAMy/SxWTUAgxY2Krl1M",
	        "1_2551_AKSCiGIAAMXrSxWTUwTGfiCiZ"
	      ]
	    }
	  ]
	}

    searchmessages.request={
	  "method": "SearchMessages",
	  "params": [
	    {
	      "search": {
	        "fid": "Inbox",
	        "query": "the"
	      },
	      "numInfo": 2000,
	      "numMid": 2000,
	      "sortKey": "date",
	      "sortOrder": "up"
	    }
	  ]
	}


    setuserdata.request={
	  "method": "SetUserData",
	  "params": [
	    {
	      "setdata": {
	        "userUIPref": {
	          "defaultSortOrder": "up",
	          "useRichText": "dynamic"
	        },
	        "userSendPref": {
	          "showCcBcc": "show"
	        }
	      }
	    }
	  ]
	}

    batchexecute.request={
	  "method": "BatchExecute",
	  "params": [
	    {
	      "call": [
	        {
	          "GetUserData": {

	          }
	        },
	        {
	          "ListMessages": {
	            "fid": "Inbox",
	            "startInfo": 0,
	            "startMid": 0,
	            "numInfo": 50,
	            "numMid": 50,
	            "sortKey": "date",
	            "sortOrder": "down",
	            "verifyInAddressBook": 1,
	            "filterBy": [
	              {
	                "isRead": 1
	              }
	            ]
	          }
	        },
	        {
	          "ListFolders": {
	            "resetMessengerUnseen": 1
	          }
	        }
	      ]
	    }
	  ]
	}

    sendmessageattachment.request={
	  "method": "SendMessage",
	  "params": [
	    {
	      "savecopy": true,
	      "message": {
	        "to": {
	          "email": "test34@yahoo.com",
	          "name": "SendMessage TestUser"
	        },
	        "from": {
	          "email": "ctest34@yahoo.com",
	          "name": "SendMessage Attach Test User"
	        },
	        "replyto": {
	          "email": "test34@yahoo.com",
	          "name": "SendMessage Attach Test User"
	        },
	        "mailer": "YahooMailRC\\\/MailBeta YahooMailWebService\\\/V1",
	        "simplebody": {
	          "text": "SendMessage with message with one attachment FILENAME",
	          "attachment": [
	            {
	              "attachment": "upload:\\\/\\\/XXXXXXXXXX"
	            }
	          ]
	        },
	        "subject": "SendMessage with an attachment"
	      }
	    }
	  ]
	}

    sendmessageattachment2.request= {
	"method": "SendMessage",
	"params": 
	[{
		"savecopy": true,
		"message": {
			"to": {
				"email": "test1@yahoo.com",
				"name": "Test User"
			},
			"from": {
				"email": "test1@yahoo.com",
				"name": "Test User"
			},
			"replyto": {
				"email": "test1@yahoo.com",
				"name": "Test User"
			},	
			"simplebody": {
				"text": "Send Message",
				"attachment": {
					"attachment": "message:\/\/SendForwardTest\/1_232_AJfPjkQAAUYoTVm2CQ80Z0bD1qI"
				}
			},
			"subject": "Test Message for YDN - Attach message"
		}
	}]
    }


#Note on Licenses

The libraries (jar and external java code) used in this sample has its own license policies. Please refer the respective libraries home page for the same and for the rest please refer the LICENSE file. 





  
 
 


 
