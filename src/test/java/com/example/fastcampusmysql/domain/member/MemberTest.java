package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {
	@Test
	@DisplayName("회원은 닉네임을 변경할 수 있다.")
	void testChangeNickname () {
		//given
		var member = MemberFixtureFactory.create();
		var expected = "pnu";
		//when
		member.changeNickname(expected);
	    //then
		Assertions.assertEquals(expected, member.getNickName());
	}
	@Test
	@DisplayName("닉네임은 10글자를 넘길 수 없다.")
	void testNicknameMaxLength() {
		//given
		var member = MemberFixtureFactory.create();
		var overMaxLengthName = "pnupnupnupnu";
		//when
		Assertions.assertThrows(RuntimeException.class, () ->
			member.changeNickname(overMaxLengthName)
		);

		//then
	}
}
