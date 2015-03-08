package org.dockhouse;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by BWI on 07/03/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port:0")
public abstract class AbstractIntegrationTest {

    // Will contain the random free port number
    @Value("${local.server.port}")
    private int port;

    protected TestRestTemplate restTemplate = new TestRestTemplate();

    /**
     * Returns the base url for your rest interface
     *
     * @return
     */
    protected String getBaseUrl() {
        return "http://localhost:" + port + "/api/dockhouse";
    }


}
