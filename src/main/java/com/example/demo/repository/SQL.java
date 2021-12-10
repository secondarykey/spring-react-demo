package com.example.demo.repository;

public class SQL {
	
	public static String JOINSAMPLE = """
SELECT DISTINCT * FROM PLAN_DETAILS
  INNER JOIN 
    (SELECT * FROM PLANS WHERE PLANS.PLACES_ID = :placeId AND PLANS.DATE = :date)
  PLANS ON PLANS.id = PLAN_DETAILS.plans_id
""";
}
