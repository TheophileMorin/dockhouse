package org.dockhouse.web.rest.dto;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
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
 * Test class for RegistryInDTO.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryInDTOTest extends DTOValidationTest<RegistryInDTO> {

	private RegistryInDTO registryInDTO;
	
	private RegistryType registryType;

    @Inject
    private RegistryTypeRepository registryTypeRepository;
	
	@Before
    public void setup() {
    	registryTypeRepository.deleteAll();
        registryType = new RegistryType();
        registryType.setName("name");
        registryType.setLogo("http://example.com/logo.png");
        registryType.setHost("host");
        registryType.setPort(2222);
        registryType.setPublic(false);
        registryType = registryTypeRepository.save(registryType);
		
		registryInDTO = new RegistryInDTO();
    	registryInDTO.setName("name");
    	registryInDTO.setHost("host");
    	registryInDTO.setPort(1);
    	registryInDTO.setProtocol("protocol");
    	registryInDTO.setRegistryTypeId(registryType.getId());
    }

	@Test
	public void testRegistryInDTOIsValid() {
		assertIsValid(registryInDTO);
	}
	
	@Test
	public void testNameCannotBeNull() {
		registryInDTO.setName(null);
		assertFieldIsInvalid(registryInDTO, "name");
	}
	
	@Test
	public void testNameCannotBeEmpty() {
		registryInDTO.setName("");
		assertFieldIsInvalid(registryInDTO, "name");
	}
	
	@Test
	public void testNameLengthisBetween1And50() {
		registryInDTO.setName(stringOfLength(0));
		assertFieldIsInvalid(registryInDTO, "name");
		
		registryInDTO.setName(stringOfLength(51));
		assertFieldIsInvalid(registryInDTO, "name");
		
		registryInDTO.setName(stringOfLength(1));
		assertFieldIsValid(registryInDTO, "name");
		
		registryInDTO.setName(stringOfLength(10));
		assertFieldIsValid(registryInDTO, "name");
		
		registryInDTO.setName(stringOfLength(50));
		assertFieldIsValid(registryInDTO, "name");
	}
	
	@Test
	public void testPortIsBetween0And65535() {
		registryInDTO.setPort(-1);   
		assertFieldIsInvalid(registryInDTO, "port");
		
		registryInDTO.setPort(65536);
		assertFieldIsInvalid(registryInDTO, "port");
		
		registryInDTO.setPort(0);
		assertFieldIsValid(registryInDTO, "port");
		
		registryInDTO.setPort(22222);
		assertFieldIsValid(registryInDTO, "port");
		
		registryInDTO.setPort(65535);
		assertFieldIsValid(registryInDTO, "port");
	}
	
	@Test
	public void testProtocolLengthisBetween0And10() {
		registryInDTO.setProtocol(stringOfLength(0));
		assertFieldIsValid(registryInDTO, "protocol");
		
		registryInDTO.setProtocol(stringOfLength(11));
		assertFieldIsInvalid(registryInDTO, "protocol");
		
		registryInDTO.setProtocol(stringOfLength(1));
		assertFieldIsValid(registryInDTO, "protocol");
		
		registryInDTO.setProtocol(stringOfLength(5));
		assertFieldIsValid(registryInDTO, "protocol");
		
		registryInDTO.setProtocol(stringOfLength(10));
		assertFieldIsValid(registryInDTO, "protocol");
	}
	
	@Test
	public void testRegistryTypeIdCannotBeNull() {
		registryInDTO.setRegistryTypeId(null);
		assertFieldIsInvalid(registryInDTO, "registryTypeId");
	}
	
	@Test
	public void testRegistryTypeIdMustReferToAnExistingRegistryType() {
		registryInDTO.setRegistryTypeId(null);
		assertFieldIsInvalid(registryInDTO, "registryTypeId");
		
		registryInDTO.setRegistryTypeId(registryType.getId());
		assertFieldIsValid(registryInDTO, "registryTypeId");
	}
}
