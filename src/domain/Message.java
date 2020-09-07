package domain;
import java.io.Serializable;
import java.util.Date;
public class Message implements Serializable {
 private Integer id;
 private String user;
 private String body;
 private Date posted;
	 
	 public Integer getId() {
		 return id;
		 }
		 public void setId(Integer id) {
		 this.id = id;
		 }
		 public String getUser() {
		 return user;
		 }
		 public void setUser(String user) {
		 this.user = user;
		 }
		 public String getBody() {
		 return body;
		 }
		 public void setBody(String body) {
		 this.body = body;
		 }
		 public Date getPosted() {
		 return posted;
		 }
		 public void setPosted(Date posted) {
		 this.posted = posted;
		 }

}
