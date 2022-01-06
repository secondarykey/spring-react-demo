package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DemoApplication;
import com.example.demo.config.AuthenticationInterceptor;
import com.example.demo.transfer.response.Result;

@SpringBootTest(classes = DemoApplication.class)

public class AuthenticationInterceptorTest {

    private MockMvc mockMvc;

	@BeforeEach
    public void setup() {
        // MockMvcオブジェクトにテスト対象メソッドを設定
        mockMvc = MockMvcBuilders.standaloneSetup(new MockController()).
        		addInterceptors(new AuthenticationInterceptor()).build();
    }

	@Test
	void testAuthURL() {
		try {
			ResultActions rtn = mockMvc.perform(get("/"));
			rtn.andExpect(status().is(200));

			//TODO 以下、他のURLでMockControllerが動作しない
			//TODO 認証が動作して拒否されること
			rtn = mockMvc.perform(get("/api/v1/todos/view"));
			rtn.andExpect(status().is(401));

			//TODO 認証が動作しないことを確認
			rtn = mockMvc.perform(get("/api/v1/login"));
			rtn.andExpect(status().is(200));

			//TODO 正規のヘッダを付与して確認
			rtn = mockMvc.perform(get("/api/v1/todos/view"));
			rtn.andExpect(status().is(200));

		} catch (Exception e) {
			fail("発生しないはず",e);
		}
	}

	@RestController
	@RequestMapping("/*")
	public static class MockController {
		@RequestMapping("")
		public Result<String> allAccess() {
			Result<String> result = new Result<>();
			result.setResult("access");
			return result;
		}
	}
}
