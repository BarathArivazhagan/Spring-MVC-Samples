package com.barath.mvc.app;

import java.io.Serializable;

public interface CsrfToken extends Serializable {
    String getHeaderName();

    String getParameterName();

    String getToken();
}
