package ai.patient.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ai.patient.model.PatientMemberRecord;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientMemberRecordRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	PatientMemberRecordRepository repo;
	
	@Before
	public void setup() {
		for(int i : Arrays.asList(1,2,3)){
			PatientMemberRecord pmr = new PatientMemberRecord();
			pmr.setSource(String.format("source%d", i));
			pmr.setMedicalRecordNumber(String.format("medicalRecordNumber%d", i));
			entityManager.persist(pmr);
		}
		entityManager.flush();
	}
	
	@Test
	public void testFindBySource() {
		Collection<?> result = repo.findBySource("source2");
		assertThat(result).hasSize(1);
	}

	@Test
	public void testFindBySourceNull() {
		Collection<?> result = repo.findBySource(null);
		assertThat(result).hasSize(0);
	}

	@Test
	public void testFindByMedicalRecordNumber() {
		Collection<?> result = repo.findByMedicalRecordNumber("medicalRecordNumber2");
		assertThat(result).hasSize(1);
	}

	@Test
	public void testFindByMedicalRecordNumberNull() {
		Collection<?> result = repo.findByMedicalRecordNumber(null);
		assertThat(result).hasSize(0);
	}
	
	@Test
	public void testFindBySourceOrMedicalRecordNumberMRN() {
		Collection<?> result = repo.findBySourceOrMedicalRecordNumber(null, "medicalRecordNumber2");
		assertThat(result).hasSize(1);
	}
	
	@Test
	public void testFindBySourceOrMedicalRecordNumberSource() {
		Collection<?> result = repo.findBySourceOrMedicalRecordNumber("source2", null);
		assertThat(result).hasSize(1);
	}
	
	@Test
	public void testFindBySourceOrMedicalRecordNumberOverlap() {
		Collection<?> result = repo.findBySourceOrMedicalRecordNumber("source2", "medicalRecordNumber2");
		assertThat(result).hasSize(1);
	}
	
	@Test
	public void testFindBySourceOrMedicalRecordNumberUnion() {
		Collection<?> result = repo.findBySourceOrMedicalRecordNumber("source1", "medicalRecordNumber2");
		assertThat(result).hasSize(2);
	}
	
	@Test
	public void testFindBySourceOrMedicalRecordNumberAllNulls() {
		Collection<?> result = repo.findBySourceOrMedicalRecordNumber(null, null);
		assertThat(result).hasSize(0);
	}	

}
