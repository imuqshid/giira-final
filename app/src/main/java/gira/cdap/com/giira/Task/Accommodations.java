package gira.cdap.com.giira.Task;

/**
 * Created by Perceptor on 7/30/2016.
 */
public class Accommodations {
    private String Accname;
    private String Accdescription;
    private String Accaddress;
    private String Accwebsite;
    private String AccphoneNo;
    private String AccEmail;
    private String Accimage;
    private String Accid;

    public Accommodations() {

    }
    public String getAccid() {
        return Accid;
    }

    public void setAccid(String accid) {
        Accid = accid;
    }

    public String getAccname() {
        return Accname;
    }

    public void setAccname(String accname) {
        Accname = accname;
    }

    public String getAccdescription() {
        return Accdescription;
    }

    public void setAccdescription(String accdescription) {
        Accdescription = accdescription;
    }

    public String getAccaddress() {
        return Accaddress;
    }

    public void setAccaddress(String accaddress) {
        Accaddress = accaddress;
    }

    public String getAccwebsite() {
        return Accwebsite;
    }

    public void setAccwebsite(String accwebsite) {
        Accwebsite = accwebsite;
    }

    public String getAccphoneNo() {
        return AccphoneNo;
    }

    public void setAccphoneNo(String accphoneNo) {
        AccphoneNo = accphoneNo;
    }

    public String getAccEmail() {
        return AccEmail;
    }

    public void setAccEmail(String accEmail) {
        AccEmail = accEmail;
    }

    public String getAccimage() {
        return serverURL.local_host_url+Accimage;
    }

    public void setAccimage(String accimage) {
        Accimage = accimage;
    }
}