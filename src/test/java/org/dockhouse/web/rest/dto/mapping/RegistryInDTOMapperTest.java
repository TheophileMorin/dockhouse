package org.dockhouse.web.rest.dto.mapping;

import static org.junit.Assert.assertEquals;

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

    @Before
    public void setup() {
    }

    @Test
    public void getAllFieldsAreMapped() throws Exception {
    	registryInDTOMapper.mapper().validate();
    }
    
    @Test
    public void createRegistryTest() {
    	RegistryInDTO registryInDTO = new RegistryInDTO();
    	registryInDTO.setName("name");
    	registryInDTO.setHost("host");
    	registryInDTO.setPort(1);
    	registryInDTO.setProtocol("protocol");
    	registryInDTO.setRegistryTypeId("registryId");
    	
    	Registry registry = registryInDTOMapper.createRegistry(registryInDTO);
    
    	assertEquals(registryInDTO.getName(), registry.getName());
    	assertEquals(registryInDTO.getHost(), registry.getHost());
    	assertEquals(registryInDTO.getPort(), registry.getPort());
    	assertEquals(registryInDTO.getProtocol(), registry.getProtocol());
    	assertEquals(registryInDTO.getRegistryTypeId(), registry.getRegistryTypeId());
    }
}
