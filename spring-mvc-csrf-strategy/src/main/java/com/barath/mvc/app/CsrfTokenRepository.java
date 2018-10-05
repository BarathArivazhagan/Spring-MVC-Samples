package com.barath.mvc.app;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CsrfTokenRepository {

    CsrfToken generateToken(HttpServletRequest var1);

    void saveToken(CsrfToken var1, HttpServletRequest var2, HttpServletResponse var3);

    CsrfToken loadToken(HttpServletRequest var1);
}
