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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Factory of RegistryAPIService
 */
@Service
public class RegistryAPIServiceFactory {

	@Inject
	@Qualifier("DockerRegistryAPIServiceV1")
	private RegistryAPIService dockerRegistryAPIServiceV1;
	
	public static final String DOCKER_REGISTRY_TYPE_NAME = "Docker";
	public static final String DOCKER_V1_REGISTRY_TYPE_VERSION = "v1";

	/**
	 * Return RegistryAPIService corresponding to given registry type.
	 *
	 * @param registryTypeName Name of the registry type
	 * @param registryApiVersion Version of the registry
	 * @return RegistryAPIService of given registry type
	 * @throws IllegalArgumentException
	 */
	public RegistryAPIService get(String registryTypeName, String registryApiVersion) throws IllegalArgumentException {
		switch (registryTypeName) {
			case DOCKER_REGISTRY_TYPE_NAME:
				try{
					return getDockerRegistryByApiVersion(registryApiVersion);
				}
				catch (IllegalArgumentException e) {
					throw e;
				}
			default:
				throw new IllegalArgumentException("RegistryAPIService for given id does not exist.");
		}
	}

	/**
	 * Return RegistryAPIService corresponding to given registry.
	 *
	 * @param registry Registry to find the RegistryAPIService for
	 * @return RegistryAPIService of given registry
	 * @throws IllegalArgumentException
	 */
	public RegistryAPIService get(Registry registry) throws IllegalArgumentException {
		try{
			return get(registry.getRegistryType().getName(), registry.getApiVersion());
		}
		catch(IllegalArgumentException e){
			throw e;
		}
	}
	
	/**
	 * Return DockerRegistryAPIService corresponding to given api version.
	 *
	 * @param registryApiVersion Version of the registry
	 * @return RegistryAPIService of given registry type and api version
	 * @throws IllegalArgumentException
	 */
	public RegistryAPIService getDockerRegistryByApiVersion(String registryApiVersion) throws IllegalArgumentException {
		switch (registryApiVersion) {
		case DOCKER_V1_REGISTRY_TYPE_VERSION:
			return dockerRegistryAPIServiceV1;
		default:
			throw new IllegalArgumentException("RegistryAPIService for given id and api does not exist.");
		}
	}
}
