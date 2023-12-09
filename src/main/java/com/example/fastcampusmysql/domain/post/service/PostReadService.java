package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService {

	private final PostRepository postRepository;

	public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
		/*
			반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 갯수]
			select createdDate, memberId, count(id)
			from Post
			where memberId = :memberId and createdDate between firstDate and lastDate
			group by createdDate memberId
		 */
		return postRepository.groupByCreatedDate(request);
	}

	public Page<Post> getPosts(Long memberId, Pageable pageable) {
		return postRepository.findAllByMemberId(memberId, pageable);
	}

	public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
		List<Post> posts = findAllBy(memberId, cursorRequest);
		Long nextKey = posts.stream()
			.mapToLong(Post::getId)
			.min().orElse(CursorRequest.NONE_KEY);

		return new PageCursor<>(cursorRequest.next(nextKey), posts);
	}

	private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
		if (cursorRequest.hasKey()) {
			return postRepository.findAllLessThanIdAndByMemberIdAndOrderByIdDesc(
				cursorRequest.key(),
				memberId,
				cursorRequest.size());
		}

		return postRepository.findAllByMemberIdAndOrderByIdDesc(
			memberId,
			cursorRequest.size());

	}
}
