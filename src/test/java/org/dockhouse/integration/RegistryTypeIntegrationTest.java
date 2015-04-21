package org.dockhouse.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.populator.RegistryTypePopulator;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.web.rest.RegistryTypeResource;
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
 * Test class for the RegistryTypeResource REST endpoint.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RegistryTypeIntegrationTest {

    private MockMvc mockMvc;

    @Inject
    private RegistryTypeResource registryTypeRessource;
    
    @Inject
    private RegistryTypeRepository registryTypeRepository; 

    private RegistryType registryType1;

    private RegistryType registryType2;

    @Inject
	private RegistryTypePopulator registryTypePopulator;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(registryTypeRessource).build();
        
        registryTypeRepository.deleteAll();
        registryTypePopulator.populate();
        
        List<RegistryType> registryTypes = registryTypeRepository.findAll();
        registryType1 = registryTypes.get(0);
        registryType2 = registryTypes.get(1);
    }
    
    @Test
    public void getRegistryTypeTest200() throws Exception {
    	
    	this.mockMvc.perform(get("/api/registry_types/" + registryType1.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        
        .andExpect(jsonPath("$.id")         .value(registryType1.getId()))
        .andExpect(jsonPath("$.name")       .value(registryType1.getName()))
        .andExpect(jsonPath("$.logo")       .value(registryType1.getLogo()))
        .andExpect(jsonPath("$.defaultHost").value(registryType1.getDefaultHost()))
        .andExpect(jsonPath("$.defaultPort").value(registryType1.getDefaultPort()))
        .andExpect(jsonPath("$.apiVersions[0]").value(registryType1.getApiVersions().get(0)))
        .andExpect(jsonPath("$.public")     .value(registryType1.isPublic()))        
        ;
    }
    
    @Test
    public void getRegistryTypeTest404() throws Exception {
    	this.mockMvc.perform(get("/api/registry_types/does_not_exists"))
        .andExpect(status().isNotFound());      
    }
    
    @Test
    public void getRegistryTypesTest200() throws Exception {
        assertThat(registryTypeRepository.findAll()).hasSize(2);
    	
    	this.mockMvc.perform(get("/api/registry_types"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        
        .andExpect(jsonPath("$[0].id")         .value(registryType1.getId()))
        .andExpect(jsonPath("$[0].name")       .value(registryType1.getName()))
        .andExpect(jsonPath("$[0].logo")       .value(registryType1.getLogo()))
        .andExpect(jsonPath("$[0].defaultHost").value(registryType1.getDefaultHost()))
        .andExpect(jsonPath("$[0].defaultPort").value(registryType1.getDefaultPort()))
        .andExpect(jsonPath("$[0].apiVersions[0]").value(registryType1.getApiVersions().get(0)))
        .andExpect(jsonPath("$[0].public")     .value(registryType1.isPublic()))  
      
        .andExpect(jsonPath("$[1].id")         .value(registryType2.getId()))
        .andExpect(jsonPath("$[1].name")       .value(registryType2.getName()))
        .andExpect(jsonPath("$[1].logo")       .value(registryType2.getLogo()))
        .andExpect(jsonPath("$[1].defaultHost").value(registryType2.getDefaultHost()))
        .andExpect(jsonPath("$[1].defaultPort").value(registryType2.getDefaultPort()))
        .andExpect(jsonPath("$[1].apiVersions[0]").value(registryType2.getApiVersions().get(0)))
        .andExpect(jsonPath("$[1].public")     .value(registryType2.isPublic()))  
        ;
    }
}