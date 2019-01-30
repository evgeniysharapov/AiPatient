/**
 * 
 */
package ai.patient.api;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;

/**
 * E is for entiry type
 * R is for repository type
 * I is for Id type (Long or String in our case)
 * @author evgeniy.sharapov
 *
 */
@Slf4j
public abstract class BaseController<E, I, R extends JpaRepository<E, I> >{
	
	@Autowired
	protected R repo;
	
	@GetMapping(path = "/")
	public Collection<E> list (){
		return repo.findAll();
	}

	// TODO: use 404 page via controller advice 
	@GetMapping(path = "/{id}")
	public Optional<E> getOne (@PathVariable("id") I id) {
		return repo.findById(id);
	}
	
	@PostMapping(path = "/")
	public E create( @RequestBody E e) {
		return repo.save(e);
	}
	
	// TODO: Should we return deleted entity ? 
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable("id") I id) {
		repo.deleteById(id);
	}

	// Partial Put is never restful
	// https://twitter.com/fielding/status/275471320685367296?lang=en
	@PutMapping(path = "/{id}")
	public Optional<E> updade(@RequestBody E mod, @PathVariable I id) {
		Optional<E> orig = repo.findById(id);
		if( orig.isPresent()) {
			setId(mod, id);
			return Optional.of(repo.save(mod));
		} else {
			return orig;
		}
	}
	/**
	 * Set Id for entity generically 
	 * @param e
	 * @param id
	 */
	private void setId(E e, I id) {
		for (Field f :  e.getClass().getDeclaredFields()) {
			if(f.getAnnotation(Id.class) != null) {
				ReflectionUtils.makeAccessible(f);
				ReflectionUtils.setField(f, e, id);
			}
		}
	}
}
