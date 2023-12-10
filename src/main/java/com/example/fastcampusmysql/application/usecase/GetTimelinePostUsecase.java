package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {

	private final FollowReadService followReadService;
	private final PostReadService postReadService;
	public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
		/*
		1. memberId -> follow 조회
		2. 1번 결과로 게시물들 조회
		 */
		List<FollowDto> followings = followReadService.getFollowings(memberId);
		List<Long> followingMemberIds = followings.stream().map(FollowDto::toMemberId).toList();
		return postReadService.getPosts(followingMemberIds, cursorRequest);
	}

}
