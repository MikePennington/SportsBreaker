package com.breaker.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

public class ResourceServlet extends HttpServlet {

    private static final long                    serialVersionUID = 1L;
    private static final String                  PIC_URL          = Constants.PICTURE_USERPIC_URL;
    private static final HashMap<String, String> CONTENT_TYPES    = createContentTypeMap();

    private static HashMap<String, String> createContentTypeMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jpg", "image/jpeg");
        map.put("jpeg", "image/jpeg");
        map.put("gif", "image/gif");
        map.put("bmp", "image");
        map.put("png", "image/png");
        map.put("pdf", "application/pdf");
        map.put("xls", "excel/ms-excel");
        map.put("xlsx", "excel/ms-excel");

        return map;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getResource(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getResource(req, resp);
    }

    private void getResource(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String uri = request.getRequestURI();
        int index = uri.indexOf(PIC_URL);
        if (index < 0)
            return;

        String filePath = uri.substring(index + PIC_URL.length());

        File file = new File(Constants.PICTURE_USERPIC_PATH + filePath);
        if (!file.exists())
            return;

        ServletOutputStream out = response.getOutputStream();
        response.setContentLength((int) file.length());

        setContentType(response, file);

        byte[] buf = new byte[1024];
        int length = 0;
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        while ((in != null) && ((length = in.read(buf)) != -1)) {
            out.write(buf, 0, length);
        }
        in.close();
        out.close();
    }

    /**
     * Sets the content type based on the file extension.
     * 
     * @param response
     * @param file
     */
    private void setContentType(HttpServletResponse response, File file) {
        String ext = FilenameUtils.getExtension(file.toString());
        String contentType = CONTENT_TYPES.get(ext);
        if (!StringUtils.isEmpty(contentType)) {
            response.setContentType(contentType);
        }
    }

}
