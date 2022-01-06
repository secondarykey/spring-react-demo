package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organization;
import com.example.demo.repository.OrganizationQueryRepository;
import com.example.demo.transfer.response.OrganizationTree;
import com.example.demo.util.DateUtil;
import com.example.demo.util.Util;

@Service
public class OrganizationService  extends BusinessService {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(OrganizationService.class);

	@Autowired(required=true)
	OrganizationQueryRepository query;

	/**
	 * 指定開始日の組織ツリーを生成
	 * @param belong 組織ID
	 * @param start 開始日
	 * @return
	 */
	public OrganizationTree createTree(int belong,String startBuf) {
		
		Date start = DateUtil.parseDate(startBuf);
		
		List<Organization> list = query.find(start);
		if ( Util.isEmpty(list) ) {
			logger.warn("開始日{}に対する組織が存在しない",start);
			return null;
		}
	
		if ( logger.isDebugEnabled() ) {
			logger.debug("組織({})内の開始日({})の全件数:{}",belong,start,list.size());
		}

		Organization root = null;
		Map<Integer,List<Organization>> map = new HashMap<>();
		for ( Organization org : list ) {
			
			int parent = org.getParent();
			if ( org.getOrgID() == belong ) {
				root = org;
			}

			List<Organization> orgs =  map.get(parent);
			if ( orgs == null ) {
				orgs = new ArrayList<Organization>();
			}
			orgs.add(org);
			map.put(parent,orgs);
		}

		if ( root == null ) {
			logger.warn("対象の組織({})が開始日({})内に存在しない",belong,start);
			return null;
		}

		OrganizationTree treeRoot = new OrganizationTree();
		treeRoot.setKey(root.getOrgID());
		treeRoot.setTitle(root.getName());

		setTree(treeRoot,map);

		return treeRoot;
	}

	/**
	 * 組織ツリーの設定（再帰）
	 * @param parent 親オブジェクト
	 * @param map データマップ
	 */
	private void setTree(OrganizationTree parent, Map<Integer, List<Organization>> map) {
		List<Organization> children = map.get(parent.getKey());
		if ( Util.isEmpty(children) ) {
			return;
		}
		List<OrganizationTree> list = new ArrayList<OrganizationTree>();
		for ( Organization org : children ) {
			OrganizationTree obj = new OrganizationTree();
			obj.setKey(org.getOrgID());
			obj.setTitle(org.getName());
			setTree(obj,map);
			list.add(obj);
		}
		parent.setChildren(list);
	}
}
