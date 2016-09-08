package gira.cdap.com.giira.Task;

/**
 * Created by Perceptor on 8/9/2016.
 */
public class category {

    private String categoryname;
    private String categoryimage;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    private String categoryID;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategoryimage() {
        return serverURL.local_host_url+categoryimage;
    }

    public void setCategoryimage(String categoryimage) {
        this.categoryimage = categoryimage;
    }
}
