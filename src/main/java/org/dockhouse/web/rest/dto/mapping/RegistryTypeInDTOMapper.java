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
package org.dockhouse.web.rest.dto.mapping;

import org.dockhouse.domain.RegistryType;
import org.dockhouse.web.rest.dto.RegistryTypeInDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class RegistryTypeInDTOMapper {

	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<RegistryTypeInDTO, RegistryType>() {
		    protected void configure() {
		        skip().setId(null);
		        skip().setCreatedBy(null);
		        skip().setCreatedDate(null);
		        skip().setLastModifiedBy(null);
		        skip().setLastModifiedDate(null);
		    }	
		});
		return modelMapper;
	}
	public RegistryType createRegistryType(RegistryTypeInDTO registryTypeInDTO) {
		return mapper().map(registryTypeInDTO, RegistryType.class);
	}

}
