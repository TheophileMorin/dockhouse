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
package org.dockhouse.domain.validation;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.dockhouse.domain.Registry;
import org.dockhouse.domain.RegistryType;
import org.dockhouse.service.RegistryTypeService;

public class RegistryTypeReferenceValidator implements ConstraintValidator<ValidateRegistryTypeReference, Registry> {

 	@Inject
    private RegistryTypeService registryTypeService;

    private ValidateRegistryTypeReference validateRegistryTypeReference;

    @Override
    public void initialize(ValidateRegistryTypeReference validateRegistryTypeReference) {
        this.validateRegistryTypeReference = validateRegistryTypeReference;
    }

    @Override
    public boolean isValid(Registry registry, ConstraintValidatorContext cvc) {
		String registryTypeId = registry.getRegistryType().getId();
    	Optional<RegistryType> registryType = registryTypeService.getOne(registryTypeId);
		if (registryType.isPresent()) {
			registry.setRegistryType(registryType.get());
		}
		return registryType.isPresent();
    }
}