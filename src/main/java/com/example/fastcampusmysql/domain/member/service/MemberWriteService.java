package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
	private final MemberRepository memberRepository;
	public Member create(RegisterMemberCommand command) {
		/*
			목표 - 회원정보 저장 (이메일, 닉네임, 생년월일)
				- 닉네임은 10자를 남길 수 없다.
			파라미터 - memberRegisterCommand
			val member = Member.of(memberRegisterCommand)
			memberRepository.save(member)
		 */

		Member member = Member.builder()
			.nickName(command.nickName())
			.birthDay(command.birthday())
			.email(command.email())
			.build();

		return memberRepository.save(member);
	}
}
