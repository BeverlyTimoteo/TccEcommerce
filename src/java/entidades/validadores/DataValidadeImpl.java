/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.validadores;

import java.util.Calendar;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Beverly
 */
public class DataValidadeImpl implements ConstraintValidator<DataValidadeCC, Date> {

    @Override
    public void initialize(DataValidadeCC constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        Date actualDate = Calendar.getInstance().getTime();
        if (value == null) {
            return true;
        }
        return actualDate.before(value);
    }
}
