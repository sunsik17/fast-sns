package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
	final private Long id;

	private String nickName;

	final private String email;

	final private LocalDate birthDay;

	final private LocalDateTime createdAt;

	final private static Long NAME_MAX_LENGTH = 10L;
	@Builder
	public Member(Long id, String nickName, String email, LocalDate birthDay,
		LocalDateTime createdAt) {
		this.id = id;
		this.email = Objects.requireNonNull(email);
		this.birthDay = Objects.requireNonNull(birthDay);

		validateNickName(nickName);
		this.nickName = Objects.requireNonNull(nickName);

		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}

	private void validateNickName(String nickName) {
		if (nickName.length() < 1 || nickName.length() > NAME_MAX_LENGTH) throw new RuntimeException("최대 길이를 초과했습니다.");
	}

	public void changeNickname(String to) {
		validateNickName(Objects.requireNonNull(to));
		this.nickName = to;
	}
}
