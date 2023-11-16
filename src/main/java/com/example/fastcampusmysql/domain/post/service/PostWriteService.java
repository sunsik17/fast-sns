package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteService {
	private final PostRepository postRepository;

	public Long create(PostCommand command) {

		return postRepository.save(
			Post.builder()
				.memberId(command.memberId())
				.contents(command.contents())
				.build()).getId();
	}
}
