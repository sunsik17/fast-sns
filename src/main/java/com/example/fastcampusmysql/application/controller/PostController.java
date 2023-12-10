package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.GetTimelinePostUsecase;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostWriteService postWriteService;
	private final PostReadService postReadService;
	private final GetTimelinePostUsecase getTimelinePostUsecase;

	@PostMapping("")
	public Long create(PostCommand postCommand) {
		return postWriteService.create(postCommand);
	}

	@GetMapping("/daily-post-counts")
	public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request) {
		return postReadService.getDailyPostCount(request);
	}

	@GetMapping("/members/{memberId}")
	public Page<Post> getPosts(
		@PathVariable Long memberId,
		Pageable pageable) {
		return postReadService.getPosts(memberId, pageable);
	}

	@GetMapping("/members/{memberId}/by-cursor")
	public PageCursor<Post> getPosts(
		@PathVariable Long memberId,
		CursorRequest cursorRequest) {
		return postReadService.getPosts(memberId, cursorRequest);
	}

	@GetMapping("/members/{memberId}/timeline")
	public PageCursor<Post> getTimeline(
		@PathVariable Long memberId,
		CursorRequest cursorRequest) {
		return getTimelinePostUsecase.execute(memberId, cursorRequest);
	}
}
