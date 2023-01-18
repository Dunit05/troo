// By: Tommy
// Sprint: 7

package com.troo.controllers.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

// Import all the necessary libraries for PDF creation
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border3D;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class GenerateReceipt {

    public static final float COL = 300f, DOUBLE_COL = 600f, TWO_COL = 350f, SEC_TWO_COL = 150f, THREE_COL = 190f;
    public static final int MAX_ID = 1000000, ONE_HUNDRED = 100, BORDER_SIZE = 1, FONT_SIZE = 10,
            IMG_WIDTH = 100,
            IMG_HEIGHT = 100, TAX_RATE = 13, DELIVERY_FEE = 25;

    public static void generateReceipt() {
        // Formatting for receipt columns
        float[] twoColWidth = { TWO_COL, TWO_COL };
        float[] threeColWidthForTop = { COL, COL, COL };
        float[] twoColWidthForInfo = { COL, COL };
        float[] threeColWidth = { THREE_COL, THREE_COL, THREE_COL };
        float[] paymentColWidth = { DOUBLE_COL, COL };
        float[] itemsColWidth = { COL, DOUBLE_COL, COL, COL };
        float[] fullColWidth = { DOUBLE_COL };

        // Create a new pdf document with FileOutputStream
        try {
            FileOutputStream fos = new FileOutputStream(
                    "src/main/resources/com/troo/data/transaction_data/" + StorageBucket.getReceiptNumber() + ".pdf");
            PdfWriter writer = new PdfWriter(fos);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdf);

            // Outline the receipt tebles and lines and space objects
            Table receiptInfoTable = new Table(threeColWidthForTop);
            Table nestedReciptInfoTable = new Table(twoColWidth);
            Table informationTable = new Table(twoColWidthForInfo);
            Table nestedInfoTo = new Table(twoColWidthForInfo);
            Table nestedInfoFrom = new Table(twoColWidthForInfo);
            Table itemsHeader = new Table(itemsColWidth);
            Table items = new Table(itemsColWidth);
            Table nestedItems = new Table(itemsColWidth);
            Table paymentInfo = new Table(paymentColWidth);
            Table thankyou = new Table(fullColWidth);
            Table line1 = new Table(threeColWidth);
            Table line2 = new Table(threeColWidth);
            Paragraph space = new Paragraph("\n");
            SolidBorder sb = new SolidBorder(BORDER_SIZE);
            line1.setBorder(sb);
            DottedBorder db = new DottedBorder(BORDER_SIZE);
            line2.setBorder(db);

            // Get logo
            Image image = new Image(ImageDataFactory.create("src/main/resources/com/troo/img/icon.png"));
            image.scaleAbsolute(IMG_WIDTH, IMG_HEIGHT);
            receiptInfoTable.addCell(new Cell().add(image).setBorder(Border3D.NO_BORDER));

            // Get qr code
            Image qrCode = new Image(ImageDataFactory.create("src/main/resources/com/troo/data/transaction_data/"
                    + StorageBucket.getTransactionNumber() + ".png"));
            qrCode.scaleAbsolute(IMG_WIDTH, IMG_HEIGHT);
            receiptInfoTable.addCell(new Cell().add(qrCode).setBorder(Border3D.NO_BORDER));

            // Start adding the receipt information
            nestedReciptInfoTable.addCell(documentText("\nReceipt No.:"));
            nestedReciptInfoTable.addCell(documentText("\n" + StorageBucket.getReceiptNumber()));
            nestedReciptInfoTable.addCell(documentText("\nAuth No.:"));
            nestedReciptInfoTable.addCell(documentText("\n" + StorageBucket.getTransactionNumber()));
            nestedReciptInfoTable.addCell(documentText("Date:"));
            nestedReciptInfoTable.addCell(documentText(getDate()));
            nestedReciptInfoTable.addCell(documentText("Delivery Time:"));
            nestedReciptInfoTable.addCell(documentText(String.valueOf(StorageBucket.getDeliveryTime()) + " minutes"));

            receiptInfoTable.addCell(new Cell().add(nestedReciptInfoTable).setBorder(Border3D.NO_BORDER));

            informationTable.addCell(documentText("Receipt To:"));
            informationTable.addCell(documentText("Receipt From:"));

            nestedInfoTo.addCell(documentText("Full Name:"));
            nestedInfoTo.addCell(documentText(StorageBucket.getUserName()));
            nestedInfoTo.addCell(documentText("Phone:"));
            nestedInfoTo.addCell(documentText(StorageBucket.getUserPhone()));
            nestedInfoTo.addCell(documentText("Email:"));
            nestedInfoTo.addCell(documentText(StorageBucket.getUserEmail()));
            nestedInfoTo.addCell(documentText("Address:"));
            nestedInfoTo.addCell(documentText(StorageBucket.getUserAddress()));

            nestedInfoFrom.addCell(documentText("Company:"));
            nestedInfoFrom.addCell(documentText("tróo"));
            nestedInfoFrom.addCell(documentText("Email:"));
            nestedInfoFrom.addCell(documentText("troo@furot.tech"));
            nestedInfoFrom.addCell(documentText("Delivery Method:"));
            nestedInfoFrom.addCell(documentText("Delivery"));

            informationTable.addCell(new Cell().add(nestedInfoTo).setBorder(Border3D.NO_BORDER));
            informationTable.addCell(new Cell().add(nestedInfoFrom).setBorder(Border3D.NO_BORDER));

            // Add the item column headers
            itemsHeader.setBackgroundColor(WebColors.getRGBColor("#CFD2CF"), 1f);
            itemsHeader.addCell(documentText("Item"));
            itemsHeader.addCell(documentText("Restaurant"));
            itemsHeader.addCell(documentText("Description"));
            itemsHeader.addCell(documentText("Price $"));

            items.setBackgroundColor(WebColors.getRGBColor("#FFFFFF"), 1f);
            // Add the items
            for (int i = 0; i < StorageBucket.getCart().size(); i++) {
                items.addCell(documentText(StorageBucket.getCart().get(i).getName()));
                items.addCell(documentText(StorageBucket.getCart().get(i).getRestaurantName()));
                items.addCell(documentText(StorageBucket.getCart().get(i).getDescription()));
                items.addCell(documentText(StorageBucket.getCart().get(i).getPrice() + ""));
            }

            // Adding the final transaction information
            paymentInfo.addCell(documentText("Total Quantity:"));
            paymentInfo.addCell(documentText(String.valueOf(StorageBucket.getCartAmount())));

            paymentInfo.addCell(documentText("Devliery Fee:"));
            paymentInfo.addCell(documentText("$" + String.valueOf(StorageBucket.getDeliveryFee())));

            paymentInfo.addCell(documentText("Tax:"));
            paymentInfo.addCell(documentText(TAX_RATE + "%"));

            paymentInfo.addCell(documentText("Total:"));
            paymentInfo.addCell(documentText("$" + StorageBucket.getCartTotalWithTax()));

            // Add a thank you message
            thankyou.addCell(documentText("Thank you for your purchase! We hope to serve you again soon!"));

            // Add all the tables to the document
            document.add(receiptInfoTable);
            document.add(space);
            document.add(line1);
            document.add(space);
            document.add(informationTable);
            document.add(space);
            document.add(line2);
            document.add(space);
            document.add(itemsHeader);
            document.add(items);
            document.add(nestedItems);
            document.add(space);
            document.add(line2);
            document.add(space);
            document.add(paymentInfo);
            document.add(space);
            document.add(line2);
            document.add(space);
            document.add(thankyou);

            // Close the document
            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Get the current date
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return month + "/" + day + "/" + year;
    }

    // Create a cell with the text
    public static Cell documentText(String text) {
        Cell cell = new Cell().add(new Paragraph(text)).setBorder(Border3D.NO_BORDER).setFontSize(FONT_SIZE).setBold();
        return cell;
    }

}
