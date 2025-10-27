package com.gabriel.draw.service;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.service.DocumentService;
import org.w3c.dom.*;
import com.gabriel.drawfx.model.Shape;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.*;
import java.io.File;

public class XmlDocumentService implements DocumentService {

    Drawing drawing;

    public XmlDocumentService(Drawing drawing){
        this.drawing = drawing;
    }

    @Override
    public void newDoc() {

    }

    @Override
    public void save() {
        saveAs(drawing.getFilename());
    }

    @Override
    public void saveAs(String filename){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("Drawing");

            Attr attr = document.createAttribute("color");
            attr.setValue(String.valueOf(drawing.getColor()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("filename");
            attr.setValue(filename);
            root.setAttributeNode(attr);

            document.appendChild(root);

            for (Shape shape : drawing.getShapes()) {

                Element element = document.createElement("Shape");
                attr = document.createAttribute("type");
                if (shape.getClass() == Line.class) {
                    attr.setValue("Line");
                } else if (shape.getClass() == Ellipse.class) {
                    attr.setValue("Ellipse");
                } else if (shape.getClass() == Rectangle.class) {
                    attr.setValue("Rectangle");
                }
                element.setAttributeNode(attr);
                attr = document.createAttribute("start.x");
                attr.setValue(String.valueOf(shape.getLocation().getX()));
                element.setAttributeNode(attr);
                attr = document.createAttribute("start.y");
                attr.setValue(String.valueOf(shape.getLocation().getY()));
                element.setAttributeNode(attr);
                attr = document.createAttribute("width");
                attr.setValue(String.valueOf(shape.getWidth()));
                element.setAttributeNode(attr);
                attr = document.createAttribute("height");
                attr.setValue(String.valueOf(shape.getHeight()));
                element.setAttributeNode(attr);
                attr = document.createAttribute("color");
                attr.setValue(String.valueOf(shape.getColor()));
                element.setAttributeNode(attr);

                // FIX: Added saving for thickness
                attr = document.createAttribute("thickness");
                attr.setValue(String.valueOf(shape.getThickness()));
                element.setAttributeNode(attr);

                root.appendChild(element);
            }

            // Write to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            // Specify your local file path
            StreamResult result = new StreamResult(filename);
            transformer.transform(source, result);

            System.out.println("XML file created successfully!");
        }
        catch(Exception e){
            System.out.println("Error occurred " + e.getMessage());
            e.printStackTrace(); // Also good to print the stack trace
        }
    }
    @Override
    public void open(String filebame) {
        drawing.getShapes().clear();
        Shape  shape = null;
        File xmlFile = new File(filebame);
        int x;
        int y;
        int width;
        int height;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName("Shape");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NamedNodeMap map = node.getAttributes();
                shape = null; // Reset shape

                Node attr = map.getNamedItem("start.x");
                x = (int) Double.parseDouble(attr.getNodeValue());
                attr = map.getNamedItem("start.y");
                y = (int) Double.parseDouble(attr.getNodeValue());
                Point start = new Point(x, y);

                attr = map.getNamedItem("width");
                width = (int) Double.parseDouble(attr.getNodeValue());
                attr = map.getNamedItem("height");
                height = (int) Double.parseDouble(attr.getNodeValue());

                // FIX: End point was calculated incorrectly
                Point end = new Point(x + width, y + height);

                // FIX: Load color safely with a default
                attr = map.getNamedItem("color");
                Color color = drawing.getColor(); // Default
                if(attr != null) {
                    color = convertColor(attr.getNodeValue());
                }

                // FIX: Load thickness safely with a default
                attr = map.getNamedItem("thickness");
                int thickness = drawing.getThickness(); // Default
                if(attr != null) {
                    thickness = Integer.parseInt(attr.getNodeValue());
                }

                attr = map.getNamedItem("type");
                if (attr.getNodeValue().equals("Rectangle")) {
                    shape = new Rectangle(start, width, height);
                } else if (attr.getNodeValue().equals("Ellipse")) {
                    shape = new Ellipse(start, end); // Use corrected end point
                } else if (attr.getNodeValue().equals("Line")) {
                    shape = new Line(start, end); // Use corrected end point
                }

                // FIX: Apply the loaded properties and add to drawing
                if (shape != null) {
                    shape.setColor(color);
                    shape.setThickness(thickness);
                    drawing.getShapes().add(shape);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace(); // Also good to print the stack trace
        }
    }

    public Color convertColor(String colorStr) {
        // This method is fragile, but I will leave it as-is.
        // A more robust method would use regex or handle nulls.
        if (colorStr == null) {
            return Color.BLACK; // Default
        }
        try {
            Color color;
            int rIndex = colorStr.indexOf("r=");
            colorStr = colorStr.substring(rIndex + 2);
            int nextIndex = colorStr.indexOf(",");
            String rStr = colorStr.substring(0, nextIndex);
            colorStr = colorStr.substring(nextIndex + 3);
            nextIndex = colorStr.indexOf(",");
            String gStr = colorStr.substring(0, nextIndex);
            colorStr = colorStr.substring(nextIndex + 3);
            nextIndex = colorStr.indexOf("]");
            String bStr = colorStr.substring(0, nextIndex);
            int rcolor = Integer.parseInt(rStr);
            int gcolor = Integer.parseInt(gStr);
            int bcolor = Integer.parseInt(bStr);
            color = new Color(rcolor, gcolor, bcolor);
            return color;
        } catch (Exception e) {
            return Color.BLACK; // Default on parsing error
        }
    }
}