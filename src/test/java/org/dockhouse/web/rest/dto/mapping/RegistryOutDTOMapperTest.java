package org.dockhouse.web.rest.dto.mapping;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryOutDTO;
import org.dockhouse.web.rest.dto.RegistryTypeOutDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for RegistryOutDTOMapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryOutDTOMapperTest {
	
    @Inject
    private RegistryOutDTOMapper registryOutDTOMapper;

    @Before
    public void setup() {
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
    	registryOutDTOMapper.mapper().validate();
    }
    
    @Test
    public void createDTOTest() {
    	Registry registry = new Registry();
    	registry.setId("1");
    	registry.setName("name");
    	registry.setHost("host");
    	registry.setPort(1);
    	registry.setProtocol("protocol");

    	RegistryType registryType = new RegistryType();
    	registryType.setId("1");
    	registryType.setName("name");
    	registryType.setHost("host");
    	registryType.setPort(1);
    	registryType.setLogo("logo");
    	registryType.setPublic(true);

    	RegistryOutDTO registryOutDTO = registryOutDTOMapper.createDTO(registry, registryType);
    	RegistryTypeOutDTO registryTypeOutDTO = registryOutDTO.getRegistryType();

    	assertEquals(registry.getId(), registryOutDTO.getId());
    	assertEquals(registry.getName(), registryOutDTO.getName());
    	assertEquals(registry.getHost(), registryOutDTO.getHost());
    	assertEquals(registry.getPort(), registryOutDTO.getPort());
    	assertEquals(registry.getProtocol(), registryOutDTO.getProtocol());
    	
    	assertEquals(registryType.getId(), registryTypeOutDTO.getId());
    	assertEquals(registryType.getName(), registryTypeOutDTO.getName());
    	assertEquals(registryType.getHost(), registryTypeOutDTO.getHost());
    	assertEquals(registryType.getPort(), registryTypeOutDTO.getPort());
    	assertEquals(registryType.getLogo(), registryTypeOutDTO.getLogo());
    	assertEquals(registryType.isPublic(), registryTypeOutDTO.isPublic());
    }
}
