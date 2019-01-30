/**
 * 
 */
package ai.patient.service;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Iterables;

import ai.patient.DemoApplication;
import ai.patient.data.PatientRepository;
import ai.patient.model.Patient;
import ai.patient.model.PatientMemberRecord;

/**
 * @author evgeniy.sharapov
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class PatientServiceTest {
	
	@MockBean
	private PatientRepository patientRepo;

	@Autowired
	private PatientService service;
	
	Patient[] ps;
	
	@Before
	public void setup() throws JsonProcessingException {
		int N = 3;
		ps = new Patient[N];
		for( int i = 1; i <= N; i++) {
			Patient p = new Patient();
			p.setEnterpriseId(format("enterpriseId%d", i));
			p.setMemberRecords(new ArrayList<>());
			for ( int j : Arrays.asList(1, 2,3)) {
				PatientMemberRecord pmr = new PatientMemberRecord();
				pmr.setSource(format("source%d", j));
				pmr.setMedicalRecordNumber(format("medicalRecordNumber%d", j));
				p.getMemberRecords().add(pmr);
			}
			ps[i-1] = p;
		}
		
	}
	
	@Test
	public void testSearchSimple() throws Exception {
		when(patientRepo.findByMemberRecords_Source(startsWith("source1")))
			.thenReturn(Arrays.asList(ps[0]));
		when(patientRepo.findByMemberRecords_MedicalRecordNumber(startsWith("medicalRecordNumber2")))
			.thenReturn(Arrays.asList(ps[1]));
	
		Collection<Patient> r = service.searchBySourceOrMedicalRecordNumber("source1", "medicalRecordNumber2");
		
		assertThat(r).hasSize(2);
		
		Patient first = Iterables.get(r,0);
		assertThat(first.getMemberRecords()).hasSize(1); // source1

		Patient second = Iterables.get(r,1);
		assertThat(second.getMemberRecords()).hasSize(1); // mrn2
	}
	
	public static <T> T copy(T object) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(bais);
			return (T) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

	@Test
	public void testSearch() throws Exception {
		
		when(patientRepo.findByMemberRecords_Source(startsWith("source1")))
			.thenReturn(Arrays.asList(copy(ps[0]), copy(ps[1])));
		
		when(patientRepo.findByMemberRecords_MedicalRecordNumber(startsWith("medicalRecordNumber2")))
			.thenReturn(Arrays.asList(copy(ps[1]), copy(ps[2])));
		
		Collection<Patient> r = service.searchBySourceOrMedicalRecordNumber("source1", "medicalRecordNumber2");
		
		assertThat(r).hasSize(3);

		// patients might not be in order
		
		for (Patient p : r) {
			switch (p.getEnterpriseId()) {
			case "enterpriseId1":
				assertThat(p.getMemberRecords()).hasSize(1);
				assertThat(p.getMemberRecords().get(0).getSource()).isEqualTo("source1");
				break;
			case "enterpriseId2":
				assertThat(p.getMemberRecords()).hasSize(2);
				assertThat(p.getMemberRecords()).extracting(PatientMemberRecord::getSource)
							.contains("source1", "source2");
				break;
			case "enterpriseId3":
				assertThat(p.getMemberRecords()).hasSize(1);
				assertThat(p.getMemberRecords().get(0).getMedicalRecordNumber()).isEqualTo("medicalRecordNumber2");
				break;
			default:
				fail("Unknown Patient");
				break;
			}
			
		}
	}
	
}
