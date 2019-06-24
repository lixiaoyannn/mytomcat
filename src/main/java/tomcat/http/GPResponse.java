package tomcat.http;

import java.io.OutputStream;

/**
 * 创建者:小䶮
 */
public class GPResponse {
    private  OutputStream outputStream;
    public GPResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public void write(String s) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n").append("Content-Type: text/html;\n")
                .append("\r\n")
           .append(s);
        outputStream.write(sb.toString().getBytes());
    }
}
