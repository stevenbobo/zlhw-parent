package com.zb.jnlxc.viewResolver;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import com.google.gson.Gson;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of {@link org.springframework.http.converter.HttpMessageConverter HttpMessageConverter}
 * that can read and write JSON using <a href="http://jackson.codehaus.org/">Jackson's</a> {@link ObjectMapper}.
 *
 * <p>This converter can be used to bind to typed beans, or untyped {@link java.util.HashMap HashMap} instances.
 *
 * <p>By default, this converter supports {@code application/json}. This can be overridden by setting the
 * {@link #setSupportedMediaTypes(List) supportedMediaTypes} property.
 *
 */
public class MappingGsonConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

//    private ObjectMapper objectMapper = new ObjectMapper();
    private Gson gson = new Gson();

    private boolean prefixJson = false;


    /**
     * Construct a new {@code BindingJacksonHttpMessageConverter}.
     */
    public MappingGsonConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    /**
     * Indicate whether the JSON output by this view should be prefixed with "{} &&". Default is false.
     * <p>Prefixing the JSON string in this manner is used to help prevent JSON Hijacking.
     * The prefix renders the string syntactically invalid as a script so that it cannot be hijacked.
     * This prefix does not affect the evaluation of JSON, but if JSON validation is performed on the
     * string, the prefix would need to be ignored.
     */
    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
    }


    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        JavaType javaType = getJavaType(clazz);
        return ( canRead(mediaType));
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return ( canWrite(mediaType));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // should not be called, since we override canRead/Write instead
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        JavaType javaType = getJavaType(clazz);
        try {
            Reader reader = new InputStreamReader(inputMessage.getBody());
            return gson.fromJson(reader, clazz);
        }
        catch (JsonProcessingException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        try {
//            outputMessage.getHeaders().setContentType(MediaType.parseMediaType("text/html;charset=UTF-8"));
            outputMessage.getBody().write(gson.toJson(object).getBytes("utf-8"));
        }
        catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }


    /**
     * Return the Jackson {@link JavaType} for the specified class.
     * <p>The default implementation returns {@link TypeFactory#type(java.lang.reflect.Type)},
     * but this can be overridden in subclasses, to allow for custom generic collection handling.
     * For instance:
     * <pre class="code">
     * protected JavaType getJavaType(Class&lt;?&gt; clazz) {
     *   if (List.class.isAssignableFrom(clazz)) {
     *     return TypeFactory.collectionType(ArrayList.class, MyBean.class);
     *   } else {
     *     return super.getJavaType(clazz);
     *   }
     * }
     * </pre>
     * @param clazz the class to return the java type for
     * @return the java type
     */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.type(clazz);
    }

    /**
     * Determine the JSON encoding to use for the given content type.
     * @param contentType the media type as requested by the caller
     * @return the JSON encoding to use (never <code>null</code>)
     */
    protected JsonEncoding getJsonEncoding(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();
            for (JsonEncoding encoding : JsonEncoding.values()) {
                if (charset.name().equals(encoding.getJavaName())) {
                    return encoding;
                }
            }
        }
        return JsonEncoding.UTF8;
    }

}
