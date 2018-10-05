package com.barath.mvc.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


public class CsrfFilter  extends OncePerRequestFilter

    {
        public static final DefaultRequiresCsrfMatcher DEFAULT_CSRF_MATCHER = new DefaultRequiresCsrfMatcher();
        private final Logger logger = LoggerFactory.getLogger(this.getClass());
        private final CsrfTokenRepository tokenRepository;
        private DefaultRequiresCsrfMatcher requireCsrfProtectionMatcher;


    public CsrfFilter(CsrfTokenRepository csrfTokenRepository) {
        this.requireCsrfProtectionMatcher = DEFAULT_CSRF_MATCHER;
        Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
        this.tokenRepository = csrfTokenRepository;
    }

        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
        {

            if(logger.isInfoEnabled()) {
                logger.info("CSRF filter invoked");
            }
            request.setAttribute(HttpServletResponse.class.getName(), response);
            CsrfToken csrfToken = this.tokenRepository.loadToken(request);
            boolean missingToken = csrfToken == null;
            if (missingToken) {
                csrfToken = this.tokenRepository.generateToken(request);
                this.tokenRepository.saveToken(csrfToken, request, response);
            }

            request.setAttribute(CsrfToken.class.getName(), csrfToken);
            request.setAttribute(csrfToken.getParameterName(), csrfToken);
            if (!this.requireCsrfProtectionMatcher.matches(request)) {
                filterChain.doFilter(request, response);
            } else {
                String actualToken = request.getHeader(csrfToken.getHeaderName());
                if(logger.isInfoEnabled()) { logger.info("csrf: validating actual token {} with req token {}", actualToken, csrfToken.getToken()); }
                if (actualToken == null) {
                    actualToken = request.getParameter(csrfToken.getParameterName());
                }

                if (!csrfToken.getToken().equals(actualToken)) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Invalid CSRF token found for {}" , request.getRequestURL());
                    }

                    if (missingToken) {
                       throw  new ServletException("CSRF token missing. Abort the execution");
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        }

        public void setRequireCsrfProtectionMatcher(DefaultRequiresCsrfMatcher requireCsrfProtectionMatcher) {
            Assert.notNull(requireCsrfProtectionMatcher, "requireCsrfProtectionMatcher cannot be null");
            this.requireCsrfProtectionMatcher = requireCsrfProtectionMatcher;
          }



        private static final class DefaultRequiresCsrfMatcher {
            private final HashSet<String> allowedMethods;

            private DefaultRequiresCsrfMatcher() {
                this.allowedMethods = new HashSet(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));
            }

            public boolean matches(HttpServletRequest request) {
                return !this.allowedMethods.contains(request.getMethod());
            }
        }
}
