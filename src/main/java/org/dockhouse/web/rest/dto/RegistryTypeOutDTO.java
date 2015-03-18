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

import org.dockhouse.domain.RegistryType;

public class RegistryTypeOutDTO {

    private String id;

    private String name;
    
    private String logo;
    
    private String host;
    
    private int port;
    
    private boolean isPublic;

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
    @Override
    public String toString() {
        return "RegistryTypeOutDTO{" +
	    "id='" + id + '\'' +
	    ", name='" + name + '\'' +
	    ", logo='" + logo + '\'' +
	    ", host='" + host + '\'' +
	    ", port='" + port + '\'' +
	    ", isPublic='" + isPublic + '\'' +
	    "}";
    }
}
