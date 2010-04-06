
This example helps you to quickly checkout Ymail API's(YMWS) using OAuth Authorization. 
It uses SignPost OAuth API. For more on the API refer http://code.google.com/p/oauth-signpost/

To get your OAuth keys go to https://developer.apps.yahoo.com/dashboard/createKey.html 

Steps to Run the example

1. Unzip the zip file which contains a jar file and the Properties folder
2. The jar file reads app.properties and test.properties from the Properties folder. 
3. Modify the app.properties file to include your consumer key and consumer secret 
4. Assuming you are in the unzipped folder, run the jar file
   #java -jar YmailApi.jar
 
5. This will launch a Browser which will ask your app's end users to authorize this app. 
   The end user should give his/her yahoo mail id to authorize your app. Ideally if there is a callback url
   set then Yahoo will return the "code" to this URL.Once authorized depending on your consumer key you can 
   have access to the Ymail API's run on end user's mailbox. In this example we have the callback url set to "OOB". 
   Hence copy the "code" from the browser to the command line. 
 
6. Once you have entered the code in the command line and press Enter , you can see the YMWS response to API's which are in test.properties.

7. If you want to try out more API's, you can use the command line option for the same.

8. All the output is logged in a file created with a timestamp during runtime. If you need to check your 
   previous output please use this file.

For example

Want to execute more api's in command line . Type yes/no and press Enter: yes
Enter the method: 
ListMessages

Enter the params: 
[{"fid":"Inbox","numInfo":25,"numMid":25,"sortKey":"date","sortOrder":"up","groupBy":"unRead"}]

For source download
http://github.com/ramiyer/YahooMailApiSample


