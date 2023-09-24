package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	private static final String MEMBER_TABLE_NAME = "Member";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
		.id(resultSet.getLong("id"))
		.email(resultSet.getString("email"))
		.nickname(resultSet.getString("nickName"))
		.birthday(resultSet.getObject("birthday", LocalDate.class))
		.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
		.build();

	public Member save(Member member) {
		/*
			member의 id를 보고 갱신 또는 삽입 연산
			반환값은 id를 담아서 반환
		 */

		if (member.getId() == null) {
			return insert(member);
		}
		return update(member);
	}

	public Optional<Member> findById(Long id) {
		/*
			select *
			from Member
			where id = : id
		 */

		String sql = String.format("SELECT * FROM %s WHERE id = :id", MEMBER_TABLE_NAME);
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", id);

		Member member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
		return Optional.ofNullable(member);
	}

	private Member insert(Member member) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
			.withTableName(MEMBER_TABLE_NAME)
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

		return Member.builder()
			.id(id)
			.email(member.getEmail())
			.nickname(member.getNickname())
			.birthday(member.getBirthday())
			.createdAt(member.getCreatedAt())
			.build();
	}

	private Member update(Member member) {
		String sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", MEMBER_TABLE_NAME);
		SqlParameterSource params = new BeanPropertySqlParameterSource(member);

		namedParameterJdbcTemplate.update(sql, params);
		return member;
	}
}
