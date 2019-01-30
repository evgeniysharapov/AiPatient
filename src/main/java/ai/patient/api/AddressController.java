package ai.patient.api;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class AddressController extends BaseController<Address, Long, AddressRepository>{
}
