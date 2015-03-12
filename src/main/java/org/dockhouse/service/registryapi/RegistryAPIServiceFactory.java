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
package org.dockhouse.service.registryapi;

import javax.inject.Inject;

import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.service.RegistryService;
import org.springframework.stereotype.Service;

/**
 * Factory of RegistryAPIService
 */
@Service
public class RegistryAPIServiceFactory {

	@Inject
	private DockerRegistryAPIService dockerRegistryAPIService;

	@Inject
	private RegistryService registryService;
	
	/**
	 * Return RegistryAPIService corresponding to given registry type.
	 * 
	 * @param registryTypeName Name of the registry type
	 * @return RegistryAPIService of given registry type
	 */
	public RegistryAPIService get(String registryTypeName) {
		switch (registryTypeName) {
			case "Docker": return dockerRegistryAPIService;
			default: 
				throw new IllegalArgumentException("RegistryAPIService for given id does not exist.");
		}
	}
	
	/**
	 * Return RegistryAPIService corresponding to given registry.
	 * 
	 * @param registry Registry to find the RegistryAPIService for
	 * @return RegistryAPIService of given registry 
	 */
	public RegistryAPIService get(Registry registry) {
		RegistryType registryType = registryService.getRegistryTypeOf(registry);
		return get(registryType.getName());
	}
	
}
