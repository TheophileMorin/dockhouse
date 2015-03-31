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

import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Field;

public class RegistryTypeInDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    
    private String logo;
    
    @NotNull
    @Field("default_host")
    private String defaultHost;
    
    @Range(min = 0, max = 65535)
    @Field("default_port")
    private int defaultPort;
    
    private boolean isPublic;

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

	public String getDefaultHost() {
		return defaultHost;
	}

	public void setDefaultHost(String defaultHost) {
		this.defaultHost = defaultHost;
	}

	public int getDefaultPort() {
		return defaultPort;
	}

	public void setDefaultPort(int defaultPort) {
		this.defaultPort = defaultPort;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
    @Override
    public String toString() {
        return "RegistryTypeInDTO{" +
	    ", name='" + name + '\'' +
	    ", logo='" + logo + '\'' +
	    ", host='" + defaultHost + '\'' +
	    ", port='" + defaultPort + '\'' +
	    ", isPublic='" + isPublic + '\'' +
	    "}";
    }
}
