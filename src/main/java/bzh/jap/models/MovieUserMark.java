package bzh.jap.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "MovieUserMark")
public class MovieUserMark {

	@EmbeddedId
	private MarkKey markKey;
	
	@Column(name = "movie_user_mark_mark")
    private double movieUserMarkMark;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
	@MapsId("user_id")
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
	@MapsId("movie_id")
    private Movie movie;
	
	public MovieUserMark() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieUserMark(MarkKey mk, double note) {
		// TODO Auto-generated constructor stub
		this.markKey = mk;
		this.movieUserMarkMark = note;
	}

	public MarkKey getMarkKey() {
		return markKey;
	}

	public void setMarkKey(MarkKey markKey) {
		this.markKey = markKey;
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
