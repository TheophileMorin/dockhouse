package org.dockhouse.service.registryapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.dockhouse.Application;
import org.dockhouse.domain.Registry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Test class for DockerRegistryAPIServiceV1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DockerRegistryAPIServiceV1Test {

	@Inject
	private DockerRegistryAPIServiceV1 dockerRegistryAPIServiceV1;
	
	private Registry registry;
	
	@Mock
	private RestTemplate restTemplateMock;
	
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		
    	registry = new Registry();
    	registry.setApiVersion("v1");
    	registry.setProtocol("http");
    	registry.setHost("localhost");
    	registry.setPort(5000);
    	dockerRegistryAPIServiceV1 = new DockerRegistryAPIServiceV1();
    	
    	dockerRegistryAPIServiceV1.setRestTemplate(restTemplateMock);
    }
	
	@Test
	public void isAvailable() {
		ResponseEntity<String> responseMocked = new ResponseEntity<String>(HttpStatus.OK); 
		when(restTemplateMock.getForEntity(anyString(), eq(String.class))).thenReturn(responseMocked);
		assertThat(dockerRegistryAPIServiceV1.isAvailable(registry)).isEqualTo(true);
	}
	
	@Test
	public void isNotAvailable() {
		ResponseEntity<String> responseMocked = new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE); 
		when(restTemplateMock.getForEntity(anyString(), eq(String.class))).thenReturn(responseMocked);
		assertThat(dockerRegistryAPIServiceV1.isAvailable(registry)).isEqualTo(false);
	}
	
	@Test
	public void isNotAvailableWrongAdress(){
		when(restTemplateMock.getForEntity(anyString(), eq(String.class))).thenThrow(new RestClientException(new String()));
		assertThat(dockerRegistryAPIServiceV1.isAvailable(registry)).isEqualTo(false);
	}
	
	@Test
	public void getDetails(){
		String details = new String("Response");
		ResponseEntity<String> responseMocked = new ResponseEntity<String>(details, HttpStatus.NOT_ACCEPTABLE);
		when(restTemplateMock.getForEntity(anyString(), eq(String.class))).thenReturn(responseMocked);
		assertThat(dockerRegistryAPIServiceV1.getDetails(registry)).isEqualTo(details);
	}
	
	@Test
	public void getNoDetailsWrongAdress(){
		when(restTemplateMock.getForEntity(anyString(), eq(String.class))).thenThrow(new RestClientException(new String()));
		assertThat(dockerRegistryAPIServiceV1.getDetails(registry)).isEqualTo(new String());
	}

}
