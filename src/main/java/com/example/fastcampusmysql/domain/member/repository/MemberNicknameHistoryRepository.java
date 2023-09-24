package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
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
public class MemberNicknameHistoryRepository {
	private static final String TABLE_NAME = "MemberNicknameHistory";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	static final RowMapper<MemberNicknameHistory> rowMapper =
		(ResultSet resultSet, int rowNum) -> MemberNicknameHistory.builder()
		.id(resultSet.getLong("id"))
		.memberId(resultSet.getLong("memberId"))
		.nickname(resultSet.getString("nickName"))
		.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
		.build();
	public MemberNicknameHistory save(MemberNicknameHistory history) {
		if (history.getId() == null) {
			return insert(history);
		}
		throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다.");
	}

	public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
		String sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE_NAME);
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberId", memberId);
		return namedParameterJdbcTemplate.query(sql, params, rowMapper);
	}
	private MemberNicknameHistory insert(MemberNicknameHistory history) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
			.withTableName(TABLE_NAME)
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(history);
		Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

		return MemberNicknameHistory.builder()
			.id(id)
			.memberId(history.getMemberId())
			.nickname(history.getNickname())
			.createdAt(history.getCreatedAt())
			.build();
	}
}
