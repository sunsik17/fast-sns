package com.example.fastcampusmysql.domain.member.dto;

import com.example.fastcampusmysql.domain.member.entity.Member;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record MemberDto(
	Long id,
	String email,
	String nickName,
	LocalDate birthday
) {
	public static MemberDto toDto(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.email(member.getEmail())
			.nickName(member.getNickName())
			.birthday(member.getBirthDay())
			.build();
	}
}
