package org.dockhouse.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for the RegistryType service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class RegistryTypeServiceTest {

    @Inject
    private RegistryTypeRepository registryTypeRepository;

    @Inject
    private RegistryTypeService registryTypeService;
    
    private RegistryType registryType1;

    private RegistryType registryType2;
    
    @Before
    public void setup() {
        registryTypeRepository.deleteAll();
        
        registryType1 = new RegistryType();
        registryType1.setName("name1");
        registryType1.setLogo("http://example.com/logo.png");
        registryType1.setDefaultHost("host");
        registryType1.setDefaultPort(2222);
        registryType1.setPublic(false);
        registryTypeRepository.save(registryType1);
        
        registryType2 = new RegistryType();
        registryType2.setName("name2");
        registryType2.setLogo("http://example.com/logo.png");
        registryType2.setDefaultHost("host");
        registryType2.setDefaultPort(2222);
        registryType2.setPublic(false);
        registryTypeRepository.save(registryType2);
    }

    @Test
    public void getAllTest() {
    	List<RegistryType> registryTypes = registryTypeService.getAll();
    	assertEquals(2, registryTypes.size());
    }
    
    @Test
    public void getOneTest() {
    	Optional<RegistryType> registryType = registryTypeService.getOne(registryType1.getId());
    	assertTrue(registryType.isPresent());
    	assertEquals(registryType1.getId(), registryType.get().getId());
    
    	registryType = registryTypeService.getOne("0");
    	assertFalse(registryType.isPresent());
    }
    
    @Test
    public void insertTest() {
    	long collectionSize = registryTypeRepository.count();
    	RegistryType registryType = new RegistryType();
    	String name = "name";
    	registryType.setName(name);
    	registryType.setDefaultHost("host");
    	registryType.setDefaultPort(4);
    	registryType = registryTypeService.insert(registryType);
    	assertEquals(name, registryType.getName());
    	assertEquals(collectionSize+1, registryTypeRepository.count());
    }
}
