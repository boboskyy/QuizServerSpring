package pl.nanocoder.testserver.validation;

import pl.nanocoder.testserver.web.dto.ChangePasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final ChangePasswordDto personDto = (ChangePasswordDto)o;

        return personDto.getPassword().equals(personDto.getConfirmPassword());
    }
}
