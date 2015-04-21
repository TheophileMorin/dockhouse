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

import org.dockhouse.domain.Registry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Qualifier("DockerRegistryAPIServiceV1")
public class DockerRegistryAPIServiceV1 implements RegistryAPIService {
	private final String apiVersion = "v1"; 
	private final String pingCall = "_ping";
	
	private RestTemplate restTemplate;
	
	public DockerRegistryAPIServiceV1() {
		this.restTemplate = new RestTemplate();
	}

	@Override
	public boolean isAvailable(Registry registry) {
		String url = getURL(registry, pingCall);

		try{
			ResponseEntity<String> result = this.restTemplate.getForEntity(url, String.class);
			return result.getStatusCode() == HttpStatus.OK;
		}
		catch(RestClientException e){
			return false;
		}
	}

	@Override
	public String getDetails(Registry registry) {
		String url = getURL(registry, pingCall);

		try{
			ResponseEntity<String> result = this.restTemplate.getForEntity(url, String.class);
			return result.getBody();
		}
		catch(RestClientException e){
			return new String();
		}
	}

	/**
	 * Returns the formatted URL in order to call the webservice
	 * @param registry Registry to pull the information from
	 * @param call String the specific call
	 * @return String the formatted URL for the request
	 */
	private String getURL(Registry registry, String call){
		return registry.getProtocol()
				+ "://"
				+ registry.getHost()
				+ ":"
				+ registry.getPort()
				+ "/"
				+ apiVersion
				+ "/"
				+ call;
	}
	
	public void setRestTemplate(RestTemplate restTemp){
		this.restTemplate = restTemp;
	}
}
