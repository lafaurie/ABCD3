package cu.uci.abos.core.security;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for class members that needs permissions granted in security
 * context.
 * 
 * @author sergio
 * 
 */

@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Documented
public @interface SecurityPermision {

	String permission() default "";

}
