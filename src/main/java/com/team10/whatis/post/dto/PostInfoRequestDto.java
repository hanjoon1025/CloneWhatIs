package com.team10.whatis.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostInfoRequestDto {

    @Size(max = 40, message = "{title}")
    private String title;

    @Range(min = 500000, max = 100000000, message = "{targetAmount}")
    private int targetAmount;

    @Range(min = 1000, message = "{price}")
    private int price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Future(message = "{future}")
    private LocalDate deadLine;

    private List<String> searchTag = new ArrayList<>();
}
