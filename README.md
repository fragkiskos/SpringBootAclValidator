# SpringBootAclValidator
This is a spring boot module which offers acl validation to your rest API


## Validation Supported Types ##

### Request parameters ###
* Long (consider the long as id for acl validation)
* String (consider the String as id for acl validation)
* Collection&lt;Long&gt; (consider the longs as ids for acl validation)
* Collection&lt;String&gt; (consider the strings as ids for acl validation)

### Path parameters ###
* Long (consider the long as id for acl validation)
* String (consider the String as id for acl validation)

### Request body(only json support) ###
* Long (consider the long as id for acl validation)
* String (consider the String as id for acl validation)
* Collection&lt;Long&gt; (consider the longs as ids for acl validation)
* Collection&lt;String&gt; (consider the strings as ids for acl validation)
* String(JsonObject) (trying to find property "id" as id for acl validation)
* Collection&lt;String&gt;(JsonObjects) (trying to find property "id" for every object as the ids for acl validation)
* String(JsonArray)(trying to find property "id" for every object as the ids for acl validation)
* Map (trying to find property "id" as id for acl validation)
* CustomObject (trying to find public method getId() as id for acl validation)
* Collection&lt;CustomObject&gt; (trying to find public method getId() for every object as ids for acl validation)

### Response body(only json support) ###
* Long (consider the long as id for acl validation)
* String (consider the String as id for acl validation)
* Collection&lt;Long&gt; (consider the longs as ids for acl validation)
* Collection&lt;String&gt; (consider the strings as ids for acl validation)
* String(JsonObject) (trying to find property "id" as id for acl validation)
* Collection&lt;String&gt;(JsonObjects) (trying to find property "id" for every object as the ids for acl validation)
* String(JsonArray) (trying to find property "id" for every object as the ids for acl validation)
* Map (trying to find property "id" as id for acl validation)
* CustomObject (trying to find public method getId() as id for acl validation)
* Collection&lt;CustomObject&gt; (trying to find public method getId() for every object as ids for acl validation)
* all of the above types wrapped in ResponseEntity
