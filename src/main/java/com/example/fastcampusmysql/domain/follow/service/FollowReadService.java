package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowReadService {

	private final FollowRepository followRepository;

	public List<FollowDto> getFollowings(Long memberId) {
		return followRepository.findAllByFromMemberId(memberId).stream()
			.map(FollowDto::toDto).collect(Collectors.toList());
	}

	public List<FollowDto> getFollowers(Long memberId) {
		return followRepository.findAllByToMemberId(memberId).stream()
			.map(FollowDto::toDto).collect(Collectors.toList());
	}

}
