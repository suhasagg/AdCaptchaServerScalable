Deployment Configuration -

----------------------------------------------------------------------------


Description -

Test images contains static images generated by Image Generation APIs. 


2)captcha.war is to be deployed in Servlet container.


3)Memcached is to be started at 127.0.0.1:11211


There are four modes of Captcha -

0-Advertisement Captcha

1-Math Captcha

2-Advertisement Captcha with hint

3-Plain/Text Captcha 

![image](https://i.ibb.co/RjkcsSD/example1.png)
![image](https://i.ibb.co/jkf3mZw/example2.png)
![image](https://i.ibb.co/vPDy6fc/example3.png)
![image](https://i.ibb.co/Gv3fh4W/example4.png)
![image](https://i.ibb.co/C1jXLvG/example5.png)
![image](https://i.ibb.co/xJpG5KC/example6.png)
![image](https://i.ibb.co/qCZ61ZT/example7.png)

--------------------------------------------------------------------------------------

Demo Configuration -


-------------------------------------------------------------------------------------------

URL-
http://localhost:8080/tcaptcha/



Registration for API Key api -
http://localhost:8080/captcha/reg?appname=app1


Sample response-
<string>6d5fc3482be490727686de4a5c1d45b8995dfe9f</string>



System can be demoed via index.jsp interface.



Individual Apis -


Ticket API - 
1)http://localhost:8080/captcha/ticket?ip=127.0.0.1&apikey=6d5fc3482be490727686de4a5c1d45b8995dfe9f&alt=json&mode=3

Response can also be obtained in xml by changing alt parameter=xml

Sample response -

{"code":"V6RM","hint":"","key":"c22cc4f9d7946b8383e30e1e0620d2dec407c96a","type":"text/plain","url":"http://localhost:8080/captcha/captcha/c22cc4f9d7946b8383e30e1e0620d2dec407c96a"}



Captcha Url -
http://localhost:8080/captcha/captcha/c22cc4f9d7946b8383e30e1e0620d2dec407c96a

Response corresponding to Captcha url will be a jpeg/png image in case of mode - 0 and mode - 2
In case of mode 1 and mode 3 response will be plain/text 



Validate API-

2)http://localhost:8080/captcha/validate?ip=127.0.0.1&apikey=6d5fc3482be490727686de4a5c1d45b8995dfe9f&code=v6RM&key=c22cc4f9d7946b8383e30e1e0620d2dec407c96a


Sample response in plain/text- 

true



Set up Captcha Server Cluster -


For setting up Captcha Server Cluster, 


.war deployed in different Tomcat for cluster setup should point to same memcached servers.



Ehcache support has been added.

-----------------------------------------------------------------------------------------------


Analytics Panel --


Captcha Analytics will be stored in AnalyticReportfile.txt
File is created in D:/ drive.


Format of file is -
<image_id>#<Captcha Validation Response>#<timestamp>

Database support has been added

----------------------------------------------------------------------------------------------

Reference -
https://bitbucket.org/sunng/yan
  
© 2021 GitHub, Inc.
