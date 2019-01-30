package ai.patient.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ai.patient.model.Patient;
import ai.patient.model.PatientMemberRecord;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	PatientRepository repo;
	
	Patient p;
	
	@Before
	public void setup() {
		List<PatientMemberRecord> pmrList = new ArrayList<>();
		for(int i : Arrays.asList(1,2,3)){
			PatientMemberRecord pmr = new PatientMemberRecord();
			pmr.setSource(String.format("source%d", i));
			pmr.setMedicalRecordNumber(String.format("medicalRecordNumber%d", i));
			entityManager.persist(pmr);
			pmrList.add(pmr);
		}
		p = new Patient();
		p.setEnterpriseId("enterpriseId");
		p.setMemberRecords(pmrList);
		entityManager.persist(p);
		entityManager.flush();
	}
	
	
	@Test
	public void testFindAll() {
		Collection<Patient> result = repo.findAll();
		assertThat(result).hasSize(1);
		Patient p = result.iterator().next();
		assertThat(p.getMemberRecords()).hasSize(3);
	}	
	
	@Test
	public void testFindByMemberRecordSource() {
		Collection<Patient> result = repo.findByMemberRecords_Source("source1");
		assertThat(result).hasSize(1);
		Patient patient = result.iterator().next();
		assertThat(patient.getMemberRecords()).hasSameSizeAs(p.getMemberRecords());
	}

	@Test
	public void testFindByMemberRecordMedicalRecordNumber() {
		Collection<Patient> result = repo.findByMemberRecords_MedicalRecordNumber("medicalRecordNumber1");
		assertThat(result).hasSize(1);
		Patient patient = result.iterator().next();
		assertThat(patient.getMemberRecords()).hasSameSizeAs(p.getMemberRecords());
	}
}
