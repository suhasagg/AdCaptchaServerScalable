Applications of Captcha Server / Adserver (Hybrid) Supporting User input, (basic user interaction tracking also supported - impression + click tracking) , highly scalable cache based) with a purpose.
Applications include -

1)Bot pattern detection/ Distributed Denial of Service/ Intrusion Detection

2)Authentication before user comment - 

i)Ensures quality of comment data (Users who post geniune content with proper context) 

ii)Malware/bogus comments based Noise removal / Semantic check ML machines are not able to ensure good context in comment based on News Article context.(Comment IQ)

iii)Monetization channel from different content agencies (User feedback loop, direct Authenticated interaction between user and content agency providing content)

iv)User reward system for users who have good post frequency and good quality comments (Reward Coupons based Creatives demoed), also boosts SSO logins and more user participation). DMP integration detects all user patterns like loyalty nature, different domain user personas for better personalization.

v)Refined comments data available for ML Models for quality sentiment analysis.

vi)Feature to collect emotion data for content and added optional Authentication Layer before user emoji input to ensure good quality emotion data which can be used as input to train ML models removing junk patterns and quality predictions using human computation.

vii)Rewards based on user persona data currency.

A win - win senario for publishers + advertisers + content agencies.

System is fully functional with full scale testing.

Some integrations which are to be done -

1)Elasticsearch based data store integration due..


2)ML models integration due..

Will Fetch Persona data from First party Local Storage based on Persona Connect Documentation submitted.

3)Fetches User Persona Data from Data Management Platform for optimising Creative recommendation - images,videos,news articles and other media based Creative.

Feature Set - 

1)Creative Selection based on User Persona


2)Color customisation of creatives/media

We can make color recommendations for the media based on color persona
Our color affinity modules takes into account lot of parameters like segment, demographic affinities.

Optimum media lead to better persona match thus, leading to more engagement and better user input data which is of interest to advertisers and other content providing companies.

2)Large number of segments are available in persona based on different domains.

1)Location


2)Platform Based


3)Time Unit


4)Behavioral Segments


5)Topic Segments


```Publisher Analytic Segments```


```Engagement Time Based Segments```


1)Segment Engagement Times

2)Topic Engagement Times

3)Section Engagement Times


```Session Based Segments```


1)Session count

2)Time Since Last Session

3)Session Engagement Time

4)Loyalty Nature

5)Visitor Count



```Machine Learning based Segments```


This covers different domains Personas

1)Emotion Persona


2)Affinities based on Creative Persona


3)Refined Demographic Persona


4)Affinities of Topic and Segments present in User Persona with respect to Media Object segments.




```Deployment Configuration```

----------------------------------------------------------------------------
????????????????????????????????????????????????????????????????????

Description -

Test images contains static images generated by Image Generation APIs (Automatic Creative Generation based on Predefined Templates for Dynamic Creative Optimization) 


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


![image](https://i.ibb.co/rk5jjxM/39.jpg)


![image](https://i.ibb.co/dQzsK60/28.jpg)


![image](https://i.ibb.co/Yyvdk4D/27.jpg)


![image](https://i.ibb.co/DYpcWYf/12.jpg)


![image](https://i.ibb.co/TqgCk4w/10.jpg)


![image](https://i.ibb.co/sqWLcdt/8.jpg)


![image](https://i.ibb.co/sFKMZLV/42.gif)


![image](https://i.ibb.co/stjsBJz/41.gif)



![image](https://i.ibb.co/HFmxwn8/48.gif)



![image](https://i.ibb.co/GdVV2Dy/44.gif)



![image](https://i.ibb.co/hKGHfP1/43.gif)



![image](https://i.ibb.co/fNnk7bf/45.gif)



![image](https://i.ibb.co/DpwLN1g/47.gif)



![image](https://i.ibb.co/1zJPYPN/46.gif)

--------------------------------------------------------------------------------------

Demo Configuration -


-------------------------------------------------------------------------------------------

URL-
http://localhost:8080/captcha/



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



Validate API- (For predefined answers, validity check can be made graceful (based on junk filter only) to take custom user input)

2)http://localhost:8080/captcha/validate?ip=127.0.0.1&apikey=6d5fc3482be490727686de4a5c1d45b8995dfe9f&code=v6RM&key=c22cc4f9d7946b8383e30e1e0620d2dec407c96a


Sample response in plain/text- 

true



Set up Captcha Server Cluster -


For setting up Captcha Server Cluster, 


.war deployed in different Tomcat for cluster setup should point to same memcached servers.



Ehcache support has been added.

-----------------------------------------------------------------------------------------------


Analytics Panel -


Captcha Analytics will be stored in AnalyticReportfile.txt
File is created in D:/ drive.
All the reports are ingested in Elasticsearch for data visualisation and custom dimensions report.
Collected data points are ingested in data lake for feature interaction modelling with user data points collected via other channels - website page interaction, mobile app interaction, campaign interaction.
Data points go through regular processing and enhancement process using third party databases like Maxmind, Wurfl, Semantic Engine.

Format of file is -
<image_id>#<Captcha Validation Response>#<timestamp>

Database support has been added

----------------------------------------------------------------------------------------------

Reference -
https://bitbucket.org/sunng/yan
  
?? 2021 GitHub, Inc.
