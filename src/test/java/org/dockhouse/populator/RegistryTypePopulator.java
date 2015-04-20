package org.dockhouse.populator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistryTypePopulator {

    @Inject
    private RegistryTypeRepository registryTypeRepository;
	
	public void populate() {
		
	    RegistryType registryType1 = new RegistryType();
	    registryType1.setName("name1");
	    registryType1.setDefaultHost("host1");
	    registryType1.setDefaultPort(1111);
	    registryType1.setLogo("http://example.com/logo1.png");
	    registryType1.setPublic(true);
    	List<String> versions = new ArrayList<String>();
		versions.add("V1");
		registryType1.setApiVersions(versions);
    	 
	    RegistryType registryType2 = new RegistryType();
	    registryType2.setName("name2");
	    registryType2.setDefaultHost("host2");
	    registryType2.setDefaultPort(2222);
	    registryType2.setLogo("http://example.com/logo2.png");
	    registryType2.setPublic(false);
		registryType2.setApiVersions(versions);
		
	    registryTypeRepository.save(registryType1);
	    registryTypeRepository.save(registryType2);
	}
}
