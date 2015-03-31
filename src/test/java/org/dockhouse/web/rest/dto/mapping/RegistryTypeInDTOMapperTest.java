package org.dockhouse.web.rest.dto.mapping;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryInDTO;
import org.dockhouse.web.rest.dto.RegistryTypeInDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryTypeInDTOMapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTypeInDTOMapperTest {
	
    @Inject
    private RegistryTypeInDTOMapper registryTypeInDTOMapper;
   
	private ModelMapper modelMapper;

    @Before
    public void setup() {
        modelMapper = registryTypeInDTOMapper.mapper();
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
    	modelMapper.validate();
    }
    
    @Test
    public void createRegistryTypeTest() {
    	RegistryTypeInDTO registryTypeInDTO = new RegistryTypeInDTO();
    	registryTypeInDTO.setName("name");
    	registryTypeInDTO.setDefaultHost("host");
    	registryTypeInDTO.setDefaultPort(1);
    	registryTypeInDTO.setLogo("logo");
    	registryTypeInDTO.setPublic(true);
    	
    	RegistryType registryType = registryTypeInDTOMapper.createRegistryType(registryTypeInDTO);
    
    	assertEquals(registryTypeInDTO.getName(), registryType.getName());
    	assertEquals(registryTypeInDTO.getDefaultHost(), registryType.getDefaultHost());
    	assertEquals(registryTypeInDTO.getDefaultPort(), registryType.getDefaultPort());
    	assertEquals(registryTypeInDTO.getLogo(), registryType.getLogo());
    	assertEquals(registryTypeInDTO.isPublic(), registryType.isPublic());
    }
}
