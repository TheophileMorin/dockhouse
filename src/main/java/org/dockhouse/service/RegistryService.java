/**
 *  Copyright (C) 2015  Dockhouse project org. ( http://dockhouse.github.io/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dockhouse.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.service.registryapi.DockerRegistryAPIServiceV1;
import org.dockhouse.service.registryapi.RegistryAPIService;
import org.dockhouse.web.rest.dto.RegistryInDTO;
import org.dockhouse.web.rest.dto.RegistryOutDTO;
import org.dockhouse.web.rest.dto.RegistryStatusDTO;
import org.dockhouse.web.rest.dto.mapping.RegistryInDTOMapper;
import org.dockhouse.web.rest.dto.mapping.RegistryOutDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing registries.
 */
@Service
public class RegistryService {

    private final Logger log = LoggerFactory.getLogger(RegistryService.class);

    @Inject
    private RegistryRepository registryRepository;

    @Inject
    private RegistryTypeRepository registryTypeRepository;

    @Inject
    private RegistryOutDTOMapper registryOutDTOMapper;

    @Inject
    private RegistryInDTOMapper registryInDTOMapper;

    
    public List<RegistryOutDTO> getAll() {
    	return registryRepository.findAll()
    							 .stream()
    							 .map(this::createRegistryOutDTO)
    							 .collect(Collectors.toList());
    }
    
    public Optional<RegistryOutDTO> getOne(String id) {
    	return Optional.ofNullable(registryRepository.findOne(id))
    			       .map(this::createRegistryOutDTO);
    }
    
    public RegistryOutDTO createRegistryOutDTO(Registry registry) {
    	RegistryType registryType = registryTypeRepository.findOne(registry.getRegistryTypeId());
    	return registryOutDTOMapper.createDTO(registry, registryType);
    }
    
    public RegistryOutDTO insert(RegistryInDTO registryInDTO) {
    	Registry registry = registryInDTOMapper.createRegistry(registryInDTO);
    	registry = registryRepository.save(registry);
    	return createRegistryOutDTO(registry);
    }
    
    public RegistryOutDTO upsert(RegistryInDTO registryInDTO, String id) {
    	Registry registry = registryInDTOMapper.createRegistry(registryInDTO);
    	registry.setId(id);
    	registry = registryRepository.save(registry);
    	return createRegistryOutDTO(registry);
    }
    
    public RegistryType getRegistryTypeOf(Registry registry) {
    	return registryTypeRepository.findOne(registry.getRegistryTypeId());
    }
    
    public RegistryStatusDTO getStatus(String id){
    	RegistryStatusDTO registryStatusDTO = new RegistryStatusDTO();
    	registryStatusDTO.setStatus(RegistryStatusDTO.STATUT_OFFLINE);
    	
    	//MOCK TODO remove
        Registry registry = new Registry();
        registry.setHost("ns507652.ip-192-99-10.net");
        registry.setPort(5000);
        registry.setProtocol("http");  
        RegistryAPIService registreAPI = new DockerRegistryAPIServiceV1(); 
        //ENDMOCK
        
        //Registry registry = registryRepository.findOne(id);
        if(registry != null){
        	//RegistryAPIServiceFactory factory = new RegistryAPIServiceFactory();
            //RegistryAPIService registreAPI = factory.get(registry);
            if(registreAPI.isAvailable(registry)){
            	registryStatusDTO.setStatus(RegistryStatusDTO.STATUT_ONLINE);
            }
        }
        return registryStatusDTO;  
    }
    
    public String getDetails(String id){
    	String details = "";
    	
    	//MOCK TODO remove
        Registry registry = new Registry();
        registry.setHost("ns507652.ip-192-99-10.net");
        registry.setPort(5000);
        registry.setProtocol("http");  
        RegistryAPIService registreAPI = new DockerRegistryAPIServiceV1(); 
        //ENDMOCK
        
        //Registry registry = registryRepository.findOne(id);
        if(registry != null){
        	//RegistryAPIServiceFactory factory = new RegistryAPIServiceFactory();
            //RegistryAPIService registreAPI = factory.get(registry);
        	details = registreAPI.getDetails(registry);
        }
        return details;
    }
}
