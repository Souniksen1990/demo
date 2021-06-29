package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@io.micronaut.http.annotation.Controller(value = "api/v0")
public class Controller {
    @Post(uri = "/upload", consumes = MediaType.APPLICATION_OCTET_STREAM, produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> saveLargeFile(@Body InputStream bytes) throws IOException {
        System.out.println("handling file upload");
        try(FileOutputStream outputStream = new FileOutputStream("upload")) {
            IOUtils.copy(bytes, outputStream); //using package apache.commons.io
        } catch (FileNotFoundException e) {
            System.out.println("Upload failed");
            return HttpResponse.serverError().body("failed").contentType(MediaType.TEXT_PLAIN);
        }
        bytes.close();
        System.out.println("Upload Success");
        return HttpResponse.ok().body("success").contentType(MediaType.TEXT_PLAIN);
    }
}
