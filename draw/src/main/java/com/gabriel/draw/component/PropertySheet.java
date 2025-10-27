package com.gabriel.draw.component;

import com.gabriel.draw.controller.PropertyEventListener;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.PropertyPanel;
import com.gabriel.property.cell.SelectionCellComponent;
import com.gabriel.property.property.*;
import com.gabriel.property.property.selection.Item;
import com.gabriel.property.property.selection.SelectionProperty;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertySheet extends PropertyPanel {
    PropertyPanel propertyTable;
    private SelectionProperty shapeProp;
    Item RectangleItem;
    Item EllipseItem;
    Item LineItem;
    Item TextItem;
    Item SelectItem;
    Item ImageItem; // Added for completeness

    // Font Style items
    Item PlainItem;
    Item BoldItem;
    Item ItalicItem;
    Item BoldItalicItem;


    public void setShapeProp(ShapeMode shapeMode ){
        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        if (shapeMode ==ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        } else if (shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        } else if (shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        } else if (shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        } else if (shapeMode == ShapeMode.Text) {
            selectionComponent.setCellEditorValue(TextItem);
        } else if (shapeMode == ShapeMode.Image) {
            selectionComponent.setCellEditorValue(ImageItem);
        }
    }

    public PropertySheet(PropertyOptions options){
        super(options);

        // Initialize all shape items
        RectangleItem = new Item<>(ShapeMode.Rectangle, "Rectangle");
        EllipseItem = new Item<>(ShapeMode.Ellipse, "Ellipse");
        LineItem = new Item<>(ShapeMode.Line, "Line");
        TextItem = new Item<>(ShapeMode.Text, "Text");
        ImageItem = new Item<>(ShapeMode.Image, "Image");
        SelectItem = new Item<>(ShapeMode.Select, "Select");

        shapeProp = new SelectionProperty<>(
                "Current Shape",
                new ArrayList<>(Arrays.asList(
                        RectangleItem,
                        EllipseItem,
                        LineItem,
                        TextItem,
                        ImageItem,
                        SelectItem
                ))
        );

        // Initialize font style items
        PlainItem = new Item<>(Font.PLAIN, "Plain");
        BoldItem = new Item<>(Font.BOLD, "Bold");
        ItalicItem = new Item<>(Font.ITALIC, "Italic");
        BoldItalicItem = new Item<>(Font.BOLD | Font.ITALIC, "Bold Italic");
    }

    public void populateTable(AppService appService) {
        propertyTable = this;
        // The listener is now added in DrawingFrame, no need to add it here.
        // propertyTable.addEventListener(new PropertyEventListener(appService));

        Shape shape  = appService.getSelectedShape();
        propertyTable.clear();

        if ( shape == null) {
            // NO SHAPE SELECTED: Show drawing-wide properties
            String objectType = "Drawing";
            StringProperty targetProp = new StringProperty("Object Type", objectType);
            propertyTable.addProperty(targetProp);

            // Re-create shapeProp to ensure it's fresh
            shapeProp = new SelectionProperty<>(
                    "Current Shape",
                    new ArrayList<>(Arrays.asList(
                            RectangleItem,
                            EllipseItem,
                            LineItem,
                            TextItem,
                            ImageItem,
                            SelectItem
                    ))
            );
            propertyTable.addProperty(shapeProp);

            // Set the dropdown to the current app service shape mode
            ShapeMode shapeMode = appService.getShapeMode();
            if (shapeMode == ShapeMode.Rectangle) {
                shapeProp.setValue(RectangleItem.getValue());
            } else if (shapeMode == ShapeMode.Ellipse) {
                shapeProp.setValue(EllipseItem.getValue());
            } else if (shapeMode == ShapeMode.Line) {
                shapeProp.setValue(LineItem.getValue());
            } else if (shapeMode == ShapeMode.Text) {
                shapeProp.setValue(TextItem.getValue());
            } else if (shapeMode == ShapeMode.Image) {
                shapeProp.setValue(ImageItem.getValue());
            } else if (shapeMode == ShapeMode.Select) {
                shapeProp.setValue(SelectItem.getValue());
            }

            ColorProperty currentColorProp = new ColorProperty("Fore color", appService.getColor());
            propertyTable.addProperty(currentColorProp);

            ColorProperty currentFillProp = new ColorProperty("Fill color", appService.getFill());
            propertyTable.addProperty(currentFillProp);

            IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", appService.getThickness());
            propertyTable.addProperty(lineThicknessProp);

            // Add Font properties for the *next* text shape
            StringProperty textProp = new StringProperty("Text", appService.getText());
            propertyTable.addProperty(textProp);

            StringProperty fontFamilyProp = new StringProperty("Font Family", appService.getFont().getFamily());
            propertyTable.addProperty(fontFamilyProp);

            SelectionProperty fontStyleProp = new SelectionProperty<>(
                    "Font Style",
                    new ArrayList<>(Arrays.asList(PlainItem, BoldItem, ItalicItem, BoldItalicItem))
            );
            fontStyleProp.setValue(appService.getFont().getStyle());
            propertyTable.addProperty(fontStyleProp);

            IntegerProperty fontSizeProp = new IntegerProperty("Font size", appService.getFont().getSize());
            propertyTable.addProperty(fontSizeProp);

        }
        else {
            // SHAPE IS SELECTED: Show shape-specific properties
            StringProperty targetProp = new StringProperty("Object Type", shape.getClass().getSimpleName());
            propertyTable.addProperty(targetProp);

            // X, Y, Width, Height are relevant here
            IntegerProperty xlocProp = new IntegerProperty("X Location", shape.getLocation().x);
            propertyTable.addProperty(xlocProp);

            IntegerProperty ylocProp = new IntegerProperty("Y Location", shape.getLocation().y);
            propertyTable.addProperty(ylocProp);

            IntegerProperty widthProp = new IntegerProperty("Width", shape.getWidth());
            propertyTable.addProperty(widthProp);

            IntegerProperty heightProp = new IntegerProperty("Height", shape.getHeight());
            propertyTable.addProperty(heightProp);

            // Color properties
            ColorProperty currentColorProp = new ColorProperty("Fore color", shape.getColor());
            propertyTable.addProperty(currentColorProp);

            ColorProperty currentFillProp = new ColorProperty("Fill color", shape.getFill());
            propertyTable.addProperty(currentFillProp);

            IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", shape.getThickness());
            propertyTable.addProperty(lineThicknessProp);

            // Add text properties ONLY if the shape is a Text shape
            // (or potentially an Image shape if we add a caption feature)
            if (shape.getClass().getSimpleName().equals("Text")) {
                StringProperty textProp= new StringProperty("Text", shape.getText());
                propertyTable.addProperty(textProp);

                StringProperty fontFamilyProp= new StringProperty("Font Family", shape.getFont().getFamily());
                propertyTable.addProperty(fontFamilyProp);

                SelectionProperty fontStyleProp = new SelectionProperty<>(
                        "Font Style",
                        new ArrayList<>(Arrays.asList(PlainItem, BoldItem, ItalicItem, BoldItalicItem))
                );
                fontStyleProp.setValue(shape.getFont().getStyle());
                propertyTable.addProperty(fontStyleProp);

                IntegerProperty fontSizeProp= new IntegerProperty("Font size", shape.getFont().getSize());
                propertyTable.addProperty(fontSizeProp);
            }

            // Add image property ONLY if it's a Picture
            if (shape.getClass().getSimpleName().equals("Picture")) {
                // FIX: Added the missing 2nd argument "actionName" (the button text)
                ActionProperty imageProp = new ActionProperty("Change Image", "Change...", () -> {
                    appService.setImageFileename();
                });
                propertyTable.addProperty(imageProp);
            }
        }
    }
}