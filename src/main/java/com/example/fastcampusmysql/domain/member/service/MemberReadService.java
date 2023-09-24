package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

	public MemberDto getMember(Long id) {
		Member member = memberRepository.findById(id).orElseThrow();
		return MemberDto.toDto(member);
	}

	public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
		return memberNicknameHistoryRepository.findAllByMemberId(memberId)
			.stream().map(MemberNicknameHistoryDto::toDto).collect(Collectors.toList());
	}
}
