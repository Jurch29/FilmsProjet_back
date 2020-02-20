package bzh.jap.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MovieUserCart")
public class MovieUserCart {
	
	@EmbeddedId
	private MovieUserKey embeddedKeyMovieUser;
	
	@Column(name = "movie_user_cart_count")
    private int movieUserCartCount;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
	@MapsId("user_id")
	@JsonIgnore
    private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="movie_id")
	@MapsId("movie_id")
	@JsonIgnore
    private Movie movie;
	
	public MovieUserCart() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieUserCart(MovieUserKey muk, int quantity) {
		// TODO Auto-generated constructor stub
		this.embeddedKeyMovieUser = muk;
		this.movieUserCartCount = quantity;
	}
	
	public MovieUserKey getEmbeddedKeyMovieUser() {
		return embeddedKeyMovieUser;
	}

	public void setEmbeddedKeyMovieUser(MovieUserKey embeddedKey) {
		this.embeddedKeyMovieUser = embeddedKey;
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
