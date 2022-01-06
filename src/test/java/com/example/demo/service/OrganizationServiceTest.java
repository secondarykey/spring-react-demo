package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.transfer.response.OrganizationTree;

@SpringBootTest(classes = DemoApplication.class)
public class OrganizationServiceTest {

	@Autowired
	OrganizationService service;

	@Test
	void testCreateTree() {

		OrganizationTree tree = service.createTree(1, "2022-01-01");
		assertNotNull(tree,"検索できること");
		assertNull(tree.getChildren(),"適用開始前なので子がない");
		
		tree = service.createTree(1, "2022-01-06");
		assertNotNull(tree,"適用開始日を変更して検索できること");

		List<OrganizationTree> children = tree.getChildren();
		assertNotNull(children,"適用で子が設定される");
		assertEquals(children.size(),2,"件数が２件であること");
		
		tree = children.get(0);
		assertEquals(tree.getKey(),2,"組織IDが2であること");

		children = tree.getChildren();
		assertNotNull(children,"孫が設定される");
		assertEquals(children.size(),3,"件数が３件であること");
	
		//子で検索
		tree = service.createTree(2, "2022-01-06");
		assertNotNull(tree,"子で検索してもツリーが設定されること");
		assertEquals(tree.getKey(),2,"組織IDが2であること");
		children = tree.getChildren();
		assertEquals(children.size(),3,"孫組織が取れていること");

		//別ツリーの作成
		tree = service.createTree(7, "2022-01-06");
		assertNotNull(tree,"違う組織のツリーが作成されること");
		children = tree.getChildren();
		assertNotNull(children,"子が設定されていること");
		assertEquals(children.size(),1,"件数が１件であること");
		tree = children.get(0);
		assertEquals(tree.getKey(),8,"このIDが正確なこと");

		children = tree.getChildren();
		assertNull(children,"孫がいないこと");
		
		tree = service.createTree(10,"2022-01-06");
		assertNull(tree,"存在しない場合Nullであること");
	}
}
