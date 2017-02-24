
# Java Angular (jAngular) Standard Webapp Template

This application template consists of a Java backend coupled with an Angular UI.

The Java portion is built around Spring and Spring Security.  The REST / RPC functionality is provided using Spring MVC.
The UI client is constructed with Angular and Bootstrap.

The template consists of a set of maven modules: common, client, webapp and distribution

| Module  | Content |
| ------------- | --------------------------------- |
| common  | shared Java code used by other modules  |
| client  | Angular UI code  |
| webapp  | web application, client code is included in the war generated  |
| distribution  | a simple installable distribution which contains the war but could also contain command line scripts |


### Features

* Uses Spring 4 with Java based configuration (not XML) (see WebapInitializer)
* Uses Spring Security 2.3.x with Java based configuration (see SecurityConfig)
* REST / RPC functionality provided via Spring MVC (look for @Controller annotations)
* Logging is implemented with SLF4J and Logback.
* Version information is generated into the Java code with Maven, including: maven version, git commit and build timestamp.  See VersionService.
* Includes metrics support with Codahale (http://metrics.codahale.com/), see MetricRegistryService and CommonController.

### Notes

* The webapp module is configured to use Jetty, use run.sh or run.bat in webapp module to start Jetty
* The webapp context is /jAngular, so to go to the running web application use: http://localhost:8080/jAngular
* To change to webapp context config edit the webapp pom.xml, search for contextPath.
* SSL is also configured so https://localhost:8080/jAngular is also valid.  Replaced self-signed SSL certificate for production.

### Security Notes

* To login into to the application use any username where the password is also the username, this is obviously for development only.
Reimplement UserDetailsServiceImpl for production
* The client contains some code that does not require authentication: /lib and /unsecured.  lib contains third-party js libraries and unsecured
currently contains login code.
* On the server side the only unsecured url is /jAngular/login where the login form data is POSTed for authentication.
* The login is currently implemented via a form post, this could also be done using AJAX but requires some tricks to get around
Angular $http limitations.  See http://stackoverflow.com/questions/11442632/how-can-i-make-angular-js-post-data-as-form-data-instead-of-a-request-payload
* When developing REST / RPC calls use POST for any calls that change server-side data to defeat cross site request forgery (CSRF) attacks.
* Angular also uses a strategy to prevent CSRF by including a non-parseble Javascript prefix in front of JSON returned by the server.
See the AngularJsonMessageConverter class
* Logout is done via AJAX with the Spring Security logout success handler overridden to return 202 instead of doing a redirect, this
simplifies handling in Angular.
