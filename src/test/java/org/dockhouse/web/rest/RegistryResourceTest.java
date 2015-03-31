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
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.service.RegistryService;
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
import org.springframework.validation.Validator;

/**
 * Test class for the RegistryResource REST controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryResourceTest {

    @Inject
    private RegistryRepository registryRepository;

    @Inject
    private RegistryTypeRepository registryTypeRepository; 

    @Inject
    private RegistryService registryService;
    
    @Inject
    private Validator validator;
    
    private MockMvc mockMvc;

    private Registry registry;

    private RegistryType registryType;
    
    private static String validPayload =
    		"{ \"name\"    : \"registry\", " +
			  "\"host\"    : \"host\"    , " +
			  "\"protocol\": \"https\"   , " +
			  "\"port\"    : 22222       , " + 
			  "\"registryTypeId\": \"1\" "+ "}";

    private static String invalidPayload =
    		"{ \"name\"    : \"\", " +
      		  "\"host\"    : \"\", " +
    	 	  "\"protocol\": \"\", " +
   		  	  "\"port\"    : -1  , " + 
   		  	  "\"registryTypeId\": \"2\" " + "}";

    @Before
    public void setup() {
        RegistryResource registryMockResource = new RegistryResource();
        ReflectionTestUtils.setField(registryMockResource, "registryRepository", registryRepository);
        ReflectionTestUtils.setField(registryMockResource, "registryService"   , registryService);
        ReflectionTestUtils.setField(registryMockResource, "validator", validator); 
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(registryMockResource).build();

        registryRepository.deleteAll();
        registryTypeRepository.deleteAll();
        
	    registryType = new RegistryType();
	    registryType.setId("1");
	    registryType.setName("name");
	    registryType.setDefaultHost("host");
	    registryType.setDefaultPort(1111);
	    registryType.setLogo("http://example.com/logo.png");
	    registryType.setPublic(true);
	    registryType = registryTypeRepository.save(registryType);
    	 
        registry = new Registry();
        registry.setName("name");
        registry.setHost("host");
        registry.setPort(2222);
        registry.setProtocol("http");
        registry.setRegistryTypeId(registryType.getId());
        registryRepository.save(registry);
    }

    @Test
    public void getRegistryTest200() throws Exception {
      	this.mockMvc.perform(get("/api/registries/{id}", registry.getId()))
        .andExpect(status().isOk())
	    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRegistryTest404() throws Exception {
    	this.mockMvc.perform(get("/api/registries/2"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void getRegistryStatusTest200() throws Exception {
      	this.mockMvc.perform(get("/api/registries/{id}/status", registry.getId()))
        .andExpect(status().isOk())
	    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void getRegistriesTest200() throws Exception {
    	this.mockMvc.perform(get("/api/registries"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createRegistryTest201() throws Exception {
    	this.mockMvc.perform(post("/api/registries")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(validPayload))
    	.andExpect(status().isCreated())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createRegistryTest400() throws Exception {
    	this.mockMvc.perform(post("/api/registries")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(invalidPayload))
    	.andExpect(status().isBadRequest());
    }

    @Test
    public void updateRegistryTest200() throws Exception {
    	this.mockMvc.perform(put("/api/registries/{id}", registry.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(validPayload))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRegistryTest400() throws Exception {
    	this.mockMvc.perform(put("/api/registries/{id}", registry.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(invalidPayload))
    	.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteRegistryTest204() throws Exception {
    	this.mockMvc.perform(delete("/api/registries/{id}", registry.getId()))
    	.andExpect(status().isNoContent());
    }
}
