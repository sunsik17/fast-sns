package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class PostBulkInsertTest {
	@Autowired
	private PostRepository postRepository;

	@Test
	void bulkInsert() {
	    //given
		EasyRandom easyRandom = PostFixtureFactory.get(3L,
			LocalDate.of(1970, 1, 1),
			LocalDate.of(2023, 2, 1)
		);
		//when

		int tenThousand = 10000;

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<Post> posts = IntStream.range(0, tenThousand * 100)
			.mapToObj(i -> easyRandom.nextObject(Post.class))
			.toList();
		stopWatch.stop();
		System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

		//then
		StopWatch queryStopWatch = new StopWatch();
		queryStopWatch.start();
		postRepository.bulkInsert(posts);
		queryStopWatch.stop();
		System.out.println("데이터베이스 삽입 시간 : " + queryStopWatch.getTotalTimeSeconds());
	}
}
