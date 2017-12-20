package org.terasology.entitySystem.systems;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterSystem {

    String[] requiresOptional() default {};

    RegisterMode value() default RegisterMode.ALWAYS;

}