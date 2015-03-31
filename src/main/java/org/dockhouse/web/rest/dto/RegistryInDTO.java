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
package org.dockhouse.web.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.dockhouse.domain.validation.CheckReference;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Field;

public class RegistryInDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    private String host;

    @Range(min = 0, max = 65535)
    private int port;

    @Size(max = 10)
    private String protocol;

    @NotNull
    @CheckReference
    @Field("registry_type_id")
    private String registryTypeId;
    
    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public String getHost() {
    	return host;
    }

    public void setHost(String host) {
    	this.host = host;
    }

    public int getPort() {
    	return port;
    }

    public void setPort(int port) {
    	this.port = port;
    }

    public String getProtocol() {
    	return protocol;
    }

    public void setProtocol(String protocol) {
    	this.protocol = protocol;
    }

    public String getRegistryTypeId() {
    	return registryTypeId;
    }

    public void setRegistryTypeId(String registryTypeId) {
    	this.registryTypeId = registryTypeId;
    }

    @Override
    public String toString() {
        return "RegistryInDTO{" +
	    "name='" + name + '\'' +
	    ", host='" + host + '\'' +
	    ", port='" + port + '\'' +
	    ", protocol='"     + protocol     + '\'' +
	    ", registryTypeId='" + registryTypeId + '\'' +
	    "}";
    }
}