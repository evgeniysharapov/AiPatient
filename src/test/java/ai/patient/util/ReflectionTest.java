package ai.patient.util;

import java.lang.reflect.Field;

import javax.persistence.Id;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import ai.patient.model.Address;

public class ReflectionTest {

	@Test
	public void test() {
		Address a = new Address();
		System.out.println(a);
		for (Field f :  Address.class.getDeclaredFields()) {
			System.out.println(f);
			if(f.getAnnotation(Id.class) != null) {
				System.out.println("Id: " + f);
				ReflectionUtils.makeAccessible(f);
				ReflectionUtils.setField(f, a, 1L);
			}
		}
		System.out.println(a);
				
	}

}
