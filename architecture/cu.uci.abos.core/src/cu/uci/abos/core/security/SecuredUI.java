package cu.uci.abos.core.security;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for mark user interfaces that require security verification.
 * 
 * @author sergio
 * 
 */

@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface SecuredUI {

	String permission() default "";
}
