package com.webapp.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webapp.model.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {
    @Value("${shared.dir}")
    private String root;

    @RequestMapping(value = "**", method = RequestMethod.GET)
    public void handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
        Model model = new Model(root, uri);
        model.handle(response);
    }
}