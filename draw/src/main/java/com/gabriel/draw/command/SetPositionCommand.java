package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

public class SetPositionCommand implements Command {
    private AppService appService;
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;
    private boolean isXChange;

    public SetPositionCommand(AppService appService, int oldX, int oldY, int newX, int newY) {
        this.appService = appService;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute() {
        appService.setXLocation(newX);
        appService.setYLocation(newY);
    }

    @Override
    public void undo() {
        appService.setXLocation(oldX);
        appService.setYLocation(oldY);
    }

    @Override
    public void redo() {
        execute();
    }
}