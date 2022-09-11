package com.zxw.export;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author zxw
 * @date 2022/9/11 13:27
 */
public class PagingSeal {
    public static final String templatePath = "D:\\软件存储\\WeChat Files\\wxid_6443924439612\\FileStorage\\File\\2021-09\\9f166abdf145487e85de230c5d066c5c.pdf";

    public static void main(String[] args) {
        PdfDocument pdf = new PdfDocument();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            PdfReader reader = new PdfReader(templatePath);
            PdfStamper stamper = new PdfStamper(reader, bos);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
