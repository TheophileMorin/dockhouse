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
package org.dockhouse.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.dockhouse.domain.Registry;
import org.dockhouse.repository.RegistryRepository;
import org.dockhouse.service.RegistryService;
import org.dockhouse.web.rest.dto.RegistryInDTO;
import org.dockhouse.web.rest.dto.RegistryOutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing registries.
 */
@RestController
@RequestMapping("/api")
public class RegistryResource {

    private final Logger log = LoggerFactory.getLogger(RegistryResource.class);

    @Inject
    private RegistryRepository registryRepository;

    @Inject
    private RegistryService registryService;

    @Inject
    private Validator validator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
    
    /**
     * GET  /registries -> get all registries.
     */
    @RequestMapping(value = "/registries",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<List<RegistryOutDTO>> getRegistries() {
        log.debug("REST request to get all Registries");
        return new ResponseEntity<>(registryService.getAll(), 
        						    HttpStatus.OK);
    }

    /**
     * GET  /registries/:id -> get the "id" registry.
     */
    @RequestMapping(value = "/registries/{id}",
		    method = RequestMethod.GET,
		    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<RegistryOutDTO> getRegistry(@PathVariable String id) {
        log.debug("REST request to get Registry : {}", id);
        return registryService.getOne(id)
            .map(registry -> new ResponseEntity<>(registry, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST  /registries -> Create a new registry.
     */
    @RequestMapping(value = "/registries",
		    method = RequestMethod.POST,
		    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistryOutDTO> createRegistry(@RequestBody @Valid RegistryInDTO registryInDTO) {
        log.debug("REST request to save Registry : {}", registryInDTO);        	
        RegistryOutDTO registryOutDTO = registryService.save(registryInDTO);
        return new ResponseEntity<RegistryOutDTO>(registryOutDTO, HttpStatus.CREATED);
    }

    /**
     * PUT  /registries/:id -> update an registry.
     */
    @RequestMapping(value = "/registries/{id}",
		    method = RequestMethod.PUT,
		    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistryOutDTO> updateRegistry(@RequestBody @Valid RegistryInDTO registryInDTO,
    						                             @PathVariable String id) {
        log.debug("REST request to save Registry : {}", registryInDTO);        	
        RegistryOutDTO registryOutDTO = registryService.save(registryInDTO);
        return new ResponseEntity<RegistryOutDTO>(registryOutDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /registries/:id -> delete the "id" registry.
     */
    @RequestMapping(value = "/registries/{id}",
		    method = RequestMethod.DELETE,
		    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed
    public void deleteRegistry(@PathVariable String id) {
        log.debug("REST request to delete Registry : {}", id);
        registryRepository.delete(id);
    }
}