package com.gabriel.draw.controller;

import com.gabriel.draw.command.*;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.command.Command;
import java.awt.Color;
import java.awt.Font;

public class PropertyEventListener extends PropertyEventAdapter {
    private AppService appService;

    public PropertyEventListener(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void onPropertyUpdated(Property property) {

        // Get the value *before* creating the command
        // Note: appService.get...() will return the property of the selected shape if one exists

        if (property.getName().equals("Fill color")) {
            Color oldValue = appService.getFill();
            Color newValue = (Color) property.getValue();
            if (!oldValue.equals(newValue)) {
                Command cmd = new SetFillCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Fore color")) {
            Color oldValue = appService.getColor();
            Color newValue = (Color) property.getValue();
            if (!oldValue.equals(newValue)) {
                Command cmd = new SetColorCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("X Location")) {
            int oldValue = appService.getXLocation();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                // We bundle X and Y updates together in a single command
                int oldY = appService.getYLocation();
                Command cmd = new SetPositionCommand(appService, oldValue, oldY, newValue, oldY);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Y Location")) {
            int oldValue = appService.getYLocation();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                int oldX = appService.getXLocation();
                Command cmd = new SetPositionCommand(appService, oldX, oldValue, oldX, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Width")) {
            int oldValue = appService.getWidth();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                Command cmd = new SetWidthCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Height")) {
            int oldValue = appService.getHeight();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                Command cmd = new SetHeightCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Line Thickness")) {
            int oldValue = appService.getThickness();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                Command cmd = new SetThicknessCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Text")) {
            String oldValue = appService.getText();
            String newValue = (String) property.getValue();
            if (!oldValue.equals(newValue)) {
                Command cmd = new SetTextCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Font size")) {
            Font oldFont = appService.getFont();
            int oldValue = oldFont.getSize();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                Command cmd = new SetFontSizeCommand(appService, oldValue, newValue, oldFont);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Font Family")) {
            Font oldFont = appService.getFont();
            String oldValue = oldFont.getFamily();
            String newValue = (String) property.getValue();
            if (!oldValue.equals(newValue)) {
                Command cmd = new SetFontFamilyCommand(appService, oldFont, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Font Style")) {
            Font oldFont = appService.getFont();
            int oldValue = oldFont.getStyle();
            int newValue = (int) property.getValue();
            if (oldValue != newValue) {
                Command cmd = new SetFontStyleCommand(appService, oldValue, newValue, oldFont);
                CommandService.ExecuteCommand(cmd);
            }
        } else if (property.getName().equals("Current Shape")) {
            ShapeMode oldValue = appService.getShapeMode();
            ShapeMode newValue = (ShapeMode) property.getValue();
            if (oldValue != newValue) {
                // FIX: Renamed class from SetShapeModeCommand to SetShapeCommand
                Command cmd = new SetShapeCommand(appService, oldValue, newValue);
                CommandService.ExecuteCommand(cmd);
            }
        }
    }
}