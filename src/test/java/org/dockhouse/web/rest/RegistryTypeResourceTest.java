package org.dockhouse.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.service.RegistryTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the RegistryTypeResource REST controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTypeResourceTest {

    @Inject
    private RegistryTypeRepository registryTypeRepository;

    @Inject
    private RegistryTypeService registryTypeService;
    
    private MockMvc mockMvc;

    private RegistryType registryType;

    @Before
    public void setup() {
        RegistryTypeResource registryTypeMockResource = new RegistryTypeResource();
        ReflectionTestUtils.setField(registryTypeMockResource, "registryTypeRepository", registryTypeRepository);
        ReflectionTestUtils.setField(registryTypeMockResource, "registryTypeService"   , registryTypeService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(registryTypeMockResource).build();

        registryTypeRepository.deleteAll();
        
        registryType = new RegistryType();
        registryType.setName("name");
        registryType.setLogo("http://example.com/logo.png");
        registryType.setDefaultHost("host");
        registryType.setDefaultPort(2222);
        registryType.setPublic(false);
        registryTypeRepository.save(registryType);
    }

    @Test
    public void getRegistryTypeTest200() throws Exception {
      	this.mockMvc.perform(get("/api/registry_types/{id}", registryType.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRegistryTypeTest404() throws Exception {
    	this.mockMvc.perform(get("/api/registry_types/2"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void getRegistryTypesTest200() throws Exception {
    	this.mockMvc.perform(get("/api/registry_types"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
