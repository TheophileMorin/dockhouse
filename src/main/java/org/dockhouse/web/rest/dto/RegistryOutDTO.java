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


public class RegistryOutDTO {

    private String id;

    private String name;

    private String host;

    private int port;

    private String protocol;

    private RegistryTypeOutDTO registryType;
    
    public String getId() {
    	return id;
    }

    public void setId(String id) {
    	this.id = id;
    }

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

    public RegistryTypeOutDTO getRegistryType() {
    	return registryType;
    }

    public void setRegistryType(RegistryTypeOutDTO registryType) {
    	this.registryType = registryType;
    }

    @Override
    public String toString() {
        return "RegistryOutDTO{" +
	    "id='" + id + '\'' +
	    ", name='" + name + '\'' +
	    ", host='" + host + '\'' +
	    ", port='" + port + '\'' +
	    ", protocol='"     + protocol     + '\'' +
	    ", registryType='" + registryType + '\'' +
	    "}";
    }
}