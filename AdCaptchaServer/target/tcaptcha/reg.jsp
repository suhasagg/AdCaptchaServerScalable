<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register an API_KEY for Times Captcha Server</title>
<script type="text/javascript" src="script/mootools-1.2.3-core-yc.js"></script>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div id="page">
        <div id="header">
            <h1>Times Captcha Server</h1>
        </div>
        <div id="pagebody">
            <div>
                AppName: <input type="text" id="appname" size="24" /> <button onclick="retriveKey()">Submit</button>
            </div>
            <div id="result"> </div>
        </div>
        

        <script type="text/javascript">
            function retriveKey(){
                var appname = $('appname').value;
                var request = new Request({url:"reg"});

                request.onSuccess = showApiKey;

                request.send("appname="+appname);
                $('result').set("html", "loading ...");
                $('appname').set("value", "");
            }

            function showApiKey(response){
                $('result').set('html', 'Your API Key: <em>'+response+'</em>');
            }
        </script>
    </div>
</body>
</html>