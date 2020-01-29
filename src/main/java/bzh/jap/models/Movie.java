package bzh.jap.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "Movie" )
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id", nullable = false)
	private long movieId;
	
	@NotBlank
	@Size(max = 255)
	@Column(name = "movie_title")
	private String movieTitle;
	
	@Column(name = "movie_price")
	private double moviePrice;
	
	@Size(max = 255)
	@Column(name = "movie_image_path")
	private String movieImagePath;
	
	@Size(max = 255)
	@Column(name = "movie_file_path")
	private String movieFilePath;
	
	@Basic
	@Column(name = "movie_date")
	private Timestamp movieDate;
	
	@Column(name = "movie_duration")
	private int movieDuration;
	
	@Column(name = "movie_mark")
	private double movieMark;
	
	@Column(name = "movie_is_deleted", nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean userIsDeleted;
	
	@OneToMany
	@JoinTable(	name = "MovieTrailer", 
		joinColumns = @JoinColumn(name = "movie_id"), 
		inverseJoinColumns = @JoinColumn(name = "trailer_id"))
	private List<Trailer> trailers;
	
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	@JsonBackReference
//	private List<MovieUserMark> movieUserMark = new ArrayList<MovieUserMark>();

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public double getMoviePrice() {
		return moviePrice;
	}

	public void setMoviePrice(double moviePrice) {
		this.moviePrice = moviePrice;
	}

	public String getMovieImagePath() {
		return movieImagePath;
	}

	public void setMovieImagePath(String movieImagePath) {
		this.movieImagePath = movieImagePath;
	}

	public String getMovieFilePath() {
		return movieFilePath;
	}

	public void setMovieFilePath(String movieFilePath) {
		this.movieFilePath = movieFilePath;
	}

	public Timestamp getMovieDate() {
		return movieDate;
	}

	public void setMovieDate(Timestamp movieDate) {
		this.movieDate = movieDate;
	}

	public int getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(int movieDuration) {
		this.movieDuration = movieDuration;
	}

	public double getMovieMark() {
		return movieMark;
	}

	public void setMovieMark(double movieMark) {
		this.movieMark = movieMark;
	}

	public boolean isUserIsDeleted() {
		return userIsDeleted;
	}

	public void setUserIsDeleted(boolean userIsDeleted) {
		this.userIsDeleted = userIsDeleted;
	}

	public List<Trailer> getTrailers() {
		return trailers;
	}

	public void setTrailers(List<Trailer> trailers) {
		this.trailers = trailers;
	}
	
//	public List<MovieUserMark> getMovieUserMark() {
//		return movieUserMark;
//	}
//
//	public void setMovieUserMark(List<MovieUserMark> movieUserMark) {
//		this.movieUserMark = movieUserMark;
//	}
	
}
