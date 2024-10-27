package com.rest;

public class ParallelExecution {
     /*
    * // for parallel execution eiter at test ng or maven surefire plug in config
      <parallel>methods</parallel>
                    <threadCount>10</threadCount>
    * it will create 10 maximum we have 5 treads now
    *
    * https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html
    * Running Tests in Parallel
    * Base test will print the method and the tread id
    * To be executed in parallel we would need to do it through the maven command
    * Go to project directory mvn test
    * Restart the ide
    * */

    /*
    Run TestNG edit
    *-ea -Dtestng.dtd.http=true -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com/"
    In the Spec builder class set it this can be through command Run TestNG edit
    *-ea -Dtestng.dtd.http=true -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com/"
    With this we can set in which environment to be performed
     *
    * */
}
