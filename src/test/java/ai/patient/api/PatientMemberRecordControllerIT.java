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
import ai.patient.data.PatientMemberRecordRepository;
import ai.patient.model.Address;
import ai.patient.model.PatientMemberRecord;

/**
 * @author evgeniy.sharapov
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PatientMemberRecordController.class)
public class PatientMemberRecordControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	PatientMemberRecordRepository repo;
	
	private PatientMemberRecord pmr;
	
	private String jsonPmr;
	
	@Before
	public void setup() throws JsonProcessingException {
		pmr = new PatientMemberRecord();
		pmr.setId(1L);
		pmr.setFirstName("Alice");
		pmr.setLastName("Bobcat");
		pmr.setMedicalRecordNumber("q234123");
		pmr.setSource("source");
		pmr.setSocialSecurityNumber("123-456-7890");
		jsonPmr = (new ObjectMapper()).writeValueAsString(pmr);
	}

	@Test
	public void testList() throws Exception {
		when(repo.findAll()).thenReturn(Arrays.asList(pmr));
		mvc.perform(get("/api/records/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].firstName", is(pmr.getFirstName())))
		.andExpect(jsonPath("$[0].lastName", is(pmr.getLastName())));
	}

	@Test
	public void testGetOne() throws Exception {
		when(repo.findById(1L)).thenReturn(Optional.of(pmr));
		mvc.perform(get("/api/records/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", is(pmr.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(pmr.getLastName())));
	}

	@Test
	public void testCreate() throws Exception {
		when(repo.save(Mockito.any(PatientMemberRecord.class))).thenAnswer(arg -> arg.getArgument(0));
		mvc.perform(post("/api/records/")
				.content(jsonPmr)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", is(pmr.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(pmr.getLastName())));
	}

	@Test
	public void testDelete() throws Exception {
		when(repo.save(Mockito.any(PatientMemberRecord.class))).thenAnswer(arg -> arg.getArgument(0));
		mvc.perform(delete("/api/records/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(repo, times(1)).deleteById(1L);
	}

}
