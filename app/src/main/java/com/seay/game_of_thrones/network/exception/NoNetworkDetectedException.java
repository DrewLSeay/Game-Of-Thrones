package com.seay.game_of_thrones.network.exception;

import androidx.annotation.Nullable;

import java.net.UnknownHostException;

public class NoNetworkDetectedException extends UnknownHostException {
    public NoNetworkDetectedException(@Nullable String errorMessage) {
        super(errorMessage);
    }
}
