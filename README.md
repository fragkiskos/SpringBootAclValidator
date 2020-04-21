# SpringBootAclValidator
This is a spring boot module which offers acl validation to your rest API


## Validation Supported Types ##

# Request parameters #
* Long
* String
* Collection<Long>
* Collection<String>

# Path parameters #
* Long
* String

# Request body(only json support) #
* Long
* String
* Collection<Long>
* Collection<String>
* String(JsonObject)
* Collection<String>(JsonObjects)
* String(JsonArray)
* Map
* CustomObject
* Collection<CustomObject>

# Response body(only json support) #
* Long
* String
* Collection<Long>
* Collection<String>
* String(JsonObject)
* Collection<String>(JsonObjects)
* String(JsonArray)
* Map
* CustomObject
* Collection<CustomObject>
* all of the above types wrapped in ResponseEntity
