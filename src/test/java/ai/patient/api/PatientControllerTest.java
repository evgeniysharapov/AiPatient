/**
 * 
 */
package ai.patient.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import ai.patient.data.PatientRepository;
import ai.patient.model.Address;
import ai.patient.model.Patient;
import ai.patient.model.PatientMemberRecord;
import ai.patient.service.PatientService;

/**
 * @author evgeniy.sharapov
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	PatientRepository repo;
	
	@MockBean
	PatientService service;

	private Patient p;
	
	private String jsonPatient;
	
	@Before
	public void setup() throws JsonProcessingException {
		p = new Patient();
		p.setEnterpriseId("12345");
		p.setMemberRecords(new ArrayList<PatientMemberRecord>());
		jsonPatient = (new ObjectMapper()).writeValueAsString(p);
	}

	@Test
	public void testList() throws Exception {
		when(repo.findAll()).thenReturn(Arrays.asList(p));
		mvc.perform(get("/api/patients/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].enterpriseId", is(p.getEnterpriseId())));
	}

	@Test
	public void testGetOne() throws Exception {
		when(repo.findById("12345")).thenReturn(Optional.of(p));
		mvc.perform(get("/api/patients/12345")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.enterpriseId", is(p.getEnterpriseId())));
	}

	@Test
	public void testCreate() throws Exception {
		when(repo.save(Mockito.any(Patient.class))).thenAnswer(arg -> arg.getArgument(0));
		mvc.perform(post("/api/patients/")
				.content(jsonPatient)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.enterpriseId", is(p.getEnterpriseId())));
	}

	@Test
	public void testDelete() throws Exception {
		when(repo.save(Mockito.any(Patient.class))).thenAnswer(arg -> arg.getArgument(0));
		mvc.perform(delete("/api/patients/12345")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(repo, times(1)).deleteById("12345");
	}
}
