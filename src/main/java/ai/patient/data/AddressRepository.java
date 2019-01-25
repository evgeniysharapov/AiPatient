package ai.patient.data;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.patient.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
