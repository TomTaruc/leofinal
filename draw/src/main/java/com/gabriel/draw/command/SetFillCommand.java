package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;
import java.awt.Color;

public class SetFillCommand implements Command {
    private AppService appService;
    private Color oldValue;
    private Color newValue;

    public SetFillCommand(AppService appService, Color oldValue, Color newValue) {
        this.appService = appService;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        appService.setFill(newValue);
    }

    @Override
    public void undo() {
        appService.setFill(oldValue);
    }

    @Override
    public void redo() {
        execute();
    }
}