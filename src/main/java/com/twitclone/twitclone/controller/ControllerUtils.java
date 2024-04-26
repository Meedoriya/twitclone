package com.twitclone.twitclone.controller;

import com.twitclone.twitclone.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class ControllerUtils {

    @Value("${upload.path}")
    private static String uploadPath;

    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    public void saveFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(uploadPath);

            // Create directory if it doesn't exist
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Determine the file's name, perhaps adding a UUID to avoid overwriting
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            // Save the file
            file.transferTo(new File(uploadPath + "/" + resultFilename));

            // Update the message with the path or name of the file
            message.setFilename(resultFilename);
            // Assume a setter method exists in Message class for filename
        }
    }
}
