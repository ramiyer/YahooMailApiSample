import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import json.JSONArray;
import json.JSONObject;

import com.centerkey.utils.BareBonesBrowserLaunch;

public class YahooMail {

	public static String  JSONURL = "http://mail.yahooapis.com/ws/mail/v1.1/jsonrpc";
	private static Properties jsonProps = null;
	private static Properties appProps = null;
	
	private static String pwd = System.getProperty("user.dir");
	
	public static Properties loadJsonProperties() {
		
	    Properties jsonP = new Properties();
	    try {
	    	java.io.FileInputStream fisJ = new java.io.FileInputStream
	        	(new java.io.File( pwd +"/Properties/test.properties"));
	        jsonP.load(fisJ);
	        fisJ.close();
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	    return jsonP;
	}

	public static Properties loadAppProperties() {
		
	    Properties appP = new Properties();
	    
	    try {
	    	java.io.FileInputStream fisJ = new java.io.FileInputStream
	        	(new java.io.File( pwd +"/Properties/app.properties"));
	        appP.load(fisJ);
	        fisJ.close();
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	    return appP;
	}

	public YahooMail() throws FileNotFoundException, IOException{
		
		appProps = loadAppProperties();
		jsonProps = loadJsonProperties();
	}

    public Properties getJsonProps(){
        return jsonProps;
    }
	
	public Map<String,String> splitter(String toBeSplit){
		Map<String,String> map = new LinkedHashMap<String,String>();
        String[] parts =  toBeSplit.split("," , 2);
        map.put(parts[0], parts[1]);
        return map;
    }
	
	public Properties getAppProps(){
		return appProps;
    }

    public static void main(String[] args) throws Exception {
    	
    	//Initialize Logging related
    	Logger log = Logger.getLogger("YahooMail");
    	String file = "mailLog"+System.currentTimeMillis()+".log"; 
    	FileHandler fh = new FileHandler(file);
    	log.addHandler(fh);
    	
    	//Initialize YahooMail Class
    	YahooMail mail = new YahooMail();
    	// Get Consumer Key and Secret from the Property file. This is app specific.
    	String consumerKey = mail.getAppProps().getProperty("Consumer_key");
    	String consumerSecret = mail.getAppProps().getProperty("Consumer_secret");
        
    	//For command line input
        BufferedReader cmdLineBr = new BufferedReader(new InputStreamReader(System.in));
        String cmdLine;
        boolean Flag = false;
        
    	 // Reading the property file and sorting it
        Map<String,String> jsonPr = (Map) mail.getJsonProps();
        SortedMap<String, String> sortedJsonPr = new TreeMap<String,String>();
        
        List<String> v = new ArrayList<String>(jsonPr.keySet());
        Collections.sort(v);

        for (String str : v) {
            sortedJsonPr.put(str, (String) jsonPr.get(str));
        }
    	
    	// OAuthConsumer and Provider are from SignPost API's 
    	//Initialize OAuth Consumer 
    	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    	
    	//Initialize OAuth Provider with Yahoo OAuth urls
    	OAuthProvider provider = new CommonsHttpOAuthProvider(
                "https://api.login.yahoo.com/oauth/v2/get_request_token",
                "https://api.login.yahoo.com/oauth/v2/get_token" ,
                "https://api.login.yahoo.com/oauth/v2/request_auth");
    	
    	System.out.println("Fetching request token from Yahoo Mail...");
    	//log.info("Fetching request token from Yahoo Mail...");
    	
        // If the APP is client based and you don't have a CallBack_URL then sent "OOB" which is Out Of Bounds
    	// For more information refer 
        String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);
      //  log.info("Now " + authUrl + "\n will be launched in the browser. Please go ahead and grant authorization to this app");
        System.out.println("Now " + authUrl + "\n will be launched in the browser. Please go ahead and grant authorization to this app");
        //Launches the Browser       
        
        BareBonesBrowserLaunch.browse(authUrl);
      //  log.fine("BrowserPage Launched");
        System.out.println("Enter the verification code and hit ENTER when you're done"); 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();

        System.out.println("Fetching access token from Yahoo...");
        provider.retrieveAccessToken(consumer, code);
       
        //Store the access token in a temp string
        
     //   System.out.println("Access token: " + consumer.getToken());
        String oldAccessToken = consumer.getToken();
        
       // System.out.println("Oauth token expires in" +provider.getResponseParameters().getAsHeaderElement("oauth_expires_in"));
        
        for (Iterator i = sortedJsonPr.keySet().iterator(); i.hasNext();) {

            JSONObject jsonObjSend = new JSONObject();

            String key2 = (String) i.next();
            String value2 = (String) sortedJsonPr.get(key2);
            Map<String,String> requestMsgPar = mail.splitter(value2);
            
            //Iterates over each API request from the Property file and executes
            for(Iterator j = requestMsgPar.keySet().iterator(); j.hasNext();){
                    String key = (String) j.next();
                    String value = (String) requestMsgPar.get(key);
                    
                    JSONArray param = new JSONArray(value);
                    jsonObjSend.put("params", param);
                    jsonObjSend.put("method", key);
                    
                    //Create a HTTP Post request
                    
                    HttpPost request = new HttpPost("http://mail.yahooapis.com/ws/mail/v1.1/jsonrpc");
                    request.addHeader("Content-type", "application/json");
                    request.addHeader("Accept", "application/json");
                    StringEntity se;
                    se = new StringEntity(jsonObjSend.toString());
                    request.setEntity(se);
                    //Sign the request 
                    consumer.sign(request);
                    //Constructing an Http Client
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse response = httpClient.execute(request);
                    // JSON object to hold the information, which is sent to the server
                    JSONObject jsonObjRecv = ReadResponse.parseResponse(response);
                    if (jsonObjRecv != null) { 
        		        //Printing out the response
        		      //  System.out.println("JSON output is" +jsonObjRecv.toString(1));
        		        Logger.getLogger("YahooMail").info("JSON output is" +jsonObjRecv.toString(1));
        		    }else {
        		        Logger.getLogger("YahooMail").info("JSON output is null");
        		    }
            }
        }
        
        // Developers can also test other API's
        // using the cmd line
    
        while (!Flag){
        	System.out.print("Want to execute more cmd line api's. Type yes/no and press Enter:  ");
        	cmdLine = cmdLineBr.readLine();
    	
        	if (cmdLine.equalsIgnoreCase("yes")){
        		cmdLineExecuteCascadeApi(consumer, provider, oldAccessToken);
        	}else if (cmdLine.equalsIgnoreCase("no")) {
        		Flag = true;
        		System.out.println("Thanks for trying Yahoo Mail api's");
        	}
        }
        fh.close();
    }

	private static void cmdLineExecuteCascadeApi(OAuthConsumer consumer, OAuthProvider provider, String oldAccessToken) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthException, OAuthCommunicationException {
		
		if (provider.getResponseParameters().getAsHeaderElement("oauth_expires_in") == "0"){
        	provider.retrieveAccessToken(consumer, oldAccessToken);
        }
        
        JSONObject jsonObjSend1 = new JSONObject();
        try {
	        System.out.println("Enter the method: ");
	        BufferedReader bsr1 = new BufferedReader(new InputStreamReader(System.in));        
	        String key = bsr1.readLine() ;
	        System.out.println("\nEnter the params: ");
	        BufferedReader bsr2 = new BufferedReader(new InputStreamReader(System.in));
	        String param = bsr2.readLine() ;
	        
	       
	       // if (("").equals(key) || ("").equals(param)){
	        if (key.length() == 0 || param.length() == 0){
	        	System.out.print("Either Method or its parameters are not entered. Quitting ");
	        	System.exit(0);
	        }else {
		        	//Command Line Input 
		        JSONArray params = new JSONArray(param);
		        jsonObjSend1.put("params", params);
		        jsonObjSend1.put("method", key);
		        
		        // Create a HTTP Post request.
		        
		        HttpPost request = new HttpPost("http://mail.yahooapis.com/ws/mail/v1.1/jsonrpc");
		        request.addHeader("Content-type", "application/json");
		        request.addHeader("Accept", "application/json");
		        StringEntity se;
		        se = new StringEntity(jsonObjSend1.toString());
		        request.setEntity(se);
		        //Sign the request 
		        consumer.sign(request);
		        //Constructing an Http Client
		        HttpClient httpClient = new DefaultHttpClient();
		        HttpResponse response = httpClient.execute(request);
		        // JSON object to hold the information, which is sent to the server
		        JSONObject jsonObjRecv = ReadResponse.parseResponse(response);
		        if (jsonObjRecv != null) { 
		        //Printing out the response
		        	System.out.println("JSON output is" +jsonObjRecv.toString(1));
		        	Logger.getLogger("YahooMail").info("JSON output is" +jsonObjRecv.toString(1));
		        }else {
		        	Logger.getLogger("YahooMail").info("JSON output is null");
		        }
		    }
		}catch (Exception e ){
        	e.printStackTrace();
        }
	}
}
