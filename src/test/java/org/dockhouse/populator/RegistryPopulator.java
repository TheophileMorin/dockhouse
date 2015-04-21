package org.dockhouse.populator;

import javax.inject.Inject;

import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistryPopulator {

    @Inject
    private RegistryRepository registryRepository;	

    @Inject
    private RegistryTypeRepository registryTypeRepository;	
		
	public void populate() {
	    RegistryType registryType = registryTypeRepository.findAll().get(0);
    	 
        Registry registry1 = new Registry();
        registry1.setName("name1");
        registry1.setHost("host");
        registry1.setPort(1111);
        registry1.setProtocol("http");
		registry1.setApiVersion("v1");
        registry1.setRegistryType(registryType);
     
        Registry registry2 = new Registry();
        registry2.setName("name2");
        registry2.setHost("host2");
        registry2.setPort(2222);
        registry2.setProtocol("https");
		registry2.setApiVersion("v2");
        registry2.setRegistryType(registryType);
        
	    registryRepository.save(registry1);
	    registryRepository.save(registry2);
	}
}
