package com.example.fastcampusmysql.domain.post.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record DailyPostCount(
	Long memberId,
	@DateTimeFormat(iso = ISO.DATE)
	LocalDate date,
	@DateTimeFormat(iso = ISO.DATE)
	Long postCount
) {

}
