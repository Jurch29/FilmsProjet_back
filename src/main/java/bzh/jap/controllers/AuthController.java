package bzh.jap.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import bzh.jap.models.ERole;
import bzh.jap.models.Role;
import bzh.jap.models.User;
import bzh.jap.models.UserActivation;
import bzh.jap.payload.JwtResponse;
import bzh.jap.payload.LoginRequest;
import bzh.jap.payload.MessageResponse;
import bzh.jap.payload.SignupRequest;
import bzh.jap.payload.UserActivationResponse;
import bzh.jap.repository.RoleRepository;
import bzh.jap.repository.UserActivationRepository;
import bzh.jap.repository.UserRepository;
import bzh.jap.security.jwt.JwtUtils;
import bzh.jap.security.services.UserDetailsImpl;
import bzh.jap.util.Email;
import bzh.jap.util.GeneralService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserActivationRepository userActivationRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ObjectMapper mapper;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		java.util.Optional<UserActivation> userActivation = userActivationRepository.findById(userDetails.getId());
		
		//code d'activation present pour ce user on envoie une rï¿½ponse UserActivationResponse
		if (userActivation.isPresent()) {
			return ResponseEntity.ok(new UserActivationResponse(userDetails.getId(), true));
		}
		
		User user = userRepository.findById(userDetails.getId()).get();
		
		//Pas de code d'activation : pas la premiere connexion on renvoie une rï¿½ponse jwtresponse
		return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),user.getUserFirstname(),user.getUserLastname(),userDetails.getEmail(),roles));
	}
	
	//ObjectNode nous permet de renvoyer un objet json customisï¿½
	@GetMapping("/activation")
	public ObjectNode activateUser(@RequestParam("user_id") String id, @RequestParam("user_activation_code") String code) {
		
		Boolean result = false;
		java.util.Optional<UserActivation> userActivation = userActivationRepository.findById(Long.parseLong(id));
		//Le code est bon on supprime la ligne useractivation qui correspond ï¿½ ce user + renvoie true
		if (userActivation.get().getUserActivationCode().equals(code)) {
			userActivationRepository.delete(userActivation.get());
			result = true;
		}
		
		ObjectNode objectNode = mapper.createObjectNode();
	    objectNode.put("isActivated", result);
	    return objectNode;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		if (userRepository.existsByUserLogin(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Userlogin is already taken!"));
		}

		if (userRepository.existsByUserEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		//Creer un nouvel utilisateur
		User user = new User(signUpRequest.getLastname() , signUpRequest.getFirstname(), signUpRequest.getEmail(), signUpRequest.getUsername(),
							 encoder.encode(signUpRequest.getPassword()));
		
		user.setUserIsDeleted(false);  

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleTitle(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleTitle(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByRoleTitle(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByRoleTitle(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		
		GeneralService service = new GeneralService();
		UserActivation ua = new UserActivation(service.java8AlphaNumericGenerator(8));
		ua.setUser(user);
		user.setUserActivation(ua);
		userRepository.save(user);
		
		Email mail = new Email();
		mail.sendEmail(javaMailSender, "Bienvenue "+user.getUserLogin()+" sur Ez-Movies ! veuillez vous connecter et taper le code suivant : "
				+ ua.getUserActivationCode()+" afin d'activer votre compte.", "Code d'activation Ez-Movies", user.getUserEmail());

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/credentialsRecovery")
	public ResponseEntity<?> sendCredentialsRecovery(@Valid @RequestBody Map<String, Object> payload) {
		
		if (payload.size() != 1 && !payload.containsKey("user_email")) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Erreur: requete mal forme."));
		}
		
		if (!userRepository.existsByUserEmail((String) payload.get("user_email"))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Aucun compte n'existe avec cette adresse mail."));
		}
		
		User user = userRepository.findByUserEmail((String) payload.get("user_email")).get();
		
		String token = UUID.randomUUID().toString();

		//Envoie mail
		Email mail = new Email();
		mail.sendEmail(javaMailSender, "Bonjour "+user.getUserFirstname()+", \nsuite à votre demande veuillez retrouver ci-dessous les informations de connexion concernant votre compte.\n\n"
				+ "Login : "+user.getUserLogin()+"\nMot de passe : "+user.getUserPassword(), "Récupération des identifiants de connexion", (String) payload.get("user_email"));
		
		return ResponseEntity.ok(new MessageResponse("Un mail de récupération à été envoyé."));
	}
}
