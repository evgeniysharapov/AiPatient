package ai.patient.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address {
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  addressId;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;
}
