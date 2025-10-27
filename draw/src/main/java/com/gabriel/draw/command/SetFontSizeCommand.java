package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;
import java.awt.Font;

public class SetFontSizeCommand implements Command {
    private AppService appService;
    private int oldValue;
    private int newValue;
    private Font oldFont; // Need full font to restore

    public SetFontSizeCommand(AppService appService, int oldValue, int newValue, Font oldFont) {
        this.appService = appService;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.oldFont = oldFont;
    }

    @Override
    public void execute() {
        appService.setFontSize(newValue);
    }

    @Override
    public void undo() {
        // appService.setFontSize(oldValue) is not enough, must restore full font
        if (appService.getSelectedShape() != null) {
            appService.getSelectedShape().setFont(oldFont);
        } else {
            appService.getDrawing().setFont(oldFont);
        }
        appService.setFontSize(oldValue); // trigger repaint
    }

    @Override
    public void redo() {
        execute();
    }
}