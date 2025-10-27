package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import java.awt.Font;

public class SetFontFamilyCommand implements Command {
    private AppService appService;
    private Font oldFont;
    private String newFamily;
    private Shape shape;

    public SetFontFamilyCommand(AppService appService, Font oldFont, String newFamily) {
        this.appService = appService;
        this.oldFont = oldFont;
        this.newFamily = newFamily;
        this.shape = appService.getSelectedShape(); // Capture shape at creation time
    }

    @Override
    public void execute() {
        // We use the appService's *implementation* to set, not the command-wrapped one.
        // This is a bit of a workaround for the service-wrapper model.
        // A better way would be for the command to talk to the *base* service.
        // For now, we manually set the font.

        Font font = new Font(newFamily, oldFont.getStyle(), oldFont.getSize());
        if (shape != null) {
            shape.setFont(font);
        } else {
            appService.getDrawing().setFont(font);
        }
        appService.setFontFamily(newFamily); // This will trigger repaint
    }

    @Override
    public void undo() {
        if (shape != null) {
            shape.setFont(oldFont);
        } else {
            appService.getDrawing().setFont(oldFont);
        }
        appService.setFontFamily(oldFont.getFamily()); // This will trigger repaint
    }

    @Override
    public void redo() {
        execute();
    }
}