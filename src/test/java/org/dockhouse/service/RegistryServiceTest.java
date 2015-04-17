package org.dockhouse.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.populator.RegistryPopulator;
import org.dockhouse.populator.RegistryTypePopulator;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for the Registry service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryServiceTest {

    @Inject
    private RegistryRepository registryRepository;

    @Inject
    private RegistryTypeRepository registryTypeRepository;

    @Inject
    private RegistryService registryService;
    
    private RegistryType registryType;

    private Registry registry1;
        
    @Inject
	private RegistryTypePopulator registryTypePopulator;
    
    @Inject
	private RegistryPopulator registryPopulator;
    
    @Before
    public void setup() {
        registryRepository.deleteAll();
        registryTypeRepository.deleteAll();
        registryTypePopulator.populate();
        registryPopulator.populate();
        
	    registryType = registryTypeRepository.findAll().get(0);	     
        registry1 = registryRepository.findAll().get(0);
    }

    @Test
    public void getAllTest() {
    	List<Registry> registries = registryService.getAll();
    	assertEquals(2, registries.size());
    }
    
    @Test
    public void getOneTest() {
    	Optional<Registry> registry = registryService.getOne(registry1.getId());
    	assertTrue(registry.isPresent());
    	assertEquals(registry1.getId(), registry.get().getId());
    
    	registry = registryService.getOne("0");
    	assertFalse(registry.isPresent());
    }

    @Test
    public void insertTest() {
    	long collectionSize = registryRepository.count();
    	Registry registry = new Registry();
    	final String name = "name";
    	final String id = "id";
    	registry.setName(name);
    	registry.setId(id);
		registry.setApiVersion("V1");
    	registry.setRegistryType(registryType);
    	registry = registryService.insert(registry);
    	assertEquals(name, registry.getName());
    	assertFalse(id.equals(registry.getId()));
    	assertEquals(collectionSize+1, registryRepository.count());
    }
    
    @Test
    public void upsertTest() {
    	long collectionSize = registryRepository.count();
    	Registry registry = new Registry();
    	String name = "name";
    	String id = "id";
    	registry.setName(name);
		registry.setApiVersion("V1");
    	registry.setRegistryType(registryType);
    	registry = registryService.upsert(registry, id);
    	assertEquals(name, registry.getName());
    	assertEquals(id, registry.getId());
    	assertEquals(collectionSize+1, registryRepository.count());
    	
    	name = "new name";
    	registry.setId(null);
    	registry.setName(name);
    	registry = registryService.upsert(registry, id);
    	assertEquals(name, registry.getName());
    	assertEquals(id, registry.getId());
    	assertEquals(collectionSize+1, registryRepository.count());
    }
}
