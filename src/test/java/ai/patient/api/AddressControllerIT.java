/**
 * 
 */
package ai.patient.api;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.patient.data.AddressRepository;
import ai.patient.model.Address;

/**
 * @author evgeniy.sharapov
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	AddressRepository repo;
	
	private Address a;
	
	private String jsonAddress;
	
	@Before
	public void setup() throws JsonProcessingException {
		a = new Address();
		a.setAddressId(1L);
		a.setAddressLine1("1234 Simple Ln");
		a.setCity("Cary");
		a.setState("NC");
		a.setZipCode("27514");
		
		jsonAddress = (new ObjectMapper()).writeValueAsString(a);
	}
	
	/**
	 * Test method for {@link ai.patient.api.AddressController#list()}.
	 * @throws Exception 
	 */
	@Test
	public void testList() throws Exception {
		when(repo.findAll()).thenReturn(Arrays.asList(a));
		mvc.perform(get("/api/address/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].addressLine1", is(a.getAddressLine1())));
	}

	/**
	 * Test method for {@link ai.patient.api.AddressController#getOne(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetOne() throws Exception {
		when(repo.findById(1L)).thenReturn(Optional.of(a));
		mvc.perform(get("/api/address/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.addressLine1", is(a.getAddressLine1())));
	}

	/**
	 * Test method for {@link ai.patient.api.AddressController#create(ai.patient.model.Address)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreate() throws Exception {
		when(repo.save(Mockito.any(Address.class))).thenAnswer(arg -> arg.getArgument(0));
		mvc.perform(post("/api/address/")
				.content(jsonAddress)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.addressLine1", is(a.getAddressLine1())));
	}

	/**
	 * Test method for {@link ai.patient.api.AddressController#delete(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public void testDelete() throws Exception {
		when(repo.save(Mockito.any(Address.class))).thenAnswer(arg -> arg.getArgument(0));
		mvc.perform(delete("/api/address/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(repo, times(1)).deleteById(1L);
	}

}
