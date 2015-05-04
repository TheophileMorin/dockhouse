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
import org.dockhouse.domain.RegistryType;
import org.dockhouse.populator.RegistryTypePopulator;
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
    
    @Inject
	private RegistryTypePopulator registryTypePopulator;
    
    @Before
    public void setup() {
        registryTypeRepository.deleteAll();
        registryTypePopulator.populate();
        
        registryType1 = registryTypeRepository.findAll().get(0);
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
}
