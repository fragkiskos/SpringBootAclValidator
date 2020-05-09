package aclValidation.validation.BodyWrapping;

import aclValidation.validation.BodyWrapping.request.CachedBodyHttpServletRequest;
import aclValidation.validation.BodyWrapping.response.CachedBodyHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ContentCachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(httpServletResponse);
        filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);
    }
}
