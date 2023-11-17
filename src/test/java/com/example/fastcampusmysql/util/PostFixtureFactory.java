package com.example.fastcampusmysql.util;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import com.example.fastcampusmysql.domain.post.entity.Post;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class PostFixtureFactory {

	public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {
		Predicate<Field> idPredicate = named("id")
			.and(ofType(Long.class))
			.and(inClass(Post.class));

		Predicate<Field> memberPredicate = named("memberId")
			.and(ofType(Long.class))
			.and(inClass(Post.class));

		return new EasyRandom(new EasyRandomParameters()
			.excludeField(idPredicate)
			.dateRange(firstDate, lastDate)
			.randomize(memberPredicate, () -> memberId));
	}

}
