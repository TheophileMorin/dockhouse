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
package org.dockhouse.domain;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A type of registry.
 */
@Document(collection = "registry_types")
public class RegistryType implements Serializable {

    @Id
    private String id;

    @NotNull
    @Pattern(regexp = "^[\\w\\s]*$")
    @Size(min = 1, max = 50)
    @Indexed(unique=true)
    private String name;

    private String logo;

    private String host;

    @Range(min = 0, max = 65535)
    private int port;

    @NotNull
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegistryType registryType = (RegistryType) o;

        if (id != null ? !id.equals(registryType.id) : registryType.id != null)
        	return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RegistryType{" +
	    "id='" + id + '\'' +
	    ", name='" + name + '\'' +
	    ", logo='" + logo + '\'' +
	    ", host='" + host + '\'' +
	    ", port='" + port + '\'' +
	    ", isPublic='" + isPublic + '\'' +
	    "}";
    }
}
