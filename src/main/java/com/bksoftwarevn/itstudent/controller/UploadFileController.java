package com.bksoftwarevn.itstudent.controller;

import com.bksoftwarevn.itstudent.model.JsonResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UploadFileController", value = "/upload-file/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 50
        , maxRequestSize = 1024 * 1024 * 50)
// fileSizeThreshold : Neu kich thuoc file upload qua muc cho phep thi ghi truc tiep file vao o cung
// chu ko thong qua bo dem
public class UploadFileController extends HttpServlet {
    private JsonResult jsonResult = new JsonResult();
    private static final String SAVE_DIRECTORY = "file_upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("upload.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String rs = "";
        List<String> listRs = new ArrayList<>();

        try {
            Collection<Part> partCollection = req.getParts();
            String saveFile = getFolderUpload() + "";

            for (Part part : partCollection) {
                String fileName = getFileName(part);

                if (fileName != null && !fileName.equals("")) {
                    String filePath = saveFile + File.separator + fileName;
                    System.out.println("Write File: " + filePath);
                    part.write(filePath);
                    listRs.add(SAVE_DIRECTORY + "/" + saveFile.substring(saveFile.lastIndexOf(File.separator) + 1) + "/" + fileName);
                }
            }

            rs = listRs.size() != 0 ? jsonResult.jsonSuccess(listRs) : jsonResult.jsonSuccess("Upload Fail");
            if (new File(saveFile).list().length <= 0) {
                new File(saveFile).delete();
                rs = jsonResult.jsonSuccess("No File To Upload");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFailure("Upload Error");
        }

        resp.getWriter().write(rs);
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println(contentDisp);
        String[] items = contentDisp.split(";");

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                fileName = fileName.replace("\\", "/");
                return fileName.substring(fileName.lastIndexOf("/") + 1);
            }
        }

        return null;
    }

    private File getFolderUpload() {
        String appPath = "D:\\Web-Server\\Tomee\\apache-tomee-plume-8.0.3\\webapps\\" + SAVE_DIRECTORY + "\\" + new Date().getTime();
        File folderUpload = new File(appPath);

        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }

        return folderUpload;
    }
}
