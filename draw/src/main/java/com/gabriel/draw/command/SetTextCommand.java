package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

public class SetTextCommand implements Command {
    private AppService appService;
    private String oldValue;
    private String newValue;

    public SetTextCommand(AppService appService, String oldValue, String newValue) {
        this.appService = appService;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        appService.setText(newValue);
    }

    @Override
    public void undo() {
        appService.setText(oldValue);
    }

    @Override
    public void redo() {
        execute();
    }
}