package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.repository.PlanDetailRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.transfer.request.PlanDetailViewRequest;
import com.example.demo.transfer.request.PlanEditRequest;
import com.example.demo.transfer.request.PlanViewRequest;
import com.example.demo.transfer.response.PlanDetailViewResponse;
import com.example.demo.transfer.response.PlanViewResponse;
import com.example.demo.transfer.response.Result;
import com.example.demo.util.DateUtil;

@Service
public class PlanService extends BusinessService {

	private static Logger logger = LoggerFactory.getLogger(PlanService.class);
	@Autowired(required=true)
	PlanRepository planRepo;
	@Autowired(required=true)
	PlanDetailRepository detailRepo;

	@Transactional
	public Result<String> insertDetail(PlanEditRequest json) {

		int placeId = json.getPlace();
		Date date = DateUtil.parseDate(json.getDate());
		//place と date で存在を確認
		Plan plan = planRepo.findByPlaceDate(placeId, date);
		if ( plan == null ) {
			plan = new Plan();
			plan.setDate(date);
			plan.setPlacesId(json.getPlace());
			plan = planRepo.save(plan);
		}

		//詳細を追加
		PlanDetail detail = new PlanDetail();
		
		String start = json.getStart();
		String end = json.getEnd();

		detail.setPlans_id(plan.getId());
		detail.setStart(DateUtil.parse(json.getDate(),start));
		detail.setEnd(DateUtil.parse(json.getDate(),end));
		detail.setName(json.getName());

		detailRepo.save(detail);
		Result<String> result = new Result<>();
		result.setResult("詳細設定完了");
		return result;
	}

	public Result<PlanViewResponse> view(PlanViewRequest json) {
		
		logger.info("view() findAll()");
	
		planRepo.findById(1);
		
		logger.info("view() findByPlace()");
		
		int placeId = json.getPlaceId();
		List<Plan> planList = planRepo.findByPlace(placeId);
		
		Result<PlanViewResponse> result = new Result<>();
		PlanViewResponse res = new PlanViewResponse();
		res.setPlans(planList);
		result.setResult(res);
		return result;
	}

	public Result<PlanDetailViewResponse> viewDetail(PlanDetailViewRequest json) {
		int placeId = json.getPlaceId();
		Date date = DateUtil.parseDate(json.getDate());
		
		logger.info(String.valueOf(placeId));
		logger.info(date.toString());

		List<PlanDetail> details = detailRepo.findByPlaceDate(placeId, date);
		
		Result<PlanDetailViewResponse> result = new Result<>();
		PlanDetailViewResponse res = new PlanDetailViewResponse();
		res.setDetails(details);
		result.setResult(res);

		return result;
	}

}
