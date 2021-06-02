package com.zxw.business.export;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;

/**
 * 第1章：PDF和iText简介
 * 1.1第一个iText示例：Hello World
 */
public class HelloWorld {
    PdfReader reader;
    FileOutputStream out;
    ByteArrayOutputStream bos;
    PdfStamper stamper;

    /**
     * 生成的PDF文件的路径。
     */
    public static final String newPDFPath = "C:\\Users\\zxw\\Desktop\\hello.pdf";
    public static final String templatePath = "C:\\Users\\zxw\\Desktop\\个人三方-法人（中农准入材料）.pdf";
//    public static final String templatePath = "C:\\Users\\zxw\\Desktop\\44320643419770944.pdf";

    /**
     * 创建一个PDF文件：hello.pdf
     *
     * @param args no arguments needed
     */
    public static void main(String[] args) throws DocumentException, IOException {
        HelloWorld helloWorld = new HelloWorld();
//        helloWorld.createPdf(newPDFPath);
        helloWorld.fillPDF();
    }

    public void fillPDF() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("authName", "爱仕达多");
            map.put("time", "2021-4-27");
            map.put("legalName", "期望的所所");
            map.put("YWLX", "请问请问");
            map.put("authName", "请问请问");
            map.put("time", "2021-06-01 16:36:47");
            map.put("idNO", "320323199106060631");
            map.put("GLGS", "请问请问");
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
            BaseFont bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            while (formField.hasNext()) {
                String next = formField.next();
                form.setFieldProperty(next, "textfont", bf, null);
                // 填充pdf数据
                form.setField(next, String.valueOf(map.get(next)));
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