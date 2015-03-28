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
import org.dockhouse.web.rest.dto.RegistryInDTO;
import org.dockhouse.web.rest.dto.RegistryOutDTO;
import org.dockhouse.web.rest.dto.RegistryTypeInDTO;
import org.dockhouse.web.rest.dto.RegistryTypeOutDTO;
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
        registryType.setHost("host");
        registryType.setPort(2222);
        registryType.setPublic(false);
        registryTypeRepository.save(registryType);
        
        registry1 = new Registry();
        registry1.setName("name2");
        registry1.setProtocol("http");
        registry1.setHost("host");
        registry1.setPort(2222);
        registry1.setRegistryTypeId(registryType.getId());
        registryRepository.save(registry1);
        
        registry2 = new Registry();
        registry2.setName("name3");
        registry2.setProtocol("http");
        registry2.setHost("host");
        registry2.setPort(2222);
        registry2.setRegistryTypeId(registryType.getId());
        registryRepository.save(registry2);
    }

    @Test
    public void getAllTest() {
    	List<RegistryOutDTO> registryOutDTOs = registryService.getAll();
    	assertEquals(2, registryOutDTOs.size());
    }
    
    @Test
    public void getOneTest() {
    	Optional<RegistryOutDTO> registryOutDTO = registryService.getOne(registry1.getId());
    	assertTrue(registryOutDTO.isPresent());
    	assertEquals(registry1.getId(), registryOutDTO.get().getId());
    
    	registryOutDTO = registryService.getOne("0");
    	assertFalse(registryOutDTO.isPresent());
    }
    
    @Test
    public void createRegistryOutDTOTest() {
    	RegistryOutDTO registryOutDTO = registryService.createRegistryOutDTO(registry1);
    	assertEquals(registry1.getId(), registryOutDTO.getId());
    	assertEquals(registryType.getId(), registryOutDTO.getRegistryType().getId());
    }
    
    @Test
    public void insertTest() {
    	long collectionSize = registryRepository.count();
    	RegistryInDTO registryInDTO = new RegistryInDTO();
    	registryInDTO.setName("new");
    	registryInDTO.setRegistryTypeId(registryType.getId());
    	RegistryOutDTO registryOutDTO = registryService.insert(registryInDTO);
    	assertEquals(registryInDTO.getName(), registryOutDTO.getName());
    	assertEquals(collectionSize+1, registryRepository.count());
    }
    
    
    @Test
    public void upsertInsertTest() {
    	long collectionSize = registryRepository.count();
    	RegistryInDTO registryInDTO = new RegistryInDTO();
    	registryInDTO.setRegistryTypeId(registryType.getId());
    	registryInDTO.setName("new");
    	RegistryOutDTO registryOutDTO = registryService.upsert(registryInDTO, "id");
    	assertEquals(registryInDTO.getName(), registryOutDTO.getName());
    	assertEquals("id", registryOutDTO.getId());
    	assertEquals(collectionSize+1, registryRepository.count());
    }
    
    @Test
    public void upsertUpdateTest() {
    	long collectionSize = registryRepository.count();
    	RegistryInDTO registryInDTO = new RegistryInDTO();
    	registryInDTO.setRegistryTypeId(registryType.getId());
    	registryInDTO.setName("update");
    	RegistryOutDTO registryOutDTO = registryService.upsert(registryInDTO, registry1.getId());
    	assertEquals(registryInDTO.getName(), registryOutDTO.getName());
    	assertEquals(collectionSize, registryRepository.count());
    }
}
