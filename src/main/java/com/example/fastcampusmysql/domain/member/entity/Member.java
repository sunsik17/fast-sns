package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
	final private Long id;

	private String nickname;

	final private String email;

	final private LocalDate birthday;

	final private LocalDateTime createdAt;

	final private static Long NAME_MAX_LENGTH = 10L;
	@Builder
	public Member(Long id, String nickname, String email, LocalDate birthday,
		LocalDateTime createdAt) {
		this.id = id;
		this.email = Objects.requireNonNull(email);
		this.birthday = Objects.requireNonNull(birthday);

		validateNickName(nickname);
		this.nickname = Objects.requireNonNull(nickname);

		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}

	private void validateNickName(String nickName) {
		if (nickName.length() < 1 || nickName.length() > NAME_MAX_LENGTH) throw new RuntimeException("최대 길이를 초과했습니다.");
	}

	public void changeNickname(String to) {
		validateNickName(Objects.requireNonNull(to));
		this.nickname = to;
	}
}
