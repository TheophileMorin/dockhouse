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

import java.util.List;

import org.dockhouse.domain.Registry;
import org.dockhouse.web.rest.dto.RegistryImageDTO;

/**
 * Class to interact with the registry APIs (e.g, Docker)
 */
public interface RegistryAPIService {
	
	/**
	 * Test if remote registry is available from the network.
	 * @param registry The registry to test the availability
	 * @return true if given registry is available
	 */
	public boolean isAvailable(Registry registry);
	
	/**
	 * Returns general information about the registry
	 * @param registry The registry to get the information
	 * @return String The message containing the information (JSON)
	 */
	public String getDetails(Registry registry);
	
	/**
	 * Returns a list of all the images of the registry
	 * @param registry The registry to get the information
	 * @return List The list of all the registries
	 */
	public List<RegistryImageDTO> getImages(Registry registry);
}
