package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

	private final CreateFollowUsecase createFollowUsecase;

	@PostMapping("/{fromMemberId}/{toMemberId}")
	public void register(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
		createFollowUsecase.execute(fromMemberId, toMemberId);
	}

}
