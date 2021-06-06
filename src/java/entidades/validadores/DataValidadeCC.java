/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Beverly
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DataValidadeImpl.class)
public @interface DataValidadeCC {
   String message() default "A data de validade esta vencida";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
    
}
