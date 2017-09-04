package com.recruiting.service.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Martha on 9/4/2017.
 */
public abstract class AbstractEmailService {

    protected String fileReader(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            InputStream loggerStream = AWSEmailServiceImpl.class.getClassLoader().getResourceAsStream(filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(loggerStream, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();
        return content;
    }
}
