
package com.example;

import org.wso2.msf4j.MicroservicesRunner;
import com.example.rs.PeopleRestService;

/**
 * Application entry point.
 */
public class Application {
    public static void main(String[] args) {
        new MicroservicesRunner()
                .deploy(new PeopleRestService())
                .start();
    }
}
