package tomcat;

import tomcat.http.GPRequest;
import tomcat.http.GPResponse;
import tomcat.http.GPServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 创建者:小䶮
 */
public class GPtomcat {
    //servlet
    //request
    //response
    //1.配置好启动端口，默认8080
    //2.配置好web.xml
    //servlet-name servlet-class servlet-pattern
    private int port = 8080;
    private ServerSocket serverSocket;
    private Map<String,GPServlet> servletMapping = new HashMap<String,GPServlet>();
    private Properties webxml = new Properties();
    private void init(){
        //加载web.xml，同时初始化ServletMapping对象
        try {
            //3.读取配置
            String webInf = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(webInf + "web.properties");
            webxml.load(fis);
            for (Object k : webxml.keySet()){
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$","");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    //单实例，多线程
                    GPServlet obj = (GPServlet)Class.forName(className).newInstance();
                    //map servletmapping
                    servletMapping.put(url,obj);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //服务的启动
    public void start(){
        init();
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("GP tomcat 已启动，监听端口为：" + port);
            while(true){
                //4.HTTP请求
                Socket socket = serverSocket.accept();
                process(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //处理数据
    public void process(Socket socket) throws Exception{
        InputStream in = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        //7.request（InputStream）和response（OutputSream）
        GPRequest request = new GPRequest(in);
        GPResponse response = new GPResponse(outputStream);
        //5.从协议中拿到URL，把响应的servlet用反射进行实例化
        String url = request.getUrl();
        if (servletMapping.containsKey(url)){
            //6.调用实例化对象的service方法，执行具体的doGet/doPost
            servletMapping.get(url).service(request,response);
        }else{
            response.write("404 - NOT FOUND");
        }
        outputStream.flush();
        outputStream.close();
        in.close();
        socket.close();
    }

    public static void main(String[] args) {
        new GPtomcat().start();
    }
}
