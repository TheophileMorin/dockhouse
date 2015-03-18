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
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.web.rest.dto.RegistryTypeInDTO;
import org.dockhouse.web.rest.dto.RegistryTypeOutDTO;
import org.dockhouse.web.rest.dto.mapping.RegistryTypeInDTOMapper;
import org.dockhouse.web.rest.dto.mapping.RegistryTypeOutDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing types of registries.
 */
@Service
public class RegistryTypeService {

    private final Logger log = LoggerFactory.getLogger(RegistryTypeService.class);

    @Inject
    private RegistryTypeRepository registryTypeRepository;

    @Inject
    private RegistryTypeOutDTOMapper registryTypeOutDTOMapper;

    @Inject
    private RegistryTypeInDTOMapper registryTypeInDTOMapper;
    
    public List<RegistryTypeOutDTO> getAll() {
    	return registryTypeRepository.findAll()
    							 	 .stream()
    							 	 .map(registryTypeOutDTOMapper::createDTO)
    							 	 .collect(Collectors.toList());
    }
    
    public Optional<RegistryTypeOutDTO> getOne(String id) {
    	return Optional.ofNullable(registryTypeRepository.findOne(id))
    			       .map(registryTypeOutDTOMapper::createDTO);
    }
    
    public void save(RegistryTypeInDTO registryTypeInDTO) {
    	RegistryType registryType = registryTypeInDTOMapper.createRegistryType(registryTypeInDTO);
    	registryTypeRepository.save(registryType);
    }
}
