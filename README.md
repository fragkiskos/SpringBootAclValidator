# SpringBootAclValidator
This is a spring boot module which offers acl validation to your rest API


## Validation Supported Types ##

### Request parameters ###
* Long
* String
* Collection&lt;Long&gt;
* Collection&lt;String&gt;

### Path parameters ###
* Long
* String

### Request body(only json support) ###
* Long
* String
* Collection&lt;Long&gt;
* Collection&lt;String&gt;
* String(JsonObject)
* Collection&lt;String&gt;(JsonObjects)
* String(JsonArray)
* Map
* CustomObject
* Collection&lt;CustomObject&gt;

### Response body(only json support) ###
* Long
* String
* Collection&lt;Long&gt;
* Collection&lt;String&gt;
* String(JsonObject)
* Collection&lt;String&gt;(JsonObjects)
* String(JsonArray)
* Map
* CustomObject
* Collection&lt;CustomObject&gt;
* all of the above types wrapped in ResponseEntity
