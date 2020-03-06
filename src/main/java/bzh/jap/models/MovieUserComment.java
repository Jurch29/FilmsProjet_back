package bzh.jap.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "MovieUserComment")
public class MovieUserComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_user_comment_id", nullable = false)
	private long movieUserCommentId;
	
	@ManyToOne()
    @JoinColumn(name="user_id", nullable = false)
	@JsonBackReference(value="user-comments")
    private User user;
	
	@ManyToOne()
    @JoinColumn(name="movie_id", nullable = false)
	@JsonBackReference(value="movie-comments")
    private Movie movie;
	
	@Basic
	@Column(name = "movie_user_comment_date")
	private Timestamp movieUserCommentDate;
	
	@Column(name = "movie_user_comment_is_deleted", nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean movieUserCommentIsDeleted;
	
	@OneToMany
	@JoinTable(	name = "MovieUserCommentImage",
		joinColumns = @JoinColumn(name = "comment_id"),
		inverseJoinColumns = @JoinColumn(name = "image_id"))
	private List<Image> images = new ArrayList<Image>();
	
	public MovieUserComment() {
		// TODO Auto-generated constructor stub
	}
	
	public MovieUserComment(User user, Movie movie, Timestamp time, boolean isDeleted) {
		this.user = user;
		this.movie = movie;
		this.movieUserCommentDate = time;
		this.movieUserCommentIsDeleted = isDeleted;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
}
