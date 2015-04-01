package org.dockhouse.service.registryapi;

import static org.junit.Assert.assertSame;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.service.RegistryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.when;

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
	
	@Mock
	private RegistryService registryService;
	
	private Registry registry;
	
	private RegistryType docker;
	
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(registryAPIServiceFactory, "registryService", registryService);

    	registry = new Registry();
    	
    	docker = new RegistryType();
    	docker.setName("Docker");
    }

    @Test
    public void getDockerAPIService() {
    	assertSame(dockerAPIService, registryAPIServiceFactory.get("Docker"));
    	
    	when(registryService.getRegistryTypeOf(registry)).thenReturn(docker);
    	assertSame(dockerAPIService, registryAPIServiceFactory.get(registry));
    }
    
    @Test(expected=IllegalArgumentException.class) 
    public void getNonExistingAPIService() {
    	registryAPIServiceFactory.get("does not exist");
    }
}
