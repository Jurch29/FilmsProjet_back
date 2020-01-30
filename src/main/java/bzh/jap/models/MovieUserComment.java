package bzh.jap.models;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "MovieUserComment")
public class MovieUserComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_user_comment_id", nullable = false)
	private long movieUserCommentId;
	
	@Basic
	@Column(name = "movie_user_comment_date")
	private Timestamp movieUserCommentDate;
	
	@Column(name = "movie_user_comment_is_deleted", nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean movieUserCommentIsDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @MapsId
    @JsonManagedReference
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    @MapsId
    @JsonManagedReference
    private Movie movie;
	
	public MovieUserComment() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieUserComment(Timestamp movieUserCommentDate) {
		this.movieUserCommentDate = movieUserCommentDate;
	}

	public long getMovieUserCommentId() {
		return movieUserCommentId;
	}

	public void setMovieUserCommentId(long movieUserCommentId) {
		this.movieUserCommentId = movieUserCommentId;
	}

	public Timestamp getMovieUserCommentDate() {
		return movieUserCommentDate;
	}

	public void setMovieUserCommentDate(Timestamp movieUserCommentDate) {
		this.movieUserCommentDate = movieUserCommentDate;
	}

	public boolean isMovieUserCommentIsDeleted() {
		return movieUserCommentIsDeleted;
	}

	public void setMovieUserCommentIsDeleted(boolean movieUserCommentIsDeleted) {
		this.movieUserCommentIsDeleted = movieUserCommentIsDeleted;
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
