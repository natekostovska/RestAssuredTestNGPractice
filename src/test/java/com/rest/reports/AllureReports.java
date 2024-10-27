package com.rest.reports;

public class AllureReports {
    // https://allurereport.org/ https://allurereport.org/docs/
    /*
    *  Install Allure depending on the OS
    * macOS https://allurereport.org/docs/install-for-macos/
    *
    * 1!!!!!!!!!!------------------------------------------------------------------------------------------------
    * Windows https://allurereport.org/docs/install-for-windows/ https://github.com/ScoopInstaller/Install#readme
    * Open powershell and pass the command for scoop to be installed
    * irm get.scoop.sh | iex
# You can use proxies if you have network trouble in accessing GitHub, e.g.
irm get.scoop.sh -Proxy 'http://<ip:port>' | iex
    * if there is an error paste Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
    * scoop install allure
    * then type allure to see that is installed
    * open cmd
    *
    * 2!!!!!!!!!!!! -----------------------------------------------------------------------------------------
    * Another way to install allure with manual installation
    * https://github.com/allure-framework/allure2/releases/tag/2.31.0 download the zip file
    * Install from an archive
Allure Report commandline latest version

Make sure Java version 8 or above installed, and its directory is specified in the JAVA_HOME environment variable.

Go to the latest Allure Report release on GitHub and download the allure-*.zip or allure-*.tgz archive.

Uncompress the archive into the directory of your choice.

Remember the path to its bin subdirectory. You will need this path in a future step.

Make sure that the allure command resolves to the allure file from your installation directory.

There are various ways to do so, for example, via the Control Panel or by running a PowerShell script.

Using Control Panel

Using PowerShell

In a new PowerShell window, run this command to see if it reports the latest version:


allure --version
    *
    * */

    /*---------------------------------------------------------------------------------------------------------------
     *   3!!!! Add dependency regarding the testing framework we are using
     * https://allurereport.org/docs/frameworks/
     * TESTNG https://allurereport.org/docs/testng/
     * https://mvnrepository.com/artifact/io.qameta.allure/allure-testng - add it
     * copy the plug in section from the documentation Configure AspectJ in build, plug in pom
     * then https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-plugin
     * paste aspectj properties before build and get latest version https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
     * Restart the ide
     * Execute the test cases and it will generate the allure reports it will be generated under the root folder, allure - results
     * File-> Open in terminal
     *  paste allure serve allure-results this is open http://192.168.0.100:54605/index.html
     *
     * */


    /*
     *!!!!!!!! 4 Features --------------------------------
     * description = "should be able to create a playlist" to the @Test() Displays the name of the test case
     *    *http://192.168.0.100:60061/index.html#suites/0720168b1ae3a798435b870334bef87f/e74cf252acb345d5/
     *
     * Adding @Description("this is the description") description http://192.168.0.100:60213/index.html#suites/0720168b1ae3a798435b870334bef87f/4b5e15950cdfb821/
     *
     * Adding links @Links (any other links), @Issue (link to the issue), @TmsLink (link to the test case)
     * We need to provide the base url we need to create properties file for allure and need to add key and value pairs
     * http://192.168.0.100:60581/index.html#suites/0720168b1ae3a798435b870334bef87f/9529564baa8bd7cb/
     *
     * Add Behaviors (@Epic,@Feature,@Story) It adds a section features by stories http://192.168.0.100:60809/index.html#behaviors
     *
     * Add steps for our test cases at method level it will list all steps with values @Step http://192.168.0.100:61043/index.html#behaviors/35f04b3121bc240c6548041c44d0cd39/3f330d24483e66ca/
     *
     * Add filter (to populate request and response details) https://allurereport.org/docs/restassured/
     * https://mvnrepository.com/artifact/io.qameta.allure/allure-rest-assured
     *addFilter(new AllureRestAssured()). in spec builder http://192.168.0.100:50898/index.html#behaviors/35f04b3121bc240c6548041c44d0cd39/400fcbbd5aa3f002/
     *
     * Custom folder path allure.results.directory=target/allure-results in allure.properties file that folder will be under target
     * we need to provide the new path in the terminal allure serve target/allure-results
     *
     * https://allurereport.org/docs/testng-reference/#attachments
     *
     *------------ !!!!!!!TO EXECUTE THROUGH CMD- ---------------------------------------------------------------------
     *INSTALL MAVEN https://maven.apache.org/install.html extract paste path in environment paths
     * C:\Users\natasha.kostovska\Downloads\New folder\apache-maven-3.9.9\bin
     * open cmd and paste mvn, C:\Users\natasha.kostovska\IdeaProjects\RestAssured
     * mvn clean test and it will execute all test cases the pom file and jdk java versions should be same so the
     * test cases can be executed through cmd
     * for allure to be executed we can do this allure serve target/allure-results http://192.168.0.100:51965/index.html#suites/7f2ee12aff4f41c3a12e5ab03eeacd99/fb1cbd34716ecd10/
     *
     *  */
}
