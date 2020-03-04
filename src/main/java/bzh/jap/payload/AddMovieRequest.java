package bzh.jap.payload;

import bzh.jap.models.Movie;

public class AddMovieRequest {
	
	private Movie movie;
	
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
