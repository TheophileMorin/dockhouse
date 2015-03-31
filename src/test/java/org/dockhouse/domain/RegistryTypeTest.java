package org.dockhouse.domain;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.RegistryType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryType.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTypeTest extends ValidationTest<RegistryType> {

	private RegistryType registryType;
	
	@Before
    public void setup() {
		registryType = new RegistryType();
    	registryType.setName("name");
    	registryType.setDefaultHost("host");
    	registryType.setDefaultPort(1);
    	registryType.setLogo("logo");
    	registryType.setPublic(true);
    }

	@Test
	public void testRegistryTypeInDTOIsValid() {
		assertIsValid(registryType);
	}
	
	@Test
	public void testNameCannotBeNull() {
		registryType.setName(null);
		assertFieldIsInvalid(registryType, "name");
	}
	
	@Test
	public void testNameCannotBeEmpty() {
		registryType.setName("");
		assertFieldIsInvalid(registryType, "name");
	}
	
	@Test
	public void testNameLengthisBetween1And50() {
		registryType.setName(stringOfLength(0));
		assertFieldIsInvalid(registryType, "name");
		
		registryType.setName(stringOfLength(51));
		assertFieldIsInvalid(registryType, "name");
		
		registryType.setName(stringOfLength(1));
		assertFieldIsValid(registryType, "name");
		
		registryType.setName(stringOfLength(10));
		assertFieldIsValid(registryType, "name");
		
		registryType.setName(stringOfLength(50));
		assertFieldIsValid(registryType, "name");
	}
	
	@Test
	public void testDefaultHostCannotBeNull() {
		registryType.setDefaultHost(null);
		assertFieldIsInvalid(registryType, "defaultHost");
	}
	
	@Test
	public void testDefaultPortIsBetween0And65535() {
		registryType.setDefaultPort(-1);   
		assertFieldIsInvalid(registryType, "defaultPort");
		
		registryType.setDefaultPort(65536);
		assertFieldIsInvalid(registryType, "defaultPort");
		
		registryType.setDefaultPort(0);
		assertFieldIsValid(registryType, "defaultPort");
		
		registryType.setDefaultPort(22222);
		assertFieldIsValid(registryType, "defaultPort");
		
		registryType.setDefaultPort(65535);
		assertFieldIsValid(registryType, "defaultPort");
	}
}
