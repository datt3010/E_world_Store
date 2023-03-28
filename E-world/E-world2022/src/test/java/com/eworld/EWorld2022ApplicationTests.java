package com.eworld;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EWorld2022ApplicationTests {
	Caculator underTest = new Caculator();
	@Test
	void itShouldAddNumbers() {
		int num1=20;
		int num2=30;
		int result=underTest.sum(num1,num2);
		assertThat(result).isEqualTo(50);
	}

}
	class Caculator{
		public int sum(int a , int b){
			return a+b;
		}
	}
