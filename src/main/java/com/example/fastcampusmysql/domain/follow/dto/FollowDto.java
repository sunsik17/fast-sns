package com.example.fastcampusmysql.domain.follow.dto;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.Builder;

@Builder
public record FollowDto(
	Long id,
	Long fromMemberId,
	Long toMemberId
) {
	public static FollowDto toDto(Follow follow) {
		return FollowDto.builder()
			.id(follow.getId())
			.fromMemberId(follow.getFromMemberId())
			.toMemberId(follow.getToMemberId())
			.build();
	}
}
