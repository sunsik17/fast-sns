package com.example.fastcampusmysql.util;

/**
 * cursor 방식 페이지네이션에서 주의 해야할 것
 * key 는 절대 중복가능성이 없어야 한다 (유니크함이 보장되어야 함 데이터를 읽다가 끊기는 이슈가 있을 수 있음)
 */
public record CursorRequest(Long key, int size) {
	public static final Long NONE_KEY = -1L;

	public boolean hasKey() {
		return key != null;
	}
	public CursorRequest next(Long key) {
		return new CursorRequest(key, size);
	}
}
