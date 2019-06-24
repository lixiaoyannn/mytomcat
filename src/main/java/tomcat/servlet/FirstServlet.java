package tomcat.servlet;


import tomcat.http.GPRequest;
import tomcat.http.GPResponse;
import tomcat.http.GPServlet;

/**
 * 创建者:小䶮
 */
public class FirstServlet extends GPServlet {

    protected void doGet(GPRequest req, GPResponse resp) throws Exception {
       this.doPost(req, resp);
    }

    protected void doPost(GPRequest req, GPResponse resp) throws Exception {
        resp.write("this is the first servlet! ");
    }
}
