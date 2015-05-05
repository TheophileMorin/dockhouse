package org.dockhouse.web.rest.dto;

/**
 * 
 * Response for an image delete
 */
public class RegistryDeleteImageDTO {
	
	private boolean deleted;
	
	public boolean isDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(boolean del){
		this.deleted = del;
	}

}
