package org.dockhouse.domain.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.web.rest.dto.RegistryInDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class MongoDBReferenceValidatorTest {

	@Inject
	private Validator validator;

    @Inject
    private RegistryTypeRepository registryTypeRepository;

	private RegistryInDTO registryInDTO;
	
	private RegistryType registryType;
	
	@Before
	public void setUp() {
        registryType = new RegistryType();
        registryType.setName("name");
        registryType.setLogo("http://example.com/logo.png");
        registryType.setHost("host");
        registryType.setPort(2222);
        registryType.setPublic(false);
        registryTypeRepository.save(registryType);

        registryInDTO = new RegistryInDTO();
        registryInDTO.setName("name");
        registryInDTO.setHost("host");
        registryInDTO.setPort(2222);
        registryInDTO.setProtocol("http");
	}
	
	@Test
	public void testReferenceToRegistryTypeMustBeValid() {
        registryInDTO.setRegistryTypeId(registryType.getId());
		Set<ConstraintViolation<RegistryInDTO>> errors = validator.validateProperty(registryInDTO, "registryTypeId", Default.class);
		assertTrue(errors.isEmpty());
	
		registryInDTO.setRegistryTypeId("");
		errors = validator.validateProperty(registryInDTO, "registryTypeId", Default.class);
		assertFalse(errors.isEmpty());
		
		registryInDTO.setRegistryTypeId(null);
		errors = validator.validateProperty(registryInDTO, "registryTypeId", Default.class);
		assertFalse(errors.isEmpty());
	}
}
