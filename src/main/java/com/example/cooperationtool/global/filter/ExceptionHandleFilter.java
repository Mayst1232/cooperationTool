package com.example.cooperationtool.global.filter;

import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.exception.ErrorCode;
import com.example.cooperationtool.global.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ServiceException e) {
            setResponse(response);
            ErrorCode code = e.getCode();
            RootResponseDto<Object> errResponse = RootResponseDto.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
            response.setStatus(code.getStatus().value());

            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(errResponse));
            } catch (IOException ignored) {
            }
        }
    }

    private void setResponse(HttpServletResponse response) {
        response.setStatus(response.getStatus()); // http status 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // JSON 설정
        response.setCharacterEncoding(StandardCharsets.UTF_8.name()); // UTF8 설정
    }
}
