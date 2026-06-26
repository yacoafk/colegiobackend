package com.colegio.backend.service;

import com.colegio.backend.dto.LoginRequest;
import com.colegio.backend.dto.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest request);
}