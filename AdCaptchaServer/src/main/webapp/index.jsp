<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>


<head>
	<title>Times Captcha Server</title>
	<script type="text/javascript" src="script/mootools-1.2.3-core-yc.js"></script>
	<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div id="page">
        <div id="header">
            <h1>Times Captcha Service</h1>
        </div>

        <div id="pagebody">
            <div class="paramsArea">
                <h2>Required Parameters</h2>
                <table id="formTable">
                    <tr>
                        <td>Mode</td>
                        <td><select id="mode" name="mode" class="keyreq">
                                <option value="0">Advertisement Captcha</option>
                                <option value="1">Math Captcha</option>
                                <option value="2">Advertisement Captcha With Hint</option>
                                <option value="3">Plain Text Captcha</option>
                            </select></td>
                    </tr>
                    <tr>
                        <td>IP</td>
                        <td><input type="text" id="ip" value="127.0.0.1" name="ip" size="16" class="keyreq vrfreq" /></td>
                    </tr>
                    <tr>
                        <td>ApiKey</td>
                        <td><input type="text" id="apikey" value="" name="apikey" size="40" class="keyreq vrfreq" /></td>
                    </tr>
                </table>
            </div>
            
            <div class="paramsArea">
                <h2>Configuration Parameters <button onclick="appendRow()">+</button></h2>
                <ul id="configTable">
                    <li><input type="text" size="10" /> : <input type="text" size="10" /></li>
                    <li><input type="text" size="10" /> : <input type="text" size="10" /></li>
                    <li><input type="text" size="10" /> : <input type="text" size="10" /></li>
                    <li><input type="text" size="10" /> : <input type="text" size="10" /></li>
                </ul>
            </div>
            
            <div id="operationPane">
	            <div><button onclick="gen();">GetURL</button> <span id="capturl"></span> | <span id="mimetype"></span></div>
                <div><button onclick="view();" id="imgbtn" disabled="disabled">GetCaptcha</button></div>
	            <div id="capt"></div>
	            <div id="hint"> </div>
	            <div id="verifypanel">
	                <input type="text" size="10" name="code" id="code" class="vrfreq" />
	                <input type="hidden" name="key" id="key" value="" class="vrfreq" />
	                <button id="vrfbtn" onclick="verify();" disabled="disabled">Verify</button>
	                <span id="resultlabel">&nbsp;&nbsp;&nbsp;&nbsp;</span>
	            </div>
            </div>
        </div>

       

        <script type="text/javascript">
            function gen(){
                var params = [];
                $$("input.keyreq", "textarea.keyreq", "select.keyreq").each(function(i){
                    var key = i.name;
                    if(i.tagName == "select"){
                        var value = i.options[i.selectedIndex].value;
                    }else{
                        var value = encodeURI(i.value);
                    }
                    params.push(key+"="+value);
                });
                $("configTable").getChildren("li").each(function(it){
                    var inputs =  it.getChildren("input");
                    if(inputs[0].get("value").trim().length > 0){
                        var key = inputs[0].get("value").trim();
                        var value = inputs[1].get("value").trim();                        
                    	params.push(key+"="+value);
                    }
                    
                });
                var req = new Request({
                    url: "ticket"
                });
                req.onSuccess=function(responseText){
                    gen_callback(responseText);
                };
                req.send(params.join("&"));
            }

            function gen_callback(data){
                var d = data.split("\n");
                $('capturl').set("text", d[0]);
                $('mimetype').set("text", d[1]);
                $('key').set("value", d[2]);
                $('hint').set("text", d[4]);
                
                $('imgbtn').set("disabled", false);
                $('vrfbtn').set("disabled", true);
                
                $('resultlabel').setStyle("backgroundColor", "transparent");
                $('code').set("value", "");
            }

            function view(){
                $('capt').empty();
                if(/^image/.test($('mimetype').get("text"))){
                    var image = new Element("img", {
                        "src": $('capturl').get("text")
                    });
                    $('capt').grab(image);
                    
                }else if(/^text/.test($('mimetype').get("text"))){
                	var req = new Request({
                        url: $('capturl').get("text"),
                        method: "GET"
                    });
                    req.onSuccess=function(responseText){
                    	$('capt').set("text", responseText);
                    };
                    req.send();
                }
                $('imgbtn').set("disabled", true);
                $('vrfbtn').set("disabled", false);
            }

            function verify(){
	                    var params = [];
	                    $$("input.vrfreq").each(function(i){
	                        var key = i.name;
	                        var value = i.value;
	                        value = value.replace(/(\r\n|\n|\r)/gm,"");
	                        params.push(key+"="+value);
	                    });
	                    var req = new Request({
	                        url: "validate"
	                    });
	                    req.onSuccess=function(responseText){
	                        vrf_callback(responseText);
	                    };
	                    req.send(params.join("&"));
	                }
	    
	                function vrf_callback(result){
	                    $('vrfbtn').set("disabled", true);
	                    if(result == 'true'){
	                        $('resultlabel').setStyle("background-color", "green");
	                    }else{
	                        $('resultlabel').setStyle("background-color", "red");
	                    }
            }
            
            
            function appendRow(){
                var node = $$('#configTable li')[0].cloneNode(true);
                node.getChildren("input").set("value", "");
                $('configTable').grab(node);
            }

        </script>
    </div>
</body>
</html>
