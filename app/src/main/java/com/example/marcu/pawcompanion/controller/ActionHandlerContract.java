package com.example.marcu.pawcompanion.controller;

import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

public interface ActionHandlerContract {
    interface RootActionHandler{
        void invokeAction(HandlerType handlerType, Action action);
    }

    interface ActionHandler{
        void setNextHandler(ActionHandler handler);
        void handle(HandlerType handlerType, Action action);
    }
}
