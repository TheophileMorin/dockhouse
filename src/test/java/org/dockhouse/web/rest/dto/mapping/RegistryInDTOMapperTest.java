package org.dockhouse.web.rest.dto.mapping;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryInDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryInDTOMapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryInDTOMapperTest {
	
    @Inject
    private RegistryInDTOMapper registryInDTOMapper;
   
	private ModelMapper modelMapper;

    private Registry registry;

    private RegistryType registryType;
    
    @Before
    public void setup() {
        modelMapper = registryInDTOMapper.mapper();
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
//    	modelMapper.createTypeMap(RegistryInDTO.class, Registry.class);
    	modelMapper.validate();
    }
}
