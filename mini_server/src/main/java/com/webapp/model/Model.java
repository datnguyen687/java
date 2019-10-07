package com.webapp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class Model {
    private String root;

    private static String htmlDefStart = "<!DOCTYPE html><html>";
    private static String htmlDefEnd = "</html>";
    private static String header = "<head><title>Mini Server</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"></head>";

    private String subPath = "";

    public Model(String root, String path) {
        this.root = root;
        this.subPath = path;
    }

    public void handle(HttpServletResponse response) throws IOException {
        String fullPath = root + subPath;
        File file = new File(fullPath);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("NOT FOUND " + subPath);
            response.getWriter().flush();
            response.getWriter().close();
        } else if (file.canRead()) {
            if (file.isDirectory()) {
                handleDir(file, response);
            } else if (file.isFile()) {
                handleFile(file, response);
            }
        }
    }

    private void handleDir(File dir, HttpServletResponse response) throws IOException {
        File[] files = dir.listFiles();
        String html = htmlDefStart + header;

        html += "<body><table>";
        String dirsHtml = "";
        String filesHtml = "";
        String root = subPath.substring(1);
        if (!root.isEmpty()) {
            root = "/" + root;
        }
        for (File file : files) {
            String tableRow = "<tr>";
            if (file.isDirectory()) {
                String encoded = root + "/" + URLEncoder.encode(file.getName(), "UTF-8");
                String link = "<a href=" + encoded + ">" + file.getName() + "</a>";
                String tableData = "<td><i class=\"material-icons\">folder</i></td><td>" + link + "</td>";
                tableRow += tableData;
                tableRow += "</tr>";
                dirsHtml += tableRow;
            } else if (file.isFile()) {
                String encoded = root + "/" + URLEncoder.encode(file.getName(), "UTF-8");
                String link = "<a href=" + encoded + ">" + file.getName() + "</a>";
                String tableData = "<td><i class=\"material-icons\">file_download</i></td><td>" + link + "</td>";
                tableRow += tableData;
                tableRow += "</tr>";
                filesHtml += tableRow;
            }
        }
        html += dirsHtml + filesHtml + "</table></body>" + htmlDefEnd;

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html");
        response.setContentLength(html.length());
        response.getWriter().write(html);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void handleFile(File file, HttpServletResponse response) throws IOException {
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        response.setContentType(contentType);
        response.setContentLengthLong(file.length());
        response.setHeader("Content-disposition", "attachment; file=" + file.getName());
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }
}