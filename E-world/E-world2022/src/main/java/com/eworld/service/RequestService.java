package com.eworld.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {
    String getClientIPAdress(HttpServletRequest request);
}
