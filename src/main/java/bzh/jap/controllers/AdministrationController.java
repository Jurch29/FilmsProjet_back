package bzh.jap.controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.ERole;
import bzh.jap.models.Role;
import bzh.jap.models.User;
import bzh.jap.payload.MessageResponse;
import bzh.jap.repository.MovieRepository;
import bzh.jap.repository.RoleRepository;
import bzh.jap.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/administration")
public class AdministrationController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@DeleteMapping("/deleteuser/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteUser(@PathVariable long id) {
		userRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("User deleted"));
	}
	
	@PostMapping("/updateuser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> lookupRequestObject) {
		long userId = ((Number) lookupRequestObject.get("userid")).longValue();
		Optional<User> user = userRepository.findById(userId);
		user.get().setUserLastname((String) lookupRequestObject.get("username"));
		user.get().setUserFirstname((String) lookupRequestObject.get("firstname"));
		user.get().setUserLogin((String) lookupRequestObject.get("login"));
		user.get().setUserEmail((String) lookupRequestObject.get("email"));
		userRepository.save(user.get());
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/updatepassword")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, Object> lookupRequestObject) {
		long userId = ((Number) lookupRequestObject.get("userid")).longValue();
		Optional<User> user = userRepository.findById(userId);
		user.get().setUserPassword(encoder.encode((String) lookupRequestObject.get("password")));
		userRepository.save(user.get());
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/adduser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewUser(@RequestBody Map<String, Object> lookupRequestObject) {
		User user = new User((String) lookupRequestObject.get("username") , (String) lookupRequestObject.get("firstname"),
				(String) lookupRequestObject.get("email"),(String) lookupRequestObject.get("login"),
				encoder.encode((String) lookupRequestObject.get("password")));

		user.setUserIsDeleted(false);
		Set<Role> roles = new HashSet<>();
		
		user.setUserPassword(encoder.encode((String) lookupRequestObject.get("password")));
		Role userRole = roleRepository.findByRoleTitle(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@DeleteMapping("/deletemovie/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteMovie(@PathVariable long id) {
		movieRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Movie deleted"));
	}
	
	@PostMapping("/addmovie")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewMovie(@RequestBody Map<String, Object> lookupRequestObject) {
		
		for (Map.Entry<String, Object> entry : lookupRequestObject.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key);
		    System.out.println(value);
		}
		
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
}
