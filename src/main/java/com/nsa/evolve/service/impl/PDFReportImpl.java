package com.nsa.evolve.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.nsa.evolve.form.PDFForm;
import com.nsa.evolve.service.CompanyService;
import com.nsa.evolve.service.PDFReport;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by c1633899 on 10/12/2017.
 */
@Service
public class PDFReportImpl implements PDFReport {

    @Autowired
    private CompanyService companyService;
    private static final int WIDTH = 640, HEIGHT = 480;

//    Creating a pdf File
//    https://www.youtube.com/watch?v=_Sy4D1aAzrU
    public byte[] createReport(PDFForm pdfForm){
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter pdfWriter =  PdfWriter.getInstance(document, out);
            Font casual = FontFactory.getFont("Segoe UI", 14);

            document.open();
            document.add(new Paragraph(addTitle("Introduction")));
            document.add(new Paragraph(pdfForm.getIntroduction(), casual));

            document.add(new Paragraph(addTitle("Statistics")));

//            https://stackoverflow.com/questions/29509154/display-base64-encoded-string-as-image-in-a-pdf-file-using-itext
            byte[] decodedOne = Base64.decodeBase64(pdfForm.getQviScore().getBytes());
            Image imageOne = Image.getInstance(decodedOne);
            imageOne.scaleToFit(500, 480);
            imageOne.setAlignment(Image.ALIGN_CENTER);
            document.add(imageOne);

            byte[] decodedSix = Base64.decodeBase64(pdfForm.getModuleFive().getBytes());
            Image imageSix = Image.getInstance(decodedSix);
            imageSix.scaleToFit(500, 480);
            imageSix.setAlignment(Image.ALIGN_CENTER);
            document.add(imageSix);

            byte[] decodedFive = Base64.decodeBase64(pdfForm.getModuleFour().getBytes());
            Image imageFive = Image.getInstance(decodedFive);
            imageFive.scaleToFit(300, 300);
            imageFive.setAlignment(Image.ALIGN_CENTER);
            document.add(imageFive);

            byte[] decodedFour = Base64.decodeBase64(pdfForm.getModuleThree().getBytes());
            Image imageFour = Image.getInstance(decodedFour);
            imageFour.scaleToFit(300, 300);
            imageFour.setAlignment(Image.ALIGN_CENTER);
            document.add(imageFour);

            byte[] decodedThree = Base64.decodeBase64(pdfForm.getModuleTwo().getBytes());
            Image imageThree = Image.getInstance(decodedThree);
            imageThree.scaleToFit(300, 300);
            imageThree.setAlignment(Image.ALIGN_CENTER);
            document.add(imageThree);

            byte[] decodedTwo = Base64.decodeBase64(pdfForm.getModuleOne().getBytes());
            Image imageTwo = Image.getInstance(decodedTwo);
            imageTwo.scaleToFit(300, 300);
            imageTwo.setAlignment(Image.ALIGN_CENTER);
            document.add(imageTwo);

            document.add(new Paragraph(addTitle("Conclusion")));
            document.add(new Paragraph(pdfForm.getConclusion(), casual));

            document.close();
            pdfWriter.close();
        }

        catch (Exception e){
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public Paragraph addTitle(String title){
        Font bold = FontFactory.getFont("Verdana", 27, Font.BOLD);
        Paragraph paragraph = new Paragraph(title, bold);
        paragraph.setSpacingAfter(10);
        paragraph.setAlignment(0);
        return paragraph;
    }
}
