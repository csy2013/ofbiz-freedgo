Authentication Module
==============

I wanted to start building some common and useful building blocks for creating sites.  First up would be something with authentication and
user logins.  So here we have an authentication module that allows for 3rd party based logins.

Long story short, you can use this to authenticate the user through places like Twitter, Facebook, Google+, and Mozilla's Persona.

You will need to copy and edit the "src/main/resources/authenticationserviceconfiguration.properties.sample" to "src/main/resources/authenticationserviceconfiguration.properties" and fill in the proper "appid" and "appsecret" values for each service you want to enable.  This will require a bit of diligence on your part to go to the service websites, register an app, and harvest the proper values.

* Twitter: https://dev.twitter.com/ (Tip: You *must* have the CallbackURL value filled.  Just put in any valid URL.  It'll be overwritten anyway)
* Facebook: https://developers.facebook.com/apps
* Google: https://code.google.com/apis/console/
* Persona: none

During that process, you can tell the application that your request will be coming from http://localhost:8080/authentication/ as that's the default coded into the "src/main/resources/authenticationconfiguration.properties" file.  You can change that as you wish though.

Once you have that information in place, you can change the .enabled for each service used to "true" and that should be all the setup you need.

This is my first crack at using maven, so YMMV, but if everything is setup right and I modified everything properly, you should be able to run the the sample application by running:

mvn tomcat7:run

and then load up [http://localhost:8080/authentication/](http://localhost:8080/authentication/) (or whatever you set it to).

Let me know it if works :)


Maven Repository
-------------------
For now, this repository is hosted on GitHub as:

    <repositories>
      <repository>
        <id>com.subdigit.authentication</id>
        <url>https://raw.github.com/subdigit/authentication/mvn-repo/</url>
        <snapshots>
          <enabled>true</enabled>
          <updatePolicy>always</updatePolicy>
        </snapshots>
      </repository>
    </repositories>

and it will need to also load in classes from the [Core Module](https://github.com/subdigit/core).
