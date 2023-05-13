package com.team10.whatis.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostInfoRequestDto {
    @Size(max = 40, message = "{title}")
    @NotBlank
    private String title;

    @Size(min = 500000, max = 100000000, message = "{targetAmount}")
    private int targetAmount;

    @Size(min = 1000, message = "{price}")
    private int price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Future(message = "{future}")
    private LocalDate deadLine;

    private List<String> searchTag = new ArrayList<>();
}
