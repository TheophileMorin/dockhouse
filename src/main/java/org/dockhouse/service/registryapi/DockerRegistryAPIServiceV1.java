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

	@Override
	public boolean isAvailable(Registry registry) {

		RestTemplate restTemplate = new RestTemplate();
		String url = registry.getProtocol()
				+ "://"
				+ registry.getHost()
				+ ":"
				+ registry.getPort()
				+ "/"
				+ "v1"
				+ "/_ping";
		//TODO handle format got from data

		try{
			ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
			return result.getStatusCode() == HttpStatus.OK;
		}
		catch(RestClientException e){
			return false;
		}
	}
}
