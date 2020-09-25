package Context;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public static Map<String, String> qsToMap(String qs) {
        Map<String, String> map = new HashMap<String, String>();

        String[] pairs = qs.split("&");

        for (String pair : pairs) {
            String[] arr = pair.split("=");
            map.put(arr[0], arr[1]);
        }

        return map;
    }
}
