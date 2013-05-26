package com.zb.jnlxc.viewResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.google.gson.Gson;
import org.codehaus.jackson.JsonEncoding;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-30
 * Time: 下午6:05
 * To change this template use File | Settings | File Templates.
 */
public class MappingGsonView extends AbstractView implements HandlerExceptionResolver{

    /**
     * Default content type. Overridable as bean property.
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/json";


    private Gson gson = new Gson();

    private JsonEncoding encoding = JsonEncoding.UTF8;

    private boolean prefixJson = false;

    private Set<String> modelKeys;


    private boolean disableCaching = true;


    /**
     * Construct a new {@code JacksonJsonView}, setting the content type to {@code application/json}.
     */
    public MappingGsonView() {
        setContentType(DEFAULT_CONTENT_TYPE);
        setExposePathVariables(false);
    }




    /**
     * Set the {@code JsonEncoding} for this converter.
     * By default, {@linkplain JsonEncoding#UTF8 UTF-8} is used.
     */
    public void setEncoding(JsonEncoding encoding) {
        Assert.notNull(encoding, "'encoding' must not be null");
        this.encoding = encoding;
    }

    /**
     * Indicates whether the JSON output by this view should be prefixed with <tt>"{} && "</tt>.
     * Default is false.
     * <p>Prefixing the JSON string in this manner is used to help prevent JSON Hijacking.
     * The prefix renders the string syntactically invalid as a script so that it cannot be hijacked.
     * This prefix does not affect the evaluation of JSON, but if JSON validation is performed
     * on the string, the prefix would need to be ignored.
     */
    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
    }

    /**
     * Set the attribute in the model that should be rendered by this view.
     * When set, all other model attributes will be ignored.
     */
    public void setModelKey(String modelKey) {
        this.modelKeys = Collections.singleton(modelKey);
    }

    /**
     * Set the attributes in the model that should be rendered by this view.
     * When set, all other model attributes will be ignored.
     */
    public void setModelKeys(Set<String> modelKeys) {
        this.modelKeys = modelKeys;
    }

    /**
     * Return the attributes in the model that should be rendered by this view.
     */
    public Set<String> getModelKeys() {
        return this.modelKeys;
    }

    /**
     * Set the attributes in the model that should be rendered by this view.
     * When set, all other model attributes will be ignored.
     * @deprecated use {@link #setModelKeys(Set)} instead
     */
    @Deprecated
    public void setRenderedAttributes(Set<String> renderedAttributes) {
        this.modelKeys = renderedAttributes;
    }

    /**
     * Return the attributes in the model that should be rendered by this view.
     * @deprecated use {@link #getModelKeys()} instead
     */
    @Deprecated
    public Set<String> getRenderedAttributes() {
        return this.modelKeys;
    }

    /**
     * Disables caching of the generated JSON.
     * <p>Default is {@code true}, which will prevent the client from caching the generated JSON.
     */
    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }


    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(getContentType());
        response.setCharacterEncoding(this.encoding.getJavaName());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            response.addDateHeader("Expires", 1L);
        }
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        Object value = filterModel(model);
        response.getOutputStream().write(gson.toJson(value).getBytes("utf-8"));
    }

    /**
     * Filters out undesired attributes from the given model.
     * The return value can be either another {@link Map} or a single value object.
     * <p>The default implementation removes {@link org.springframework.validation.BindingResult} instances and entries
     * not included in the {@link #setRenderedAttributes renderedAttributes} property.
     * @param model the model, as passed on to {@link #renderMergedOutputModel}
     * @return the object to be rendered
     */
    protected Object filterModel(Map<String, Object> model) {
        Map<String, Object> result = new HashMap<String, Object>(model.size());
        Set<String> renderedAttributes = (!CollectionUtils.isEmpty(this.modelKeys) ? this.modelKeys : model.keySet());
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return  result.size() == 1 ? result.values().iterator().next() : result;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setStatus(400);
        ModelAndView mv =new ModelAndView();
//        mv.addObject("success",false);
        if (ex instanceof BaseErrorModel){
            logger.warn(ex.getMessage());
        }else {
            logger.error("未知异常",ex);
        }
        mv.addObject(ex.getMessage());
        mv.setView(this);
        return mv;
    }
}
