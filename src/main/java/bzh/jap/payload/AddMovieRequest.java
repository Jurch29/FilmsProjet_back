package bzh.jap.payload;

import bzh.jap.models.Movie;

public class AddMovieRequest {
	
	private Movie movie;
	private String synopsis;
	
	public Movie getMovie() {
		return movie;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public String getSynopsis() {
		return synopsis;
	}
	
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
}
