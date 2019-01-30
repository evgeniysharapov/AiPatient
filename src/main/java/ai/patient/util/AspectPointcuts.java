/**
 * 
 */
package ai.patient.util;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Describes all the places where we could stick our aspects to.
 * 
 * @author evgeniy.sharapov
 *
 */

public class AspectPointcuts {

	@Pointcut("execution(* ai.patient.api.*.*(..))")
	public void apiLayerMethods(){}	

	@Pointcut("execution(* ai.patient.data.*.*(..))")
	public void dataLayerMethods(){}

}
