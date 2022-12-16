// By: Tommy
// Sprint: 3

package com.troo.controllers.util;

import java.io.File;
import java.util.Hashtable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

// Class to create a QR Code
public class QRCode {
    // https://www.digitalocean.com/community/tutorials/java-qr-code-generator-zxing-example

    // Create QR Code, takes in file name and text to encode
    public static void createQRImage(File qrFile, String qrCodeText) {
        int matrixWidth = 0;
        // Try to encode the String into a QR Code image
        try {
            // Create the ByteMatrix for the QR-Code that encodes the given String
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 1000, 1000, hintMap);

            // Make the BufferedImage that are to hold the QRCode
            matrixWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);

            // Print and save the image using the ByteMatrix
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            // Write the image to a file
            ImageIO.write(image, "png", qrFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ----- To be used for generateing the QR code
// String qrCodeText = GenerateCode.transactionCode().toString();
// String filePath = "JD.png";
// File qrFile = new File(filePath);
// QRCode.createQRImage(qrFile, qrCodeText);
// System.out.println("DONE");
// GenerateReceipt.generateReceipt();