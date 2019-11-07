package com.seay.game_of_thrones.network.exception;

import androidx.annotation.Nullable;

import java.net.SocketTimeoutException;

public class NetworkTimeoutException extends SocketTimeoutException {
    public NetworkTimeoutException(@Nullable String errorMessage) {
        super(errorMessage);
    }
}
