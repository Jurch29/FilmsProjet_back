package bzh.jap.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "MovieDescription")
public class MovieDescription {
	
	@Id
    private String id;
 
    @Field(value = "movie_id")
    private String movieId;
 
    @Field(value = "movie_description")
    private String movieDescription;
    
    public MovieDescription() {
		// TODO Auto-generated constructor stub
	}
    
    public MovieDescription(String id, String content) {
		this.movieId = id;
    	this.movieDescription = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}
    
}
