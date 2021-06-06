/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Beverly
 */
public class CEPValidoImpl implements ConstraintValidator<CEPValido, String> {

    @Override
    public void initialize(CEPValido constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pater = Pattern.compile("[0-9]{5}-[0-9]{3}");
        Matcher mat = pater.matcher(value);
        return mat.matches();
    }
}
