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
@Table(name = "MovieUserMark")
public class MovieUserMark {

	@EmbeddedId
	private MovieUserKey embeddedKeyMovieUser;
	
	@Column(name = "movie_user_mark_mark")
    private double movieUserMarkMark;
	
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
	
	public MovieUserMark() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieUserMark(MovieUserKey muk, double note) {
		// TODO Auto-generated constructor stub
		this.embeddedKeyMovieUser = muk;
		this.movieUserMarkMark = note;
	}
	
	public MovieUserKey getEmbeddedKey() {
		return embeddedKeyMovieUser;
	}

	public void setEmbeddedKey(MovieUserKey embeddedKey) {
		this.embeddedKeyMovieUser = embeddedKey;
	}

	public double getMovieUserMarkMark() {
		return movieUserMarkMark;
	}

	public void setMovieUserMarkMark(double movieUserMarkMark) {
		this.movieUserMarkMark = movieUserMarkMark;
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
