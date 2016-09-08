package gira.cdap.com.giira.Task;

/**
 * Created by Kaashiff Ahamed on 03-Sep-16.
 */
public class reviews {

    private String name;
    private String comment;
    private float rating;
    private String date;
    private String userimage;

    public String getUserimage() {
        return serverURL.local_host_url+userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
