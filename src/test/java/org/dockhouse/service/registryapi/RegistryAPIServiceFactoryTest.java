package org.dockhouse.service.registryapi;

import static org.junit.Assert.assertSame;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryAPIServiceFactory.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RegistryAPIServiceFactoryTest {

	@Inject
	private RegistryAPIServiceFactory registryAPIServiceFactory;

	@Inject
	private DockerRegistryAPIServiceV1 dockerAPIService;

	private Registry registry;

	private RegistryType docker;

    @Before
    public void setup() {
    	registry = new Registry();
    	docker = new RegistryType();
    	docker.setName("Docker");
    	registry.setApiVersion("v1");
    	registry.setRegistryType(docker);
    }

    @Test
    public void getDockerAPIService() {
    	assertSame(dockerAPIService, registryAPIServiceFactory.get("Docker", "v1"));
    	assertSame(dockerAPIService, registryAPIServiceFactory.get(registry));
    }

    @Test(expected=IllegalArgumentException.class)
    public void getNonExistingAPIService() {
    	registryAPIServiceFactory.get("does not exist", "");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void getNonExistingAPIServiceForDocker() {
    	registryAPIServiceFactory.get("Docker", "not an api version");
    }
}
