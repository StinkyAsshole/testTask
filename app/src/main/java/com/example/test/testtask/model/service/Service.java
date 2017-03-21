package com.example.test.testtask.model.service;

import com.example.test.testtask.Tools.BaseUrl;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    /**
     *  Создает сервис по указанному классу
     * @param clazz Класс сервиса. Обязан иметь аннотацию {@link BaseUrl}
     * @param addParam Дополнительные параметры, передаваемые с каждым запросом
     * @return Retrofit сервис
     */
    public static <T> T create(final Class<T> clazz, String... addParam) {
        if (!clazz.isAnnotationPresent(BaseUrl.class)){
            throw new RuntimeException("Unexpected service. Unknown service base url. Annotation @BaseUrl is not present");
        }
        BaseUrl baseUrl = clazz.getAnnotation(BaseUrl.class);

        // интерцептор для логирования запросов
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        // интерцептор для добавления доп. параметров к каждому запросу
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl.Builder builer = originalHttpUrl.newBuilder();

            applyParam(builer, baseUrl.params());
            applyParam(builer, addParam);

            HttpUrl url = builer.build();
            Request.Builder requestBuilder = original.newBuilder().url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        // интерпритация underscope В camelcase
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl.value())
                .client(httpClient.build())
                .build()
                .create(clazz);
    }

    private static void applyParam(HttpUrl.Builder builer, String... params){
        for (int i = 0; i < params.length; i+=2) {
            builer.addQueryParameter(params[i], params[i+1]);
        }
    }
}
