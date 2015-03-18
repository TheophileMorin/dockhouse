package org.dockhouse.web.rest.dto.mapping;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryTypeOutDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryTypeOutDTOMapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTypeOutDTOMapperTest {
	
    @Inject
    private RegistryTypeOutDTOMapper registryTypeOutDTOMapper;
   
	private ModelMapper modelMapper;

    @Before
    public void setup() {
        modelMapper = registryTypeOutDTOMapper.mapper();
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
    	modelMapper.validate();
    }
}
