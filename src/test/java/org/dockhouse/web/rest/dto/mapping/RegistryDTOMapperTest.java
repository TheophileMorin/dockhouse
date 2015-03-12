package org.dockhouse.web.rest.dto.mapping;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryDTOMapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryDTOMapperTest {
	
    @Inject
    private RegistryDTOMapper registryDTOMapper;
   
	private ModelMapper modelMapper;

    private Registry registry;

    private RegistryType registryType;
    
    @Before
    public void setup() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
    	modelMapper.createTypeMap(Registry.class, RegistryDTO.class);
    	modelMapper.validate();
    }
}
