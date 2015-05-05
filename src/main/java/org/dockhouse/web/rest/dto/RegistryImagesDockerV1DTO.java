package org.dockhouse.web.rest.dto;

import java.util.List;

public class RegistryImagesDockerV1DTO {
	
	private int num_results;
	
	private String query;
	
	private List<RepresentationImagesDockerV1DTO> results;

	public List<RepresentationImagesDockerV1DTO> getResults() {
		return results;
	}

	public void setResults(List<RepresentationImagesDockerV1DTO> results) {
		this.results = results;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getNum_results() {
		return num_results;
	}

	public void setNum_results(int num_results) {
		this.num_results = num_results;
	}
			
}
