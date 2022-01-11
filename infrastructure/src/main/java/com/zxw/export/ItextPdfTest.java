package com.zxw.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.filechooser.FileSystemView;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * 第1章：PDF和iText简介
 * 1.1第一个iText示例：Hello World
 */
@Slf4j
public class ItextPdfTest {
    PdfReader reader;
    FileOutputStream out;
    ByteArrayOutputStream bos;
    PdfStamper stamper;

    /**
     * 生成的PDF文件的路径。
     */
    public static final String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    public static final String newPDFPath = desktopPath + "/hello.pdf";
    public static final String templatePath = "D:\\软件存储\\WeChat Files\\wxid_6443924439612\\FileStorage\\File\\2021-09\\9f166abdf145487e85de230c5d066c5c.pdf";
//    public static final String templatePath = "C:\\Users\\zxw\\Desktop\\44320643419770944.pdf";

    /**
     * 创建一个PDF文件：hello.pdf
     *
     * @param args no arguments needed
     */
    public static void main(String[] args) throws DocumentException, IOException {
        ItextPdfTest itextPdfTest = new ItextPdfTest();
//        helloWorld.createPdf(newPDFPath);
        itextPdfTest.fillPDF();
    }

    public void fillPDF() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "测试");
            map.put("ID_card", "身份证");
            map.put("ID_number", "434534153513");
            map.put("phone", "15622390724");
            map.put("year", "2022");
            map.put("month", "01");
            map.put("date", "10");
            map.put("name_1", "请问\r\t\n请问");
            map.put("name_2", "320323199106060631");
            map.put("fill_1", "一");
            map.put("fill_2", "测试业务");
            //输出流
            out = new FileOutputStream(newPDFPath);
            //读取pdf模板//
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            java.util.Iterator<String> formField = form.getFields().keySet().iterator();
            // [{"fieldId":"authName",},
            //{"fieldId":"legalName",}, {"fieldId":"time",]
//            BaseFont bf = BaseFont.createFont("SimSun", BaseFont.IDENTITY_H, true);
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            while (formField.hasNext()) {
                String next = formField.next();
                log.info("获取到pdf变量key：{},填充变量：{}", next, map.get(next));
                form.setFieldProperty(next, "textfont", bf, null);
                // 填充pdf数据
                form.setField(next, map.get(next));
            }
            stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();
            Document doc = new Document();
//            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(newPDFPath));
//            writer.close();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            int numberOfPages = reader.getNumberOfPages();
            for (int i = 1; i <= numberOfPages; i++) {
                PdfImportedPage importPage = copy.getImportedPage(
                        new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }
            doc.close();
            copy.close();
        } catch (IOException e) {
            System.out.println(1);
        } catch (DocumentException e) {
            System.out.println(2);
        }
    }

    /**
     * 创建PDF文档.
     *
     * @param filename 新PDF文档的路径
     * @throws DocumentException
     * @throws IOException
     */
    public void createPdf(String filename) throws DocumentException, IOException {
        // 第一步 创建文档实例
        Document document = new Document();
        // 第二步 获取PdfWriter实例
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // 第三步 打开文档
        document.open();
        // 第四步 添加段落内容
        document.add(new Paragraph("Hello World!"));
        // 第五部 操作完成后必须执行文档关闭操作。
        document.close();
    }
}