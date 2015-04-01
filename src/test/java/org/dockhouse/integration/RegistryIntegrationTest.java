package org.dockhouse.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.web.rest.RegistryResource;
import org.dockhouse.web.rest.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the RegistryResource REST endpoint.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RegistryIntegrationTest {

    private MockMvc mockMvc;

    @Inject
    private RegistryResource registryRessource;
    
    @Inject
    private RegistryRepository registryRepository;

    @Inject
    private RegistryTypeRepository registryTypeRepository; 

    private Registry registry1;

    private Registry registry2;
    
    private Registry invalidRegistry;

    private RegistryType registryType;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(registryRessource).build();
        
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
    	 
        registry1 = new Registry();
        registry1.setName("name1");
        registry1.setHost("host");
        registry1.setPort(1111);
        registry1.setProtocol("http");
        registry1.setRegistryType(registryType);
     
        registry2 = new Registry();
        registry2.setName("name2");
        registry2.setHost("host2");
        registry2.setPort(2222);
        registry2.setProtocol("https");
        registry2.setRegistryType(registryType);
        
        invalidRegistry = new Registry();
        invalidRegistry.setName(null);
        invalidRegistry.setHost(null);
        invalidRegistry.setPort(-1);
        invalidRegistry.setProtocol(null);
        invalidRegistry.setRegistryType(registryType);
    }
    
    @Test
    public void showRegistryTest200() throws Exception {
        registryRepository.save(registry1);
    	
    	this.mockMvc.perform(get("/api/registries/" + registry1.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        
        .andExpect(jsonPath("$.id")      .value(registry1.getId()))
        .andExpect(jsonPath("$.name")    .value(registry1.getName()))
        .andExpect(jsonPath("$.host")    .value(registry1.getHost()))
        .andExpect(jsonPath("$.port")    .value(registry1.getPort()))
        .andExpect(jsonPath("$.protocol").value(registry1.getProtocol()))
        .andExpect(jsonPath("$.registryType.id")         .value(registryType.getId()))
        .andExpect(jsonPath("$.registryType.name")       .value(registryType.getName()))
        .andExpect(jsonPath("$.registryType.logo")       .value(registryType.getLogo()))
        .andExpect(jsonPath("$.registryType.defaultHost").value(registryType.getDefaultHost()))
        .andExpect(jsonPath("$.registryType.defaultPort").value(registryType.getDefaultPort()))
        .andExpect(jsonPath("$.registryType.public")     .value(registryType.isPublic()))        
        ;
    }
    
    @Test
    public void showRegistryTest404() throws Exception {
    	this.mockMvc.perform(get("/api/registries/does_not_exists"))
        .andExpect(status().isNotFound());      
    }
    
    @Test
    public void getRegistriesTest200() throws Exception {
        registryRepository.save(registry1);
        registryRepository.save(registry2);
        assertThat(registryRepository.findAll()).hasSize(2);
    	
    	this.mockMvc.perform(get("/api/registries"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        
        .andExpect(jsonPath("$[0].id")      .value(registry1.getId()))
        .andExpect(jsonPath("$[0].name")    .value(registry1.getName()))
        .andExpect(jsonPath("$[0].host")    .value(registry1.getHost()))
        .andExpect(jsonPath("$[0].port")    .value(registry1.getPort()))
        .andExpect(jsonPath("$[0].protocol").value(registry1.getProtocol()))
        .andExpect(jsonPath("$[0].registryType.id")         .value(registryType.getId()))
        .andExpect(jsonPath("$[0].registryType.name")       .value(registryType.getName()))
        .andExpect(jsonPath("$[0].registryType.logo")       .value(registryType.getLogo()))
        .andExpect(jsonPath("$[0].registryType.defaultHost").value(registryType.getDefaultHost()))
        .andExpect(jsonPath("$[0].registryType.defaultPort").value(registryType.getDefaultPort()))
        .andExpect(jsonPath("$[0].registryType.public")     .value(registryType.isPublic())) 
      
        .andExpect(jsonPath("$[1].id")      .value(registry2.getId()))
        .andExpect(jsonPath("$[1].name")    .value(registry2.getName()))
        .andExpect(jsonPath("$[1].host")    .value(registry2.getHost()))
        .andExpect(jsonPath("$[1].port")    .value(registry2.getPort()))
        .andExpect(jsonPath("$[1].protocol").value(registry2.getProtocol()))
        .andExpect(jsonPath("$[1].registryType.id")         .value(registryType.getId()))
        .andExpect(jsonPath("$[1].registryType.name")       .value(registryType.getName()))
        .andExpect(jsonPath("$[1].registryType.logo")       .value(registryType.getLogo()))
        .andExpect(jsonPath("$[1].registryType.defaultHost").value(registryType.getDefaultHost()))
        .andExpect(jsonPath("$[1].registryType.defaultPort").value(registryType.getDefaultPort()))
        .andExpect(jsonPath("$[1].registryType.public")     .value(registryType.isPublic())) 
        ;
    }
    
    @Test
    public void createRegistryTestPOST201() throws Exception {
        assertThat(registryRepository.findAll()).hasSize(0);

    	this.mockMvc.perform(post("/api/registries")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonString(registry1)))
    			
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name")    .value(registry1.getName()))
        .andExpect(jsonPath("$.host")    .value(registry1.getHost()))
        .andExpect(jsonPath("$.port")    .value(registry1.getPort()))
        .andExpect(jsonPath("$.protocol").value(registry1.getProtocol()))
        .andExpect(jsonPath("$.registryType.id")         .value(registryType.getId()))
        .andExpect(jsonPath("$.registryType.name")       .value(registryType.getName()))
        .andExpect(jsonPath("$.registryType.logo")       .value(registryType.getLogo()))
        .andExpect(jsonPath("$.registryType.defaultHost").value(registryType.getDefaultHost()))
        .andExpect(jsonPath("$.registryType.defaultPort").value(registryType.getDefaultPort()))
        .andExpect(jsonPath("$.registryType.public")     .value(registryType.isPublic())) 
        ;
    	
    	List<Registry> registries = registryRepository.findAll();
        assertThat(registries).hasSize(1);
        Registry createdRegistry = registries.iterator().next();
        assertThat(createdRegistry.getName()).isEqualTo(registry1.getName());
    }
    
    @Test
    public void createRegistryTestPOST400() throws Exception {
        assertThat(registryRepository.findAll()).hasSize(0);
    	
    	this.mockMvc.perform(post("/api/registries")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonString(invalidRegistry)))

        .andExpect(status().isBadRequest());
    	
        assertThat(registryRepository.findAll()).hasSize(0);
    }
    
    @Test
    public void createRegistryTestPUT200() throws Exception {
    	final String id = "551c1748c8306c8fa74e3cd6";
        assertThat(registryRepository.findAll()).hasSize(0);

    	this.mockMvc.perform(put("/api/registries/" + id)
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonString(registry1)))
    			
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            
        .andExpect(jsonPath("$.id")      .value(id))
        .andExpect(jsonPath("$.name")    .value(registry1.getName()))
        .andExpect(jsonPath("$.host")    .value(registry1.getHost()))
        .andExpect(jsonPath("$.port")    .value(registry1.getPort()))
        .andExpect(jsonPath("$.protocol").value(registry1.getProtocol()))
        .andExpect(jsonPath("$.registryType.id")         .value(registryType.getId()))
        .andExpect(jsonPath("$.registryType.name")       .value(registryType.getName()))
        .andExpect(jsonPath("$.registryType.logo")       .value(registryType.getLogo()))
        .andExpect(jsonPath("$.registryType.defaultHost").value(registryType.getDefaultHost()))
        .andExpect(jsonPath("$.registryType.defaultPort").value(registryType.getDefaultPort()))
        .andExpect(jsonPath("$.registryType.public")     .value(registryType.isPublic())) 
        ;
    	
    	List<Registry> registries = registryRepository.findAll();
        assertThat(registries).hasSize(1);
        Registry createdRegistry = registries.iterator().next();
        assertThat(createdRegistry.getName()).isEqualTo(registry1.getName());
    }
    
    @Test
    public void createRegistryTestPUT400() throws Exception {
    	final String id = "551c1748c8306c8fa74e3cd6";
        assertThat(registryRepository.findAll()).hasSize(0);
    	
    	this.mockMvc.perform(put("/api/registries/" + id)
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonString(invalidRegistry)))

        .andExpect(status().isBadRequest());
    	
        assertThat(registryRepository.findAll()).hasSize(0);
    }
    
    @Test
    public void updateRegistryTest200() throws Exception {
    	registryRepository.save(registry1);
    	final String id = registry1.getId();
        assertThat(registryRepository.findAll()).hasSize(1);

        Registry updatedRegistry = new Registry();
        updatedRegistry.setName("new name");
        updatedRegistry.setHost("new host");
        updatedRegistry.setPort(1111);
        updatedRegistry.setProtocol("http");
        updatedRegistry.setRegistryType(registryType);
        
    	this.mockMvc.perform(put("/api/registries/" + id)
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonString(updatedRegistry)))
    			
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            
        .andExpect(jsonPath("$.id")      .value(id))
        .andExpect(jsonPath("$.name")    .value(updatedRegistry.getName()))
        .andExpect(jsonPath("$.host")    .value(updatedRegistry.getHost()))
        .andExpect(jsonPath("$.port")    .value(updatedRegistry.getPort()))
        .andExpect(jsonPath("$.protocol").value(updatedRegistry.getProtocol()))
        .andExpect(jsonPath("$.registryType.id")         .value(registryType.getId()))
        .andExpect(jsonPath("$.registryType.name")       .value(registryType.getName()))
        .andExpect(jsonPath("$.registryType.logo")       .value(registryType.getLogo()))
        .andExpect(jsonPath("$.registryType.defaultHost").value(registryType.getDefaultHost()))
        .andExpect(jsonPath("$.registryType.defaultPort").value(registryType.getDefaultPort()))
        .andExpect(jsonPath("$.registryType.public")     .value(registryType.isPublic())) 
        ;
    	
    	List<Registry> registries = registryRepository.findAll();
        assertThat(registries).hasSize(1);
        Registry createdRegistry = registries.iterator().next();
        assertThat(createdRegistry.getName()).isEqualTo(updatedRegistry.getName());
    }
    
    @Test
    public void updateRegistryTest400() throws Exception {
    	registryRepository.save(registry1);
    	assertThat(registryRepository.findAll()).hasSize(1);
    	
    	this.mockMvc.perform(put("/api/registries/" + registry1.getId())
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonString(invalidRegistry)))

        .andExpect(status().isBadRequest());
    	
        assertThat(registryRepository.findAll()).hasSize(1);
    }
    
    @Test
    public void deleteRegistryTest204() throws Exception {
    	registryRepository.save(registry1);
    	assertThat(registryRepository.findAll()).hasSize(1);
    	
    	this.mockMvc.perform(delete("/api/registries/" + registry1.getId()))
        .andExpect(status().isNoContent());
    	
        assertThat(registryRepository.findAll()).hasSize(0);
        
    	this.mockMvc.perform(delete("/api/registries/" + registry1.getId()))
        .andExpect(status().isNoContent());
    }
}