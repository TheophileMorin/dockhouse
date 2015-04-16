package org.dockhouse.domain;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for Registry.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTest extends ValidationTest<Registry> {

	private Registry registry;
	
	private RegistryType registryType;

    @Inject
    private RegistryTypeRepository registryTypeRepository;
	
	@Before
    public void setup() {
    	registryTypeRepository.deleteAll();
        registryType = new RegistryType();
        registryType.setName("name");
        registryType.setLogo("http://example.com/logo.png");
        registryType.setDefaultHost("host");
        registryType.setDefaultPort(2222);
        registryType.setPublic(false);
    	List<String> versions = new ArrayList<String>();
		versions.add("V1");
		registryType.setApiVersions(versions);
        registryType = registryTypeRepository.save(registryType);
		
		registry = new Registry();
    	registry.setName("name");
    	registry.setHost("host");
    	registry.setPort(1);
    	registry.setProtocol("protocol");
    	registry.setRegistryType(registryType);
    }

	@Test
	public void testRegistryIsValid() {
		assertIsValid(registry);
	}
	
	@Test
	public void testNameCannotBeNull() {
		registry.setName(null);
		assertFieldIsInvalid(registry, "name");
	}
	
	@Test
	public void testNameCannotBeEmpty() {
		registry.setName("");
		assertFieldIsInvalid(registry, "name");
	}
	
	@Test
	public void testNameLengthisBetween1And50() {
		registry.setName(stringOfLength(0));
		assertFieldIsInvalid(registry, "name");
		
		registry.setName(stringOfLength(51));
		assertFieldIsInvalid(registry, "name");
		
		registry.setName(stringOfLength(1));
		assertFieldIsValid(registry, "name");
		
		registry.setName(stringOfLength(10));
		assertFieldIsValid(registry, "name");
		
		registry.setName(stringOfLength(50));
		assertFieldIsValid(registry, "name");
	}
	
	@Test
	public void testPortIsBetween0And65535() {
		registry.setPort(-1);   
		assertFieldIsInvalid(registry, "port");
		
		registry.setPort(65536);
		assertFieldIsInvalid(registry, "port");
		
		registry.setPort(0);
		assertFieldIsValid(registry, "port");
		
		registry.setPort(22222);
		assertFieldIsValid(registry, "port");
		
		registry.setPort(65535);
		assertFieldIsValid(registry, "port");
	}
	
	@Test
	public void testProtocolLengthisBetween0And10() {
		registry.setProtocol(stringOfLength(0));
		assertFieldIsValid(registry, "protocol");
		
		registry.setProtocol(stringOfLength(11));
		assertFieldIsInvalid(registry, "protocol");
		
		registry.setProtocol(stringOfLength(1));
		assertFieldIsValid(registry, "protocol");
		
		registry.setProtocol(stringOfLength(5));
		assertFieldIsValid(registry, "protocol");
		
		registry.setProtocol(stringOfLength(10));
		assertFieldIsValid(registry, "protocol");
	}
	
	@Test
	public void testRegistryTypeCannotBeNull() {
		registry.setRegistryType(null);
		assertFieldIsInvalid(registry, "registryType");
	}
}
