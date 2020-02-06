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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@Column(name = "movie_trailer_path")
	private String movieTrailerPath;
	
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
	private boolean movieIsDeleted;
	
	@OneToMany
	@JoinTable(	name = "MovieTrailer", 
		joinColumns = @JoinColumn(name = "movie_id"), 
		inverseJoinColumns = @JoinColumn(name = "trailer_id"))
	private List<Trailer> trailers = new ArrayList<Trailer>();
	
	@OneToMany
	@JoinTable(	name = "MovieImage", 
		joinColumns = @JoinColumn(name = "movie_id"), 
		inverseJoinColumns = @JoinColumn(name = "image_id"))
	private List<Image> images = new ArrayList<Image>();
	
	@ManyToMany
	@JoinTable(	name = "MovieAuthor", 
		joinColumns = @JoinColumn(name = "movie_id"), 
		inverseJoinColumns = @JoinColumn(name = "author_id"))
	private List<Author> authors = new ArrayList<Author>();;
	
	@ManyToMany
	@JoinTable(	name = "MovieActor", 
		joinColumns = @JoinColumn(name = "movie_id"), 
		inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private List<Actor> actors = new ArrayList<Actor>();
	
	@ManyToMany
	@JoinTable(	name = "MovieCategory", 
		joinColumns = @JoinColumn(name = "movie_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories = new ArrayList<Category>();
	
	@OneToMany(mappedBy = "movie")
	@JsonBackReference
    private List<MovieUserComment> movieUserComments = new ArrayList<MovieUserComment>();
	
	public Movie() {
		// TODO Auto-generated constructor stub
	}

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
	
	public String getMovieTrailerPath() {
		return movieTrailerPath;
	}

	public void setMovieTrailerPath(String movieTrailerPath) {
		this.movieTrailerPath = movieTrailerPath;
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

	public boolean isMovieIsDeleted() {
		return movieIsDeleted;
	}

	public void setMovieIsDeleted(boolean userIsDeleted) {
		this.movieIsDeleted = userIsDeleted;
	}
	
	public List<Trailer> getTrailers() {
		return trailers;
	}

	public void setTrailers(List<Trailer> trailers) {
		this.trailers.clear();
		if (trailers!=null)
			this.trailers.addAll(trailers);
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors.clear();
		if (authors!=null)
			this.authors.addAll(authors);
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors.clear();
		if (actors!=null)
			this.actors.addAll(actors);
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories.clear();
		if (categories!=null)
			this.categories.addAll(categories);
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images.clear();
		if (images!=null)
			this.images.addAll(images);
	}

	public List<MovieUserComment> getMovieUserComments() {
		return movieUserComments;
	}

	public void setMovieUserComments(List<MovieUserComment> movieUserComments) {
		this.movieUserComments.clear();
		if (movieUserComments!=null)
			this.movieUserComments.addAll(movieUserComments);
	}
	
	public void addMovieUserComment(MovieUserComment mvc) {
		mvc.setMovie(this);
		this.movieUserComments.add(mvc);
	}
	
	public void removeMovieUserComment(MovieUserComment mvc) {
		mvc.setMovie(null);
		this.movieUserComments.remove(mvc);
	}
}
