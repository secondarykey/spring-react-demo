package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.PlanDetails;
import com.example.demo.model.Plans;
import com.example.demo.repository.PlanDetailQueryRepository;
import com.example.demo.repository.PlanDetailRepository;
import com.example.demo.repository.PlanQueryRepository;
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

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PlanService.class);

	@Autowired(required=true)
	PlanRepository planRepo;
	@Autowired(required=true)
	PlanQueryRepository queryRepo;
	@Autowired(required=true)
	PlanDetailQueryRepository detailQueryRepo;
	@Autowired(required=true)
	PlanDetailRepository detailRepo;

	@Transactional
	public Result<String> insertDetail(PlanEditRequest json) {

		int placeId = json.getPlace();
		Date date = DateUtil.parseDate(json.getDate());
		//place と date で存在を確認
		Plans plan = queryRepo.findByPlaceDate(placeId, date);
		if ( plan == null ) {
			plan = new Plans();
			plan.setDate(date);
			plan.setPlacesId(json.getPlace());
			plan = planRepo.save(plan);
		}

		//詳細を追加
		PlanDetails detail = new PlanDetails();
		
		String start = json.getStart();
		String end = json.getEnd();

		detail.setPlansId(plan.getId());
		detail.setStart(DateUtil.parse(json.getDate(),start));
		detail.setEnd(DateUtil.parse(json.getDate(),end));
		detail.setName(json.getName());

		detailRepo.save(detail);
		Result<String> result = new Result<>();
		result.setResult("詳細設定完了");
		return result;
	}

	public Result<PlanViewResponse> view(PlanViewRequest json) {
		
		int placeId = json.getPlaceId();
		List<Plans> planList = queryRepo.findByPlace(placeId);

		Result<PlanViewResponse> result = new Result<>();
		PlanViewResponse res = new PlanViewResponse();
		res.setPlans(planList);
		result.setResult(res);
		return result;
	}

	public Result<PlanDetailViewResponse> viewDetail(PlanDetailViewRequest json) {
		int placeId = json.getPlaceId();
		Date date = DateUtil.parseDate(json.getDate());
		
		List<PlanDetails> details = detailQueryRepo.findByPlaceDate(placeId, date);

		Result<PlanDetailViewResponse> result = new Result<>();
		PlanDetailViewResponse res = new PlanDetailViewResponse();
		res.setDetails(details);
		result.setResult(res);

		return result;
	}

}
