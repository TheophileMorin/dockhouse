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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A registry.
 */
@Document(collection = "registries")
public class Registry implements Serializable {

    @Id
    private String id;

    @NotNull
    @Pattern(regexp = "^[\\w\\s]*$")
    @Size(min = 1, max = 50)
    @Indexed(unique=true)
    private String name;

    private String host;

    @Range(min = 0, max = 65535)
    private int port;

    @Size(max = 10)
    private String protocol;

    @NotNull
    @DBRef
    @Field("type")
    private RegistryType type;

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

    public RegistryType getType() {
        return type;
    }

    public void setType(RegistryType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Registry registry = (Registry) o;

        if (id != null ? !id.equals(registry.id) : registry.id != null)
        	return false;

        if(!registry.getType().equals(type)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Registry{" +
	    "id='" + id + '\'' +
	    ", name='" + name + '\'' +
	    ", host='" + host + '\'' +
	    ", port='" + port + '\'' +
	    ", protocol='"       + protocol     + '\'' +
	    ", type='[" + type.toString() + "]\'" +
	    "}";
    }
}
