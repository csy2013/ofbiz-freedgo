package org.ofbiz.webapp.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2014/10/14.
 */
public class CharacterEncodingFilter implements Filter {

    private String encoding;

    private boolean forceEncoding = false;


    /**
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     */
    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("forceEncoding");
        if (value == null)
            this.forceEncoding = true;
        else if (value.equalsIgnoreCase("true"))
            this.forceEncoding = true;
        else this.forceEncoding = value.equalsIgnoreCase("yes");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (this.encoding != null && (this.forceEncoding || servletRequest.getCharacterEncoding() == null)) {
            servletRequest.setCharacterEncoding(this.encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        this.encoding = null;
     }


}