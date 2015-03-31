package org.dockhouse.web.rest.dto.mapping;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryTypeOutDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 
    @Before
    public void setup() {
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
    	registryTypeOutDTOMapper.mapper().validate();
    }
    
    @Test
    public void createDTOTest() {
    	RegistryType registryType = new RegistryType();
    	registryType.setId("1");
    	registryType.setName("name");
    	registryType.setDefaultHost("host");
    	registryType.setDefaultPort(1);
    	registryType.setLogo("logo");
    	registryType.setPublic(true);
    	
    	RegistryTypeOutDTO registryTypeOutDTO = registryTypeOutDTOMapper.createDTO(registryType);
    
    	assertEquals(registryType.getId(), registryTypeOutDTO.getId());
    	assertEquals(registryType.getName(), registryTypeOutDTO.getName());
    	assertEquals(registryType.getDefaultHost(), registryTypeOutDTO.getDefaultHost());
    	assertEquals(registryType.getDefaultPort(), registryTypeOutDTO.getDefaultPort());
    	assertEquals(registryType.getLogo(), registryTypeOutDTO.getLogo());
    	assertEquals(registryType.isPublic(), registryTypeOutDTO.isPublic());
    }
}
