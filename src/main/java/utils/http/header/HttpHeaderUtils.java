package utils.http.header;

import org.springframework.http.HttpHeaders;

/**
 * @Datetime: 2024/11/6下午5:41
 * @author: Camellia.xioahua
 */
public class HttpHeaderUtils {


    private final static ThreadLocal<HttpHeaders> headersThreadLocal = new ThreadLocal<HttpHeaders>() {
        @Override
        protected HttpHeaders initialValue() {
            HttpHeaders headers = new HttpHeaders();
            // 设置暴露的响应头
            headers.add("Access-Control-Expose-Headers", "Authorization, X-Total-Count");
            return headers;
        }
    };


    /**
     * 获取当前线程的 HttpHeaderUtils
     *
     * @return
     */
    public static HttpHeaders exposHeaders() {
        return headersThreadLocal.get();
    }

}
