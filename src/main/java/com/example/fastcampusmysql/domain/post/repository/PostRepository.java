package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.util.PageHelper;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepository {

	static final String TABLE = "Post";
	private static final RowMapper<Post> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Post.builder()
		.id(resultSet.getLong("id"))
		.memberId(resultSet.getLong("memberId"))
		.contents(resultSet.getString("contents"))
		.createdDate(resultSet.getObject("createdDate", LocalDate.class))
		.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
		.build();

	private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER =
		(ResultSet resultSet, int rowNum) -> new DailyPostCount(
			resultSet.getLong("memberId"),
			resultSet.getObject("createdDate", LocalDate.class),
			resultSet.getLong("count"));

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public List<Post> findAllByMemberIdAndOrderByIdDesc(Long memberId, int size) {
		String sql = String.format("""
			SELECT *
			FROM %s
			WHERE memberId = :memberId
			ORDER BY id desc
			LIMIT :size
			""", TABLE);

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberId", memberId)
			.addValue("size", size);

		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	public List<Post> findAllByInMemberIdsAndOrderByIdDesc(List<Long> memberIds, int size) {
		if (memberIds.isEmpty()) {
			return List.of();
		}

		String sql = String.format("""
			SELECT *
			FROM %s
			WHERE memberId in (:memberIds)
			ORDER BY id desc
			LIMIT :size
			""", TABLE);

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberIds", memberIds)
			.addValue("size", size);

		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	public List<Post> findAllLessThanIdAndByMemberIdAndOrderByIdDesc(Long id, Long memberId,
		int size) {
		String sql = String.format("""
			SELECT *
			FROM %s
			WHERE memberId = :memberId and id < :id
			ORDER BY id desc
			LIMIT :size
			""", TABLE);

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberId", memberId)
			.addValue("id", id)
			.addValue("size", size);

		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	public List<Post> findAllLessThanIdAndInMemberIdsAndOrderByIdDesc(
		Long id, List<Long> memberIds, int size) {

		if (memberIds.isEmpty()) {
			return List.of();
		}

		String sql = String.format("""
			SELECT *
			FROM %s
			WHERE memberId in (:memberIds) and id < :id
			ORDER BY id desc
			LIMIT :size
			""", TABLE);

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberIds", memberIds)
			.addValue("id", id)
			.addValue("size", size);

		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	public Post save(Post post) {
		if (post.getId() == null) {
			return insert(post);
		}
		throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
	}

	private Post insert(Post post) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
			namedParameterJdbcTemplate.getJdbcTemplate())
			.withTableName(TABLE)
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(post);
		long id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Post.builder()
			.id(id)
			.memberId(post.getMemberId())
			.contents(post.getContents())
			.createdDate(post.getCreatedDate())
			.createdAt(post.getCreatedAt())
			.build();
	}

	public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
		String sql = String.format("""
			SELECT createdDate, memberId, count(id) as count
			FROM %s
			WHERE memberId = :memberId and createdDate between :firstDate and :lastDate
			GROUP BY memberId, createdDate
			""", TABLE);

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(request);
		return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROW_MAPPER);
	}

	public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberId", memberId)
			.addValue("size", pageable.getPageSize())
			.addValue("offset", pageable.getOffset());

		String sql = String.format("""
			SELECT *
			FROM %s
			WHERE memberId = :memberId
			ORDER BY %s
			LIMIT :size
			OFFSET :offset
			""", TABLE, PageHelper.orderBy(pageable.getSort()));

		List<Post> posts = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);

		return new PageImpl<>(posts, pageable, getCount(memberId));
	}

	private Long getCount(Long memberId) {
		String sql = String.format("""
			SELECT count(id)
			FROM %s
			WHERE memberId = :memberId
			""", TABLE);

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberId", memberId);

		return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
	}

	public void bulkInsert(List<Post> posts) {
		String sql = String.format("""
						INSERT INTO `%s` (memberId, contents, createdDate, createdAt)
						VALUES (:memberId, :contents, :createdDate, :createdAt)
			""", TABLE);

		SqlParameterSource[] params = posts
			.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(SqlParameterSource[]::new);

		namedParameterJdbcTemplate.batchUpdate(sql, params);
	}
}
