package com.ubuntuvim.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 编码过滤器
 */
public class CharactorFilter implements Filter {

	/**
	 * The default character encoding to set for requests that pass through this
	 * filter.
	 */
	protected String encoding = null;

	/**
	 * The filter configuration object we are associated with. If this value is
	 * null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig = null;

	/**
	 * Should a character encoding specified by the client be ignored?
	 */
	protected boolean ignore = true;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		if ("GET".equals(req.getMethod())) {
			// 将httpRequest进行包装
			EncodingHttpServletRequest wrapper = new EncodingHttpServletRequest(req, encoding);
			chain.doFilter(wrapper, response);
		} else {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/html;charset=" + encoding);
			chain.doFilter(request, response);
		}

	}

	/**
	 * Place this filter into service.
	 * 
	 * @param filterConfig
	 *            The filter configuration object
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;

	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

}

