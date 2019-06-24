package tomcat.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * 创建者:小䶮
 */
public class GPRequest {
    private String method;
    private String url;
    public GPRequest(InputStream in){
        try {
            String content = "";
            byte[] buffer = new byte[1024];
            int len = 0;
            if((len = in.read(buffer))>0){
                content = new String(buffer,0,len);
            }
            String line = content.split("\n")[0];
            String[] arr = line.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getUrl(){
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }
}
