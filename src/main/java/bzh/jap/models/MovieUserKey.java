package bzh.jap.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Embeddable
public class MovieUserKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "movie_id", nullable = false)
    private long movieId;

    @Column(name = "user_id", nullable = false)
    private long userId;
    
	public MovieUserKey() {
		// TODO Auto-generated constructor stub
	}
    
    public MovieUserKey(long movieId, long userId) {
		// TODO Auto-generated constructor stub
    	this.movieId = movieId;
    	this.userId = userId;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieUserKey that = (MovieUserKey) o;

        if (!(this.movieId==that.movieId)) return false;
        return this.userId==that.userId;
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(getMovieId(), getUserId());
    }
}
