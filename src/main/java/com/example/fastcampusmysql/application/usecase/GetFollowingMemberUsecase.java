package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowingMemberUsecase {

	private final MemberReadService memberReadService;
	private final FollowReadService followReadService;
	public List<MemberDto> execute(Long memberId) {

		List<FollowDto> followings = followReadService.getFollowings(memberId);
		List<Long> followingMemberIds = followings.stream().map(FollowDto::toMemberId).toList();
		return memberReadService.getMembers(followingMemberIds);
	}

}
