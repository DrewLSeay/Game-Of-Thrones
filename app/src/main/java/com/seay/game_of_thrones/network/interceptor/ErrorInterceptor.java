package com.seay.game_of_thrones.network.interceptor;

import android.content.Context;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.network.exception.NetworkException;
import com.seay.game_of_thrones.network.exception.NetworkTimeoutException;
import com.seay.game_of_thrones.network.exception.NoNetworkDetectedException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

public class ErrorInterceptor implements Interceptor {
    private Context context;

    public ErrorInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response;

        try {
            response = chain.proceed(chain.request());
        } catch (UnknownHostException notConnectedToNetworkException) {
            NoNetworkDetectedException noNetworkDetectedException = new NoNetworkDetectedException(context.getString(R.string.no_network_detected));
            Timber.w(noNetworkDetectedException, "UnknownHostException. Check network connection");
            throw noNetworkDetectedException;
        } catch (SocketTimeoutException timeoutException) {
            NetworkTimeoutException networkTimeoutException = new NetworkTimeoutException(context.getString(R.string.timeout));
            Timber.w(networkTimeoutException, "NetworkTimeoutException. Network times out");
            throw networkTimeoutException;
        }

        if (!response.isSuccessful()) {
            String errorDescription = response.message();
            int httpErrorCode = response.code();

            if (httpErrorCode == 500) {
                errorDescription = "Internal Server Error (500)";
            }

            NetworkException networkException = new NetworkException(errorDescription, httpErrorCode);
            Timber.w(networkException, "Network Error occurred: %s", networkException.toString());
            throw networkException;
        }
        return response;
    }


}
