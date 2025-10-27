package com.gabriel.draw.service;

import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class DrawingAppService implements AppService {

    final private Drawing drawing;;

    @Setter
    DrawingView drawingView;
    ImageFileService imageFileService;
    MoverService moverService;
    ScalerService scalerService;
    SearchService searchService;
    XmlDocumentService xmlDocumentService;

    DocumentService documentService;
    public DrawingAppService(){
        drawing = new Drawing();
        moverService = new MoverService();
        scalerService = new ScalerService();
        searchService = new SearchService();
        xmlDocumentService = new XmlDocumentService(drawing);
        imageFileService = new ImageFileService();
        drawing.setDrawMode(DrawMode.Idle);
        drawing.setShapeMode(ShapeMode.Ellipse);
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public ShapeMode getShapeMode() {
        return drawing.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        drawing.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return drawing.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        this.drawing.setDrawMode(drawMode);
    }

    @Override
    public Color getColor() {
        return drawing.getColor();
    }

    @Override
    public void setColor(Color color) {
        List<Shape> shapes = drawing.getShapes();
        boolean isEmpty = true;
        for (Shape shape : shapes) {
            if (shape.isSelected()) {
                shape.setColor(color);
                shape.getRendererService().render(drawingView.getGraphics(), shape, false);
                isEmpty = false;
            }
        }
        if(isEmpty){
            drawing.setColor(color);
        }
    }

    @Override
    public Color getFill(){
        return drawing.getFill();
    }

    @Override
    public void setFill(Color color) {
        drawing.setFill(color);
    }

    @Override
    public void move(Shape shape, Point start, Point newLoc) {
        moverService.move(shape, start, newLoc);
    }

    @Override
    public void move(Point start, Point newLoc) {
        moverService.move(drawing, start, newLoc);
    }

    @Override
    public void scale(Point start, Point end) {
        scalerService.scale(drawing, start, end);
    }

    @Override
    public void scale(Shape shape, Point start, Point end) {
        scalerService.scale(shape, start, end);
    }

    @Override
    public void scale(Shape shape, Point end) {
        scalerService.scale(shape, end);
    }

    @Override
    public void create(Shape shape) {
        this.drawing.getShapes().add(shape);
        shape.setColor(drawing.getColor());
        shape.setR(drawing.getSearchRadius());
        shape.setFill(drawing.getFill());
        shape.setThickness(drawing.getThickness());
        shape.setId(this.drawing.getShapes().size());
        shape.setFont(drawing.getFont());
    }

    @Override
    public void delete(Shape shape) {
        drawing.getShapes().remove(shape);
    }

    @Override
    public void close() {
        System.exit(0);
    }

    @Override
    public Drawing getDrawing() {
        return drawing;
    }

    @Override
    public void setDrawing(Drawing drawing) {

    }

    @Override
    public int getSearchRadius() {
        return drawing.getSearchRadius();
    }

    @Override
    public void setSearchRadius(int radius) {
        drawing.setSearchRadius(radius);
    }

    @Override
    public void search(Point p) {
        searchService.search(this,p);
    }

    @Override
    public void search(Point p, boolean single) {
        searchService.search(this,p, single);
    }

    @Override
    public void open(String filename) {
        xmlDocumentService.open(filename);
    }

    @Override
    public void save() {
        xmlDocumentService.saveAs(drawing.getFilename());
    }

    @Override
    public void saveas(String filename) {
        xmlDocumentService.saveAs(filename);
    }

    @Override
    public void newDrawing() {

    }

    @Override
    public String getFileName() {
        return drawing.getFilename();
    }

    @Override
    public void select(Shape selectedShape) {
        List<Shape> selectedShapes = drawing.getShapes();
        for(Shape shape : selectedShapes){
            if(shape.equals(selectedShape)){
                shape.setSelected(true);
            }
            else {
                shape.setSelected(false);
            }
        }
    }

    @Override
    public void unSelect(Shape selectedShape) {
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes){
            if(shape.getId() == selectedShape.getId()) {
                shape.setSelected(false);
            }
        }
    }

    @Override
    public Shape getSelectedShape() {
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes){
            if(shape.isSelected()){
                return shape;
            }
        }
        return null;
    }
    @Override
    public List<Shape> getSelectedShapes() {
        List<Shape> shapes = drawing.getShapes();
        List<Shape> selectedShapes = new ArrayList<>();
        for (Shape shape : shapes){
            if(shape.isSelected()){
                selectedShapes.add(shape);
            }
        }
        return selectedShapes;
    }
    @Override
    public void clearSelections(){
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes){
            shape.setSelected(false);
            shape.setSelectionMode(SelectionMode.None);
        }
        drawing.setSelectedShape(null);
        drawingView.repaint();
    }

    @Override
    public void setThickness(int thickness) {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            drawing.setThickness(thickness);
        }
        else {
            seleectedShape.setThickness(thickness);
        }
    }

    @Override
    public int getThickness() {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
           return drawing.getThickness();
        }
        else {
            return seleectedShape.getThickness();
        }
    }

    @Override
    public void setXLocation(int thickness) {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            drawing.setThickness(thickness);
        }
        else {
            seleectedShape.setThickness(thickness);
        }

    }

    @Override
    public int getXLocation() {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            return drawing.getLocation().x;
        }
        else {
            return selectedShape.getLocation().x;
        }
    }

    @Override
    public void setYLocation(int yLocation) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.getLocation().y = yLocation;
        }
        else {
            selectedShape.getLocation().y = yLocation;
        }
    }

    @Override
    public int getYLocation() {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            return drawing.getLocation().y;
        }
        else {
            return seleectedShape.getLocation().y;
        }
    }

    @Override
    public void setWidth(int width) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.setWidth(width);
        }
        else {
            selectedShape.setWidth(width);
        }
    }

    @Override
    public int getWidth() {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            return drawing.getWidth();
        }
        else {
            return selectedShape.getWidth();
        }
    }

    @Override
    public void setHeight(int height) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.setHeight(height);
        }
        else {
            selectedShape.setHeight(height);
        }
    }

    @Override
    public int getHeight() {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            return 0;
            //return drawing.getHeight();
        }
        else {
            return selectedShape.getHeight();
        }
    }

    @Override
    public void setImageFileename() {
        imageFileService.setImage(drawing);
    }

    @Override
    public void setImageFileename(String filename) {
        drawing.setImageFilename(filename);
    }

    @Override
    public String getImageFileename() {
        return drawing.getImageFilename();
    }

    @Override
    public void setText(String text) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.setText(text);
        }
        else {
            selectedShape.setText(text);
        }
    }

    @Override
    public void setFontSize(int fontSize) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            Font font = new Font(drawing.getFont().getFamily(), drawing.getFont().getStyle(), fontSize);
            drawing.setFont(font);
        }
        else {
            Font font = new Font(selectedShape.getFont().getFamily(), selectedShape.getFont().getStyle(), fontSize);
            selectedShape.setFont(font);
        }
    }
}
