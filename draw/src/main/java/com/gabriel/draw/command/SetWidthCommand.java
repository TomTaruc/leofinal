package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

public class SetWidthCommand implements Command {
    private AppService appService;
    private int oldValue;
    private int newValue;

    public SetWidthCommand(AppService appService, int oldValue, int newValue) {
        this.appService = appService;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        appService.setWidth(newValue);
    }

    @Override
    public void undo() {
        appService.setWidth(oldValue);
    }

    @Override
    public void redo() {
        execute();
    }
}