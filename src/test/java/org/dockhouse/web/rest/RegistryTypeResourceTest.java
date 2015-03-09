package org.dockhouse.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private MockMvc mockMvc;

    private RegistryType registryType;

    private static String validPayload =
    		"{ \"name\"    : \"registrytype\", " +
			  "\"host\"    : \"host\"        , " +
			  "\"logo\"    : \"http://example.com/logo.png\" , " +
			  "\"port\"    : 22222, " +
			  "\"isPublic\": false }";

    private static String invalidPayload =
    		"{ \"name\"    : \"\", " +
    		  "\"host\"    : \"\", " +
    		  "\"logo\"    : \"\", " +
    		  "\"port\"    : -1  , " +
    		  "\"isPublic\": false }";

    @Before
    public void setup() {
        RegistryTypeResource registryTypeMockResource = new RegistryTypeResource();
        ReflectionTestUtils.setField(registryTypeMockResource, "registryTypeRepository", registryTypeRepository);

        this.mockMvc = MockMvcBuilders.standaloneSetup(registryTypeMockResource).build();

        registryTypeRepository.deleteAll();
        
        registryType = new RegistryType();
        registryType.setName("name");
        registryType.setLogo("http://example.com/logo.png");
        registryType.setHost("host");
        registryType.setPort(2222);
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

    @Test
    public void createRegistryTypeTest201() throws Exception {
    	this.mockMvc.perform(post("/api/registry_types")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(validPayload))
    	.andExpect(status().isCreated());
    }

    @Test
    public void createRegistryTypeTest400() throws Exception {
    	this.mockMvc.perform(post("/api/registry_types")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(invalidPayload))
    	.andExpect(status().isBadRequest());
    }

    @Test
    public void updateRegistryTypeTest204() throws Exception {
    	this.mockMvc.perform(put("/api/registry_types/{id}", registryType.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(validPayload))
    	.andExpect(status().isNoContent());
    }

    @Test
    public void updateRegistryTypeTest400() throws Exception {
    	this.mockMvc.perform(put("/api/registry_types/{id}", registryType.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(invalidPayload))
    	.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteRegistryTypeTest204() throws Exception {
    	this.mockMvc.perform(delete("/api/registry_types/{id}", registryType.getId()))
    	.andExpect(status().isNoContent());
    }
}
