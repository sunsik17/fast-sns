package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.timeline.service.TimelineWriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {
	private final PostWriteService postWriteService;
	private final FollowReadService followReadService;
	private final TimelineWriteService timelineWriteService;

	public Long execute(PostCommand postCommand) {
		Long postId = postWriteService.create(postCommand);
		List<Long> followerIds = followReadService.getFollowers(postCommand.memberId())
			.stream()
			.map(FollowDto::fromMemberId)
			.toList();
		timelineWriteService.deliveryToTimeline(postId, followerIds);

		return postId;
	}
}
