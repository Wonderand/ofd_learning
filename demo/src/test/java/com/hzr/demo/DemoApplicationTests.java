package com.hzr.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;
import org.ofdrw.converter.ConvertHelper;
import org.ofdrw.converter.FontLoader;
import org.ofdrw.converter.GeneralConvertException;
import org.ofdrw.graphics2d.OFDGraphicsDocument;
import org.ofdrw.graphics2d.OFDPageGraphics2D;
import org.ofdrw.layout.OFDDoc;
import org.ofdrw.layout.element.Paragraph;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void createOFD() throws IOException {
        Path path = Paths.get("E:\\upload_pdfjs\\ofd_file\\test.ofd");
        try (OFDDoc ofdDoc = new OFDDoc(path)) {
            Paragraph p = new Paragraph("你好呀，OFD Reader&Writer！");
            ofdDoc.add(p);
        }
        System.out.println("生成文档位置: " + path.toAbsolutePath());
    }

    @Test
    void pdfbox2ofdrw() throws Exception {

        Path path = Paths.get("E:\\upload_pdfjs\\demo\\renren-fast开发文档3.0最新版.pdf");
        Path dir = Paths.get("E:\\upload_pdfjs\\ofd_file\\", path.getFileName().toString());
        System.out.println(path.getFileName().toString());
        Path dst = Paths.get("E:\\upload_pdfjs\\ofd_file\\renren-fast开发文档3.0最新版.pdf\\1helloworld.ofd");
        Files.createDirectories(dir);
        try (OFDGraphicsDocument ofdDoc = new OFDGraphicsDocument(dst);
             PDDocument pdfDoc = PDDocument.load(path.toFile())) {
            PDFRenderer pdfRender = new PDFRenderer(pdfDoc);
            for (int pageIndex = 0; pageIndex < pdfDoc.getNumberOfPages(); pageIndex++) {
                PDRectangle pdfPageSize = pdfDoc.getPage(pageIndex).getBBox();
                OFDPageGraphics2D ofdPageG2d = ofdDoc.newPage(pdfPageSize.getWidth(), pdfPageSize.getHeight());
                pdfRender.renderPageToGraphics(pageIndex, ofdPageG2d);
            }
        }
    }
    public void convertPdf() {
//        Path src = Paths.get("src/test/resources/1.ofd");
//        Path dst = Paths.get("target/1.pdf");
//        Path src = Paths.get("src/test/resources/zsbk.ofd");
//        Path dst = Paths.get("target/zsbk.pdf");

        FontLoader.DEBUG = true;
        // 为不规范的字体名创建映射
        FontLoader.getInstance()
                .addAliasMapping("小标宋体", "方正小标宋简体")
                .addSimilarFontReplaceRegexMapping(".*SimSun.*", "SimSun");
        long start = System.currentTimeMillis();
        try {
//            ConvertHelper.toPdf(src, dst);
            ConvertHelper.toPdf(Paths.get("src/test/resources/发票示例.ofd"), Paths.get("target/发票示例.pdf"));
            ConvertHelper.toPdf(Paths.get("src/test/resources/zsbk.ofd"), Paths.get("target/zsbk.pdf"));
            ConvertHelper.toPdf(Paths.get("src/test/resources/999.ofd"), Paths.get("target/999.pdf"));
            System.out.printf(">> 总计花费: %dms\n", System.currentTimeMillis() - start);
        } catch (GeneralConvertException e) {
            e.printStackTrace();
        }
    }

}
