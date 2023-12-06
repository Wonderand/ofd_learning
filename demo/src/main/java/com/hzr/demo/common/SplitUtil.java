package com.hzr.demo.common;


import com.itextpdf.kernel.pdf.PdfReader;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class SplitUtil {

    private final static Logger logger = LoggerFactory.getLogger(SplitUtil.class);

    public void getStream(String readPath, HttpServletResponse response, String fileName) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            inputStream = new FileInputStream(new File(readPath));

            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF8"),
                    "ISO8859-1"));//解决：中文乱码
            //response.setHeader("Content-Disposition", "attachment;fileName="+fileName);

            outputStream = response.getOutputStream();

            byte[] bytes = IOUtils.toByteArray(inputStream);
            //response.getWriter().p//更安全
            IOUtils.write(bytes, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
