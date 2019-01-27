package ai.patient.api;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.patient.data.AddressRepository;
import ai.patient.model.Address;

/**
 * Address REST API
 * @author evgeniy.sharapov
 *
 */
@RestController
@RequestMapping(path="/api/address")
public class AddressController {
	
	@Autowired
	private AddressRepository repo; 
	
	@GetMapping(path = "/")
	public Collection<Address> list (){
		return repo.findAll();
	}

	// TODO: use 404 page via controller advice 
	@GetMapping(path = "/{id}")
	public Optional<Address> getOne (@PathVariable("id") Long id) {
		return repo.findById(id);
	}
	
	@PostMapping(path = "/")
	public Address create( @RequestBody Address a) {
		return repo.save(a);
	}
	
	// TODO: Should we return deleted entity ? 
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		repo.deleteById(id);
	}
}
