package org.dockhouse.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

public class ValidationTest<Entity> {
	
	@Inject
	private Validator validator;
	
	protected Validator validator() { 
		return validator;
	}
	
	public void assertIsValid(Entity dto) {
		Set<ConstraintViolation<Entity>> errors = validator().validate(dto, Default.class);
		assertTrue(errors.isEmpty());
	}
	
	public void assertIsInvalid(Entity dto) {
		Set<ConstraintViolation<Entity>> errors = validator().validate(dto, Default.class);
		assertFalse(errors.isEmpty());
	}
	
	public void assertFieldIsValid(Entity dto, String field) {
		Set<ConstraintViolation<Entity>> errors = validator().validateProperty(dto, field, Default.class);
		assertTrue(errors.isEmpty());
	}
	
	public void assertFieldIsInvalid(Entity dto, String field) {
		Set<ConstraintViolation<Entity>> errors = validator().validateProperty(dto, field, Default.class);
		assertFalse(errors.isEmpty());
	}
	
	public String stringOfLength(int length) {
		String string = "";
		for (int i = 0 ; i < length ; ++i)
			string += "a";
		return string;
	}
}