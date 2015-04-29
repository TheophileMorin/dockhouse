package org.dockhouse.domain;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.validation.Validator;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.populator.RegistryTypePopulator;
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
	private RegistryTypePopulator registryTypePopulator;
	
    @Inject
    private RegistryTypeRepository registryTypeRepository;
	
	@Before
    public void setup() {
		
    	registryTypeRepository.deleteAll();
    	registryTypePopulator.populate();
    	registryType = registryTypeRepository.findAll().get(0);
        
		registry = new Registry();
    	registry.setName("name");
    	registry.setHost("host");
    	registry.setPort(1);
    	registry.setProtocol("protocol");
    	registry.setApiVersion("V1");
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
	public void testApiVersionCannotBeNull() {
		registry.setApiVersion(null);
		assertFieldIsInvalid(registry, "apiVersion");
		
		registry.setApiVersion("V1");
		assertFieldIsValid(registry, "apiVersion");
	}
	
	@Test
	public void testRegistryTypeCannotBeNull() {
		registry.setRegistryType(null);
		assertFieldIsInvalid(registry, "registryType");
	}
	
	@Test
	public void testRegistryTypeCannotBeInvalidReference() {
		RegistryType registryType = new RegistryType();
		registryType.setId("invalid");
		registry.setRegistryType(registryType);
		assertIsInvalid(registry);
	}
	
	@Test 
	public void testRegistryTypeIsTheSameAsInTheDatabase() {
		RegistryType newRegistryType = new RegistryType();
		newRegistryType.setId(registryType.getId());
		newRegistryType.setName("different");
		registry.setRegistryType(newRegistryType);
		
		String originalName = registryType.getName();
		assertIsValid(registry);
		
		assertEquals(originalName, registry.getRegistryType().getName());
	}
}
