package bzh.jap.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import bzh.jap.models.CartHistory;

public interface CartHistoryRepository extends MongoRepository<CartHistory, String> {

	public CartHistory findByuserId(String userId);
	
}
