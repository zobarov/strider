/**
 * 
 */
package com.gav.job17.strider.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author alexeygerasimenko
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) 
@Documented 
public @interface Loggable {
 //for slf4j
} 
