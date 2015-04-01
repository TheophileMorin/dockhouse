package org.dockhouse.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.config.MongoConfiguration;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
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
    
    private Registry registry2;
    
    @Before
    public void setup() {
        registryRepository.deleteAll();
        registryTypeRepository.deleteAll();
        
        registryType = new RegistryType();
        registryType.setName("name1");
        registryType.setLogo("http://example.com/logo.png");
        registryType.setDefaultHost("host");
        registryType.setDefaultPort(2222);
        registryType.setPublic(false);
        registryTypeRepository.save(registryType);
        
        registry1 = new Registry();
        registry1.setName("name2");
        registry1.setProtocol("http");
        registry1.setHost("host");
        registry1.setPort(2222);
        registry1.setRegistryType(registryType);
        registryRepository.save(registry1);
        
        registry2 = new Registry();
        registry2.setName("name3");
        registry2.setProtocol("http");
        registry2.setHost("host");
        registry2.setPort(2222);
        registry2.setRegistryType(registryType);
        registryRepository.save(registry2);
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
