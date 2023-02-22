# How to enable security using KeyCloak

## Configure KeyCloak

* run Key Cloak server
* create a "Realm"
    * Realm is a logical group to hold details about one thing/app
* create a Client in this Realm
    * client type = OpenID Connect
    * client id = an id for this client
    * client authentication = ON
        * This defines the type of the OIDC client. When it's ON, the OIDC type is set to confidential access type. When it's OFF, it is set to public access type
* Authentication Flow = Service account roles
    * In terms of OAuth2 specification, this enables support of 'Client Credentials Grant' for this client.

## Use postman for testing

* Go to "Authorization" tab in postman
* Select Type = OAuth2
* Token Type = Access Token
* Header Prefix = Bearer
* Configuration
    * Token Name = [any name for this token]
    * Grant Type = Client Credentials
    * Access Token URL = [get url from : Key Cloak -> Realm settings -> token_endpoint]
    * Client ID = [use above created client's ID]
    * Client Secret = [use above created client's credentials]
    * Client Authentication = Send as Basic Auth header

* Click "Get New Access Token"
    * A new token will generate by accessing given url, using given client credentials. this token will be send in the header as the bearer token.
* send the request to server

