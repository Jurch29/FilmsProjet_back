package bzh.jap.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "MovieComments")
public class MovieComments {

	@Id
    private String id;
 
    @Field(value = "comment_id")
    private String commentId;
 
    @Field(value = "comment_content")
    private String commentContent;
    
    public MovieComments() {
		// TODO Auto-generated constructor stub
	}
    
    public MovieComments(String id, String content) {
		this.commentId = id;
    	this.commentContent = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
}
