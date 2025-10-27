package com.gabriel.draw.command;

import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

public class SetShapeModeCommand implements Command {
    private AppService appService;
    private ShapeMode oldValue;
    private ShapeMode newValue;

    public SetShapeModeCommand(AppService appService, ShapeMode oldValue, ShapeMode newValue) {
        this.appService = appService;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        appService.setShapeMode(newValue);
    }

    @Override
    public void undo() {
        appService.setShapeMode(oldValue);
    }

    @Override
    public void redo() {
        execute();
    }
}