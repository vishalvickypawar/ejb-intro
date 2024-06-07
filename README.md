// At the start, confirm the Java and Maven versions from the shell/terminal
// Make sure you have JDK 9-13 (The new version of Wildfly does not work with JDK 14+)
java --version

mvn --version


// Download the Wildfly Application server:
https://www.wildfly.org/downloads/

// Unpack the zip file in a folder (let's say, /Users/loonycorn/tools)
// The path to the Widlfly directory will be something like this:
/Users/loonycorn/tools/wildfly-23.0.1.Final

// Set the JBOSS_HOME environment directory in your bash profile
// Open up the file /Users/loonycorn/.bash_profile and add the following line:
export JBOSS_HOME="/Users/kishan/tools/wildfly-23.0.1.Final"

// Save the bash profile and open up a new Terminal/shell
// Verify the environment variable 
echo $JBOSS_HOME

// Navigate to the bin directory of Wildfly
cd $JBOSS_HOME/bin

ls -l

// Run the standalone.sh script to start up Wildfly server
./standalone.sh

// Open up a browser and navigate to this URL - You can see the Wildfly page
http://127.0.0.1:8080

// Click on the link to the Administration Console. This will take you to port 9990
http://127.0.0.1:9990

// You will need to activate the admin user to access the console
// Open up a new Terminal/shell tab and navigate to the Wildfly bin folder 
cd $JBOSS_HOME/bin

// Run the add user script
./add-user.sh

// Type a for Management User 
// When prompted for the username, type in: admin
// Choose to activate the user
// Set the password to something you will remember, e.g. loonycorn1@

// Head to the browser and login with the admin credentials
http://127.0.0.1:9990

// You will be navigated to the admin dashboard 
// In the Deployments section, click on Start 
// The deployments menu on the left will be empty - refresh to confirm

// Over to IntelliJ, create a new Maven project using the maven-archetype-quickstart archetype
// Set the groupId of the project to com.loonycorn.server
// The artifactId can be ejb-intro

// Once the project is created, remove the test directory and the App.java source in the main folder
// Replace the POM file contents with this
// Set the JDK version according to what you have on your system - this assumes JDK 9

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.skillsoft.ejb</groupId>
  <artifactId>ejb-intro</artifactId>
  <version>1.0</version>
  <packaging>ejb</packaging>

  <name>EJB</name>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <endorsed.dir>${project.build.directory}/endorsed</endorsed.dir>
    <maven.compiler.source>9</maven.compiler.source>
    <maven.compiler.target>9</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>javax</groupId>
      <artifactId>javaee-api</artifactId>
      <version>8.0.1</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>


  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.wildfly.plugins</groupId>
          <artifactId>wildfly-maven-plugin</artifactId>
          <version>2.0.1.Final</version>
          <configuration>
            <hostname>127.0.0.1</hostname>
            <port>9990</port>
          </configuration>
        </plugin>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-ejb-plugin</artifactId>
          <version>2.3</version>
          <configuration>
            <ejbVersion>3.2</ejbVersion>
          </configuration>
        </plugin>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-dependency-plugin</artifactId>
          <version>2.1</version>
          <executions>
            <execution>
              <phase>validate</phase>
              <goals>
                <goal>copy</goal>
              </goals>
              <configuration>
                <outputDirectory>${endorsed.dir}</outputDirectory>
                <silent>true</silent>
                <artifactItems>
                  <artifactItem>
                    <groupId>javax</groupId>
                    <artifactId>javaee-endorsed-api</artifactId>
                    <version>7.0</version>
                    <type>jar</type>
                  </artifactItem>
                </artifactItems>
              </configuration>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>

</project>


// In the com.loonycorn.server package, create an EJB Interface called BeanIntroInterface
// Paste in this code

package com.loonycorn;

import javax.ejb.Remote;

@Remote
public interface BeanIntroInterface {
    String getMessage();
}

// In the same package create a BeanIntroImplementation class with this code

package com.loonycorn;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless(name = "FirstBean")
public class BeanIntroImplementation implements BeanIntroInterface {

    @Resource
    private SessionContext context;

    @Override
    public String getMessage() {
        return "Welcome, kid, to the world of EJB!";
    }
}

// Build and package the project
// Bring up the terminal in IntelliJ, and from the directory which contains the POM, run this
mvn clean package

// A target directory will be created which you can expand in the IntelliJ GUI and confirm the 
// creation of the ejb-intro-1.0.jar

// Deploy the EJB jar to Wildfly
mvn install wildfly:deploy

// Bring up the terminal where you started up Wildfly by running standalone.sh
// You should see a message confirming the deployment and some JNDI mappings

// Head to the Wildfly web UI in the browser and reload the deployments pane
// ejb-intro-1.0.jar should now appear - expand it to confirm the contents


// Simulate the distribution of the server EJB Jar file
// Create a copy of ejb-intro-1.0.jar and place it in some directory where it can be accessed by a client
// e.g. /Users/loonycorn/lib


// ****************************************************************************************** //


// Building the EJB Client

// Create a new Maven project for the client (open in a new IntelliJ window) 
// Set the groupId of the project to com.loonycorn.client
// The artifactId can be ejb-client

// Just as with the server app, delete the test folder and the App.java in the main folder
// Use this POM file

<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.loonycorn</groupId>
  <artifactId>ejb-client</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>EJB Client</name>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>9</maven.compiler.source>
    <maven.compiler.target>9</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.wildfly</groupId>
      <artifactId>wildfly-client-all</artifactId>
      <version>23.0.1.Final</version>
    </dependency>
  </dependencies>

  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>

      </plugins>
    </pluginManagement>
  </build>
</project>


// If you're on IntelliJ, add the EJB server Jar file to your project path
// Go to File --> Project Structure -> Library and hit the + button to add a new library 
// Navigate to the "distributed" copy of the EJB server Jar (e.g. in /Users/loonycorn/lib)
// and add it to the client project

// Once you add it, expand the External Libraries menu in the Project pane of your client project
// The ejb-intro-1.0.jar file should appear

// In the com.loonycorn.client package, create a class called AccessBean and paste in this code

package com.loonycorn;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;


public class AccessBean
{
    public static void main( String[] args )
    {

        try{
            final Hashtable jndiProperties = new Hashtable();

            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
            Context context = new InitialContext(jndiProperties);

            String appName = "";
            String moduleName = "ejb-intro-1.0";
            String distinctName = "";
            String beanName = "FirstBean";
            String interfaceName = BeanIntroInterface.class.getName();

            String name = String.format("ejb:%s/%s/%s/%s!%s",
                    appName, moduleName, distinctName, beanName, interfaceName);

            System.out.println(name);
            BeanIntroInterface bean = (BeanIntroInterface)context.lookup(name);
            String result = bean.getMessage();
            System.out.println("Result computed by EJB is : " + result);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}


// Run the above program - it should return the message


// Here are explanations for the different fields used in JNDI:


String appName = "";
// The app name is the application name of the deployed EJBs. This is typically the ear name
// without the .ear suffix. However, the application name could be overridden in the application.xml of the
// EJB deployment on the server.
// Since we haven't deployed the application as a .ear, the app name for us will be an empty string

String moduleName = "ejb-intro-1.0";
// This is the module name of the deployed EJBs on the server. This is typically the jar name of the
// EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
// In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
// jboss-as-ejb-remote-app
          
String distinctName = "";
// AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
// our EJB deployment, so this is an empty string

String beanName = "FirstBean";          
// The EJB name which by default is the simple class name of the bean implementation class
          
String interfaceName = BeanIntroInterface.class.getName();
// the remote view fully qualified class name





SessionBeans.java
------------------

1. Stateless Session Beans


// Update the BeanIntroInterface

Interface BeanIntroInterface.java

	import javax.ejb.Remote;

	@Remote
	public interface BeanIntroInterface {
	    String getMessage();
	    void setName(String name);
	}

// Modify the Bean implementation 

Class BeanIntroImplementation.java is as follows:

	import javax.annotation.Resource;
	import javax.ejb.SessionContext;
	import javax.ejb.Stateless;


	@Stateless
	public class BeanIntroImplementation implements BeanIntroInterface{

	    @Resource
	    private SessionContext context;

	    public String name;

	    @Override
	    public String getMessage() {
	        return String.format("Welcome %s, to the world of EJB!", name );
	    }

	    @Override
	    public void setName(String givenName) {
	        name = givenName;
	    }

	}

// Deploy the modified bean to Wildfly

mvn install wildfly:deploy




// On the client side:

// Add the re-built jar to the library in the project structure 

// In the same com.loonycorn package where the AccessClient was created, 
// create a new client source file called StatelessClientOne


// Class StatelessClientOne.java

package com.loonycorn;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class StatelessClientOne {

    public static void main(String[] args) throws Exception {

        invokeStatelessBean();
    }

    private static void invokeStatelessBean() throws Exception {

        BeanIntroInterface statelessBean = lookupRemoteStatelessBean();
        System.out.println("Obtained a remote stateless bean for invocation");

        statelessBean.setName("Loony Client One");
        String message = statelessBean.getMessage();
        System.out.println("Returned message: " + message);

    }


    private static BeanIntroInterface lookupRemoteStatelessBean() throws Exception {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();

        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = BeanIntroImplementation.class.getSimpleName();
        final String viewClassName = BeanIntroInterface.class.getName();

        return (BeanIntroInterface) context.lookup("ejb:" + appName + "/"
                                                    + moduleName + "/" + distinctName + "/"
                                                    + beanName + "!" + viewClassName);
    }
}

// Run the program (hit the play button next to the class name or create a new run config)
// The message with the client name is returned

// On run the Result returned by the server is 
// RESULT: Welcome Loony Client One, to the world of EJB!

// In the same com.loonycorn package create a third client class called StatelessClientTwo


// Class StatelessClientTwo.java 	

package com.loonycorn;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class StatelessClientTwo {

    public static void main(String[] args) throws Exception {

        invokeStatelessBean();
    }

    private static void invokeStatelessBean() throws Exception {

        BeanIntroInterface statelessBean = lookupRemoteStatelessBean();
        System.out.println("Obtained a remote stateless bean for invocation");

        //statelessBean.setName("Loony Client Two");

        String message = statelessBean.getMessage();
        System.out.println("Returned message: " + message);

    }


    private static BeanIntroInterface lookupRemoteStatelessBean() throws Exception {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();

        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = BeanIntroImplementation.class.getSimpleName();
        final String viewClassName = BeanIntroInterface.class.getName();

        return (BeanIntroInterface) context.lookup("ejb:" + appName + "/"
                                                    + moduleName + "/" + distinctName + "/"
                                                    + beanName + "!" + viewClassName
                                                    + "?stateless");
    }
}


// Run the program 
// If the value of "Loony Client Two" is not set then the result will show the 
// "Loony Client One" value as set by the Client One.

// RESULT: Welcome Loony Client One, to the world of EJB!

// We can verify that client-bean instances are pooled. 
// Stateless beans do not provide concurrent client access 
// and are not unique per client.


// Uncomment the setName call in StatelessClientTwo
		statelessBean.setName("Loony Client Two"); 

// Re-run StatelessClientTwo
// RESULT: Welcome Loony Client Two, to the world of EJB!




2. Stateful Beans


// Back to the server-side app

// In the BeanIntroImplementation class, add this import
import javax.ejb.Stateful;

// Change the @Stateless to @Stateful

	@Stateful
	public class BeanIntroImplementation implements BeanIntroInterface{

	    // Rest of the class remains the same

	}

// The interface BeanIntroInterface remains the same



mvn clean 					// to clean the target folder
mvn wildfly:undeploy 		// to remove the version deployed on the server

// View the Wildfly logs. You should see an undeploy message

// Back to the IDE, deploy the modified bean
mvn install wildfly:deploy  	// to create jar and deploy it to the server

// From the Wildfly logs, you will see this JNDI binding
ejb:/ejb-intro-1.0/BeanIntroImplementation!com.loonycorn.BeanIntroInterface?stateful


// On the client side: 
//add the jar to the library in the project structure 

// In the com.loonycorn package, create a new class StatefulClientOne

package com.loonycorn;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class StatefulClientOne {

    public static void main(String[] args) throws Exception {

        invokeStatefulBean();
    }

    private static void invokeStatefulBean() throws Exception {

        BeanIntroInterface statefulRemoteBean = lookupRemoteStatefulBean();
        System.out.println("Obtained a remote stateful bean for invocation");

        statefulRemoteBean.setName("Stateful Client One");
        String message = statefulRemoteBean.getMessage();
        System.out.println("Returned message: " + message);

    }
    private static BeanIntroInterface lookupRemoteStatefulBean() throws Exception {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = BeanIntroImplementation.class.getSimpleName();
        final String viewClassName = BeanIntroInterface.class.getName();

        return (BeanIntroInterface) context.lookup("ejb:" + appName + "/"
                                                    + moduleName + "/" + distinctName + "/"
                                                    + beanName + "!" + viewClassName
                                                    + "?stateful");
    }

}


// Run StatefulClientOne

// Obtained a remote stateful bean for invocation
// Returned message: Welcome Stateful Client One, to the world of EJB!


// Class StatefulClientTwo.java

package com.loonycorn;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class StatefulClientTwo {

    public static void main(String[] args) throws Exception {

        invokeStatefulBean();
    }

    private static void invokeStatefulBean() throws Exception {

        BeanIntroInterface statefulRemoteBean = lookupRemoteStatefulBean();
        System.out.println("Obtained a remote stateful bean for invocation");

        //statefulRemoteBean.setName("Stateful Client Two");
        String message = statefulRemoteBean.getMessage();
        System.out.println("Returned message: " + message);

    }
    private static BeanIntroInterface lookupRemoteStatefulBean() throws Exception {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = BeanIntroImplementation.class.getSimpleName();
        final String viewClassName = BeanIntroInterface.class.getName();

        return (BeanIntroInterface) context.lookup("ejb:" + appName + "/"
									                + moduleName + "/" + distinctName + "/"
									                + beanName + "!" + viewClassName
									                + "?stateful");
    }

}



// On Run 

// Returned message: Welcome null, to the world of EJB!


// We have verified that client-bean instances are 1:1. 
// Stateless beans do not provide concurrent client access BUT ARE unique per client


//Rerun the StatefulClientTwo.java by assigning the name. 
// Uncomment the line to set name. 
		statefulRemoteBean.setName("Stateful Client Two");



// On rerun 

// Returned message: Welcome Stateful Client Two, to the world of EJB!





3. Singleton Beans

// Do not remove any of the previously created classes
// We'll just add a new singleton bean

// Reference:
// https://www.baeldung.com/java-ee-singleton-session-bean



// Interface PetShopBeanInterface

package com.loonycorn;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PetShopBeanInterface {

    List<String> getBreeds(String petType);
    void setBreeds(String petType, List<String> breeds);
}





// Class PetShopBeanImplementation
// The clients will need to manage concurrency - no guarantee is provided by the server

package com.loonycorn;


import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PetShopBeanImplementation implements PetShopBeanInterface{

    private final Map<String, List<String>> petTypeMap = new HashMap<String, List<String>>();

    @PostConstruct
    public void initialize() {

        List<String> dogBreeds = new ArrayList<String>();
        dogBreeds.add("Golden Retriever");
        dogBreeds.add("Labrador");
        dogBreeds.add("German Shepherd");
        dogBreeds.add("Bulldog");
        dogBreeds.add("Beagle");

        petTypeMap.put("Dog", dogBreeds);
    }

    @Override
    public List<String> getBreeds(String petType) {
        return petTypeMap.get(petType);
    }

    @Override
    public void setBreeds(String petType, List<String> breeds) {
        petTypeMap.put(petType, breeds);
    }

}

// Deploy the updated bean

mvn clean
mvn wildfly:undeploy 
mvn install wildfly:deploy 

// Head to the Wildfly logs to confirm the deployment
// You'll observe that both the old BeanIntroImplementation and the 
// PetShopBeanImplementations are now available 


/// Head to the client-side app

// in the com.loonycorn package, create a new bean called PetShopClientBean


package com.loonycorn;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class PetShopClientBean {

    public static void main(String[] args) throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = PetShopBeanImplementation.class.getSimpleName();
        final String viewClassName = PetShopBeanInterface.class.getName();

        String jndi = "ejb:" + appName + "/"
                        + moduleName + "/" + distinctName + "/"
                        + beanName + "!" + viewClassName;

        PetShopBeanInterface petTypeBean = (PetShopBeanInterface) context.lookup(jndi);
        List<String> petTypes = petTypeBean.getBreeds("Dog");
        System.out.println("Dog breeds: " + petTypes);

        String[] catTypes = { "Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal" };

        petTypeBean.setBreeds(
                "Cat", Arrays.asList(catTypes));

        petTypes = petTypeBean.getBreeds("Cat");
        System.out.println("Cat breeds: " + petTypes);
    }
}


// Run the program - the dog breeds are returned and so are the cat breeds

// Dog breeds: [Golden Retriever, Labrador, German Shepherd, Bulldog, Beagle]
// Cat breeds: [Siamese, Persian, Maine Coon, Ragdoll, Bengal]


// Explicitly set concurrency at the server side
// Back to the server app, change the PetShopBeanImplementation class

// Note that the only changes below are:
// Change to @ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
// Add dogBreeds.add("Indie"); in the initialize method
// Add @Lock(LockType.READ) next to the getBreeds() method
// Add @Lock(LockType.WRITE) next to setBreeds()



package com.loonycorn;


import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class PetShopBeanImplementation implements PetShopBeanInterface{

    private final Map<String, List<String>> petTypeMap = new HashMap<String, List<String>>();

    @PostConstruct
    public void initialize() {

        List<String> dogBreeds = new ArrayList<String>();
        dogBreeds.add("Golden Retriever");
        dogBreeds.add("Labrador");
        dogBreeds.add("German Shepherd");
        dogBreeds.add("Bulldog");
        dogBreeds.add("Beagle");
        dogBreeds.add("Indie");

        petTypeMap.put("Dog", dogBreeds);
    }

    @Lock(LockType.READ)
    @Override
    public List<String> getBreeds(String petType) {
        return petTypeMap.get(petType);
    }

    @Lock(LockType.WRITE)
    @Override
    public void setBreeds(String petType, List<String> breeds) {
        petTypeMap.put(petType, breeds);
    }

}



// Deploy the updated bean

mvn clean
mvn wildfly:undeploy 
mvn install wildfly:deploy   

// Head to the client - no change required there

// Run the client app - the output is:
// Dog breeds: [Golden Retriever, Labrador, German Shepherd, Bulldog, Beagle, Indie]
// Cat breeds: [Siamese, Persian, Maine Coon, Ragdoll, Bengal]




