package org.dockhouse.web.rest.dto;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryTypeInDTO.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTypeInDTOTest extends DTOValidationTest<RegistryTypeInDTO> {

	private RegistryTypeInDTO registryTypeInDTO;
	
	@Before
    public void setup() {
		registryTypeInDTO = new RegistryTypeInDTO();
    	registryTypeInDTO.setName("name");
    	registryTypeInDTO.setDefaultHost("host");
    	registryTypeInDTO.setDefaultPort(1);
    	registryTypeInDTO.setLogo("logo");
    	registryTypeInDTO.setPublic(true);
    }

	@Test
	public void testRegistryTypeInDTOIsValid() {
		assertIsValid(registryTypeInDTO);
	}
	
	@Test
	public void testNameCannotBeNull() {
		registryTypeInDTO.setName(null);
		assertFieldIsInvalid(registryTypeInDTO, "name");
	}
	
	@Test
	public void testNameCannotBeEmpty() {
		registryTypeInDTO.setName("");
		assertFieldIsInvalid(registryTypeInDTO, "name");
	}
	
	@Test
	public void testNameLengthisBetween1And50() {
		registryTypeInDTO.setName(stringOfLength(0));
		assertFieldIsInvalid(registryTypeInDTO, "name");
		
		registryTypeInDTO.setName(stringOfLength(51));
		assertFieldIsInvalid(registryTypeInDTO, "name");
		
		registryTypeInDTO.setName(stringOfLength(1));
		assertFieldIsValid(registryTypeInDTO, "name");
		
		registryTypeInDTO.setName(stringOfLength(10));
		assertFieldIsValid(registryTypeInDTO, "name");
		
		registryTypeInDTO.setName(stringOfLength(50));
		assertFieldIsValid(registryTypeInDTO, "name");
	}
	
	@Test
	public void testDefaultHostCannotBeNull() {
		registryTypeInDTO.setDefaultHost(null);
		assertFieldIsInvalid(registryTypeInDTO, "defaultHost");
	}
	
	@Test
	public void testDefaultPortIsBetween0And65535() {
		registryTypeInDTO.setDefaultPort(-1);   
		assertFieldIsInvalid(registryTypeInDTO, "defaultPort");
		
		registryTypeInDTO.setDefaultPort(65536);
		assertFieldIsInvalid(registryTypeInDTO, "defaultPort");
		
		registryTypeInDTO.setDefaultPort(0);
		assertFieldIsValid(registryTypeInDTO, "defaultPort");
		
		registryTypeInDTO.setDefaultPort(22222);
		assertFieldIsValid(registryTypeInDTO, "defaultPort");
		
		registryTypeInDTO.setDefaultPort(65535);
		assertFieldIsValid(registryTypeInDTO, "defaultPort");
	}
}
