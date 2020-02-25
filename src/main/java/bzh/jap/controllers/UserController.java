package bzh.jap.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.CartHistory;
import bzh.jap.models.Items;
import bzh.jap.models.Movie;
import bzh.jap.models.MovieUserCart;
import bzh.jap.models.MovieUserKey;
import bzh.jap.models.Purchases;
import bzh.jap.models.Role;
import bzh.jap.models.User;
import bzh.jap.payload.JwtResponse;
import bzh.jap.payload.MergeCartRequest;
import bzh.jap.payload.MessageResponse;
import bzh.jap.payload.UserDetailsResponse;
import bzh.jap.repository.CartHistoryRepository;
import bzh.jap.repository.MovieRepository;
import bzh.jap.repository.MovieUserCartRepository;
import bzh.jap.repository.UserRepository;
import bzh.jap.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private MovieUserCartRepository movieUserCartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CartHistoryRepository cartHistoryRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/cart/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<MovieUserCart> getUserCartByUserId(@PathVariable long id) {
		return movieUserCartRepository.findByEmbeddedKeyMovieUserUserId(id);
	}

	@PostMapping("/cartmerge")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> mergeCart(@Valid @RequestBody MergeCartRequest mergeCartRequest) { //Peut etre mieux fait ?
		User user = userRepository.findById(mergeCartRequest.getUserId()).get();
		List<MovieUserCart> cartsList = new ArrayList<MovieUserCart>();
		
		for (int i = 0 ; i < mergeCartRequest.getLocalCart().size() ; i++) {
			mergeCartRequest.getLocalCart().get(i).setUser(user);
			Movie m = movieRepository.findById(mergeCartRequest.getLocalCart().get(i).getEmbeddedKeyMovieUser().getMovieId()).get();
			
			MovieUserCart mv = new MovieUserCart(new MovieUserKey(m.getMovieId(),user.getUserId()),mergeCartRequest.getLocalCart().get(i).getMovieUserCartCount());
			mv.setMovie(m);
			mv.setUser(user);
			
			cartsList.add(mv);
		}
		
		movieUserCartRepository.saveAll(cartsList);
		return ResponseEntity.ok(new MessageResponse("OK"));
	}
	
	@PostMapping("/additemtocart")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addItemToCart(@RequestBody Map<String, Object> lookupRequestObject) {
		long movieId = ((Number) lookupRequestObject.get("movieId")).longValue();
		long userId = ((Number) lookupRequestObject.get("userId")).longValue();
		
		Optional<Movie> mv = movieRepository.findById(movieId);
		Optional<User> u = userRepository.findById(userId);
		
		Optional<MovieUserCart> userCart = movieUserCartRepository.findById(new MovieUserKey(movieId, userId));
		
		if (userCart.isEmpty()) {
			MovieUserCart m = new MovieUserCart(new MovieUserKey(movieId,userId),1);
			m.setMovie(mv.get());
			m.setUser(u.get());
			
			movieUserCartRepository.save(m);
		}
		else {
			userCart.get().setMovieUserCartCount(userCart.get().getMovieUserCartCount()+1);
			movieUserCartRepository.save(userCart.get());
		}
		return ResponseEntity.ok(new MessageResponse("OK"));
	}
	
	@PostMapping("/buycart")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@Transactional
	public ResponseEntity<?> buyCart(@RequestBody Map<String, Object> lookupRequestObject) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = Calendar.getInstance().getTime();        
		String todayAsString = df.format(today);
		System.out.println("Today is: " + todayAsString);
		
		String userId = String.valueOf(((Number) lookupRequestObject.get("userId")).longValue());
		CartHistory cartHistoryMongo = cartHistoryRepository.findByuserId(userId);
		List<MovieUserCart> userCart = movieUserCartRepository.findByEmbeddedKeyMovieUserUserId(Long.parseLong(userId));
		
		//Il y a un cart history il faut update ses orders
		if (cartHistoryMongo!=null) {
			Purchases purchase = new Purchases();
			purchase.setPurchase_date(todayAsString);
			purchase.setItems(new ArrayList<Items>());
			for (int i = 0 ; i < userCart.size() ; i++) {
				Items item = new Items();
				item.setMovie_id(String.valueOf(userCart.get(i).getMovie().getMovieId()));
				item.setCount(String.valueOf(userCart.get(i).getMovieUserCartCount()));
				item.setMovie_price(String.valueOf(userCart.get(i).getMovie().getMoviePrice()*userCart.get(i).getMovieUserCartCount()));
				purchase.getItems().add(item);
			}
			
			cartHistoryMongo.getOrders().add(purchase);
			cartHistoryRepository.save(cartHistoryMongo);
		}
		//Sinon on le creer
		else {
			cartHistoryMongo = new CartHistory();
			cartHistoryMongo.setUserId(userId);
			cartHistoryMongo.setOrders(new ArrayList<Purchases>());
			Purchases purchase = new Purchases();
			purchase.setPurchase_date(todayAsString);
			purchase.setItems(new ArrayList<Items>());
			for (int i = 0 ; i < userCart.size() ; i++) {
				Items item = new Items();
				item.setMovie_id(String.valueOf(userCart.get(i).getMovie().getMovieId()));
				item.setCount(String.valueOf(userCart.get(i).getMovieUserCartCount()));
				item.setMovie_price(String.valueOf(userCart.get(i).getMovie().getMoviePrice()*userCart.get(i).getMovieUserCartCount()));
				purchase.getItems().add(item);
			}
			
			cartHistoryMongo.getOrders().add(purchase);
			cartHistoryRepository.save(cartHistoryMongo);
		}
		movieUserCartRepository.deleteByEmbeddedKeyMovieUserUserId(Long.parseLong(userId));
		return ResponseEntity.ok(new MessageResponse("OK"));
	}
	
	@GetMapping("/checkuserpassword")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> checkPassword(@RequestParam("userId") String id, @RequestParam("password") String pswd) {
		Optional<User> user = userRepository.findById(Long.parseLong(id));
		
		if (user.isEmpty()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Utilisateur inconnu"));
		}
		
		if (!this.userPasswordCheck(pswd, user.get())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Mauvais mot de passe"));
		}
		
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/changepassword")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changePassword(@RequestBody Map<String, Object> lookupRequestObject) {
		long userId = ((Number) lookupRequestObject.get("userId")).longValue();
		Optional<User> user = userRepository.findById(userId);
		user.get().setUserPassword(encoder.encode((String) lookupRequestObject.get("password")));
		userRepository.save(user.get());
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/changeuserdetails")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changeUserDetails(@RequestBody Map<String, Object> lookupRequestObject, @RequestHeader (name="Authorization") String token) {
		long userId = ((Number) lookupRequestObject.get("userId")).longValue();
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isEmpty()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Utilisateur inconnu"));
		}
		
		user.get().setUserLastname((String) lookupRequestObject.get("userLastname"));
		user.get().setUserFirstname((String) lookupRequestObject.get("userFirstname"));
		user.get().setUserLogin((String) lookupRequestObject.get("userLogin"));
		user.get().setUserEmail((String) lookupRequestObject.get("userEmail"));
		
		userRepository.save(user.get());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new UserDetailsResponse(token, userId, (String) lookupRequestObject.get("userLogin"),
				(String) lookupRequestObject.get("userFirstname"), (String) lookupRequestObject.get("userLastname"),
				(String) lookupRequestObject.get("userEmail"), roles));
	}
	
	public boolean userPasswordCheck(String password, User user) {
	    PasswordEncoder passencoder = new BCryptPasswordEncoder();
	    String encodedPassword = user.getUserPassword();
	    return passencoder.matches(password, encodedPassword);
	}
	
}