package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;
	public MemberDto register(RegisterMemberCommand command) {
		/*
			목표 - 회원정보 저장 (이메일, 닉네임, 생년월일)
				- 닉네임은 10자를 남길 수 없다.
			파라미터 - memberRegisterCommand
			val member = Member.of(memberRegisterCommand)
			memberRepository.save(member)
		 */

		Member member = Member.builder()
			.nickname(command.nickName())
			.birthday(command.birthday())
			.email(command.email())
			.build();

		Member saveMember = memberRepository.save(member);
		saveMemberNicknameHistory(saveMember);
		return MemberDto.toDto(saveMember);
	}

	public void changeNickname(Long memberId, String nickName) {
		/*
			1. 회원의 이름을 변경
			2. 변경 내역을 저장한다.
		 */
		Member member = memberRepository.findById(memberId).orElseThrow();
		member.changeNickname(nickName);
		memberRepository.save(member);

		saveMemberNicknameHistory(member);
	}

	private void saveMemberNicknameHistory(Member member) {
		MemberNicknameHistory history = MemberNicknameHistory.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.build();
		memberNicknameHistoryRepository.save(history);
	}

}
