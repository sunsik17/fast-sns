package com.example.fastcampusmysql.domain.member.dto;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MemberNicknameHistoryDto(
	Long id,
	Long memberId,
	String nickname,
	LocalDateTime createdAt
) {
	public static MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
		return MemberNicknameHistoryDto.builder()
			.id(history.getId())
			.memberId(history.getMemberId())
			.nickname(history.getNickname())
			.build();
	}
}
