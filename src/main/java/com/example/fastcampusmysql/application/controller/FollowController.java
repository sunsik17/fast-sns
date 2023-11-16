package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMemberUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

	private final CreateFollowUsecase createFollowUsecase;
	private final GetFollowingMemberUsecase getFollowingMemberUsecase;

	@PostMapping("/{fromMemberId}/{toMemberId}")
	public void create(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
		createFollowUsecase.execute(fromMemberId, toMemberId);
	}

	@GetMapping("/members/{fromId}")
	public List<MemberDto> create(@PathVariable Long fromId) {
		return getFollowingMemberUsecase.execute(fromId);
	}

}
