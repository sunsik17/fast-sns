package com.example.fastcampusmysql.domain.timeline.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Timeline {
	private final Long id;
	private final Long memberId; // 커서 키가 될 것이다.
	private final Long postId;
	private final LocalDateTime createdAt;

	@Builder
	public Timeline(Long id, Long memberId, Long postId, LocalDateTime createdAt) {
		this.id = id;
		this.memberId = Objects.requireNonNull(memberId);
		this.postId = Objects.requireNonNull(postId);
		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}
}
