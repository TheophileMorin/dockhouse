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

import javax.inject.Inject;

import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.web.rest.dto.RegistryDTO;
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

    public RegistryDTO createRegistryDTO(Registry registry) {
    	RegistryDTO registryDTO = new RegistryDTO();
    	registryDTO.setId(registry.getId());
    	registryDTO.setName(registry.getName());
    	registryDTO.setHost(registry.getHost());
    	registryDTO.setPort(registry.getPort());
    	registryDTO.setProtocol(registry.getProtocol());
    	RegistryType registryType = registryTypeRepository.findOne(registry.getRegistryTypeId());
    	registryDTO.setRegistryType(registryType);
    	return registryDTO;
    }
}
