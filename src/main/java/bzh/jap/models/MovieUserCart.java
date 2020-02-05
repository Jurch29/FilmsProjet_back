package bzh.jap.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "MovieUserCart")
public class MovieUserCart {
	
	@EmbeddedId
	private MovieUserKey embeddedKey;
	
	@Column(name = "movie_user_cart_count")
    private int movieUserCartCount;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
	@MapsId("user_id")
    private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="movie_id")
	@MapsId("movie_id")
    private Movie movie;
	
	public MovieUserCart() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieUserCart(MovieUserKey muk, int quantity) {
		// TODO Auto-generated constructor stub
		this.embeddedKey = muk;
		this.movieUserCartCount = quantity;
	}
	
	public MovieUserKey getEmbeddedKey() {
		return embeddedKey;
	}

	public void setEmbeddedKey(MovieUserKey embeddedKey) {
		this.embeddedKey = embeddedKey;
	}
	
	public int getMovieUserCartCount() {
		return movieUserCartCount;
	}

	public void setMovieUserCartCount(int movieUserCartCount) {
		this.movieUserCartCount = movieUserCartCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
}
