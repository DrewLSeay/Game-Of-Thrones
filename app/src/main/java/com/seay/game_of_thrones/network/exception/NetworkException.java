package com.seay.game_of_thrones.network.exception;

import androidx.annotation.NonNull;

import java.io.IOException;

public class NetworkException extends IOException {

    private String errorMessage;
    private int httpErrorCode;

    public NetworkException(@NonNull String errorMessage, @NonNull int httpErrorCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpErrorCode = httpErrorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    @Override
    public String toString() {
        return "NetworkException{ "
                + "errorMessage= '" + errorMessage + '\''
                + "' httpErrorCode = " + httpErrorCode + '}';
    }

}
