package com.stepnik.kornel.bookshare.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by korSt on 25.11.2016.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("authorization", "Bearer ".concat(AppData.loggedUser.getToken()))
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}