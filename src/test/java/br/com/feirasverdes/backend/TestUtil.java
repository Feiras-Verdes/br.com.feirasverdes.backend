package br.com.feirasverdes.backend;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
    		.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    public static byte[] createByteArray(int size, String data) {
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = Byte.parseByte(data, 2);
        }
        return byteArray;
    }
    
    public static Object convertJsonToObject(byte[] json, Class clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }
    
    public static HttpHeaders autHeaders() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGxvY2FsaG9zdCIsIm5hbWUiOiJUZXN0IiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE3MTYyMzkwMjJ9.SfpBQMUxPhUb0aBvS1rVHNY63hH9-SLuyXs5vr558JRMroca8XcDY1Q2XjerXy_68YIQXjLjc9sQtxz8nw2UQw");
    	return headers;
    }

    private TestUtil() {}
}
