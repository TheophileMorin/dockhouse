package org.dockhouse.web.rest.dto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

public class DTOValidationTest<DTO> {
	
	@Inject
	private Validator validator;
	
	public void assertIsValid(DTO dto) {
		Set<ConstraintViolation<DTO>> errors = validator.validate(dto, Default.class);
		assertTrue(errors.isEmpty());
	}
	
	public void assertFieldIsValid(DTO dto, String field) {
		Set<ConstraintViolation<DTO>> errors = validator.validateProperty(dto, field, Default.class);
		assertTrue(errors.isEmpty());
	}
	
	public void assertFieldIsInvalid(DTO dto, String field) {
		Set<ConstraintViolation<DTO>> errors = validator.validateProperty(dto, field, Default.class);
		assertFalse(errors.isEmpty());
	}
	
	public String stringOfLength(int length) {
		String string = "";
		for (int i = 0 ; i < length ; ++i)
			string += "a";
		return string;
	}
}