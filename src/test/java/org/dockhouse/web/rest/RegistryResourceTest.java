package org.dockhouse.web.rest;

import org.dockhouse.Application;
import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.repository.RegistryTypeRepository;
import org.dockhouse.service.RegistryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by BWI on 13/03/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RegistryResourceTest {

    @Inject
    protected WebApplicationContext webApplicationContext;

    @Inject
    private RegistryRepository registryRepository;
    @Inject
    private RegistryTypeRepository typeRepository;
    @Inject
    private RegistryService registryService;

    private MockMvc restRegistryMockMvc;

    private Registry registry;
    private RegistryType type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegistryResource registryMockResource = new RegistryResource();
        ReflectionTestUtils.setField(registryMockResource, "registryRepository", registryRepository);
        ReflectionTestUtils.setField(registryMockResource, "registryService"   , registryService);
//        ReflectionTestUtils.setField(registryMockResource, "validator", validator);

        this.restRegistryMockMvc = MockMvcBuilders.standaloneSetup(registryMockResource).build();


        // Create a registry type
        this.type = new RegistryType();
        this.type.setId("1");
        this.type.setName("Docker 1");
        this.type.setPort(5000);
        this.type.setHost("localhost");
        this.type.setLogo("docker.png");
        this.type.setPublic(false);
        this.typeRepository.save(type);

        // Create the registry
        this.registry = new Registry();
        this.registry.setId("abcd");
        this.registry.setName("Test1");
        this.registry.setPort(8080);
        this.registry.setProtocol("http");
        this.registry.setHost("localhost");
        this.registry.setType(this.type);
    }

    @Before
    public void initTest() {
        this.registryRepository.deleteAll();
    }

    @Test
    public void testCreateRegistry() throws Exception {
        // Validate the database is empty
        assertThat(registryRepository.findAll()).hasSize(0);

        this.restRegistryMockMvc.perform(post("/api/registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonString(registry)))
            .andExpect(status().isCreated());


        // Validate the Author in the database
        Registry registryToTest = registryRepository.findOne(this.registry.getId());
        assertThat(registryToTest.getName()).isEqualTo("Test1");
        assertThat(registryToTest.getType().equals(this.type));
    }

    @Test
    public void testGetRegistry() throws Exception {
        // Validate the database is empty and fill one registry
        assertThat(registryRepository.findAll()).hasSize(0);
        this.registryRepository.save(this.registry);

        this.restRegistryMockMvc.perform(get("/api/registries/{id}", this.registry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(this.registry.getId()))
            .andExpect(jsonPath("$.type.id").value(this.type.getId()));
    }

    @Test
    public void testGetAllRegistries() throws Exception {
        // Validate the database is empty and fill 2 registries
        assertThat(registryRepository.findAll()).hasSize(0);
        this.registryRepository.save(this.registry);
        this.registry.setId("efg");
        this.registry.setName("Test2");
        this.registryRepository.save(this.registry);

        this.restRegistryMockMvc.perform(get("/api/registries"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[1]id").value(this.registry.getId()))
            .andExpect(jsonPath("$.[1]type.id").value(this.type.getId()));

    }
}
