package com.rewards.backend.app.referral.referral;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.ResponseHandler;
import com.rewards.backend.exception.CustomException;

@RestController
@RequestMapping("/rewards")
@CrossOrigin("*")
public class RewardController {

    @Autowired
    private RewardPlanRepository rewardPlanRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> createRewardPlan(@RequestBody RewardPlan rewardPlan) {
    	try {
            RewardPlan createdRewardPlan = rewardPlanRepository.save(rewardPlan);
            return ResponseHandler.generateResponse(createdRewardPlan, HttpStatus.OK, "CREATED");
    	} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to load data");
		}    
    }
    
    @GetMapping(value = "get/planName")
    public ResponseEntity<?> getAllPlanNames() {
        try {
            List<String> planNames = rewardPlanRepository.findAllPlanNames();
            return ResponseHandler.generateResponse(planNames, HttpStatus.OK, "Successfully retrieved all plan names");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to retrieve plan names");
        }
    }



    @GetMapping("/all")
    public ResponseEntity<Object> getAllRewardPlans() {
    	try {
            List<RewardPlan> rewardPlans = rewardPlanRepository.findAll();
            return ResponseHandler.generateResponse(rewardPlans, HttpStatus.OK, "Api called successfully");
    	} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to load data");
		}
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateRewardPlan(@PathVariable("id") long id, @RequestBody RewardPlan rewardPlanDetails) {
    	try {

            RewardPlan rewardPlan = rewardPlanRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Reward Plan not found with id: " + id));

            rewardPlan.setRewardName(rewardPlanDetails.getRewardName());
            rewardPlan.setRewardType(rewardPlanDetails.getRewardType());
            rewardPlan.setRewardCode(rewardPlanDetails.getRewardCode());
            rewardPlan.setRewardCouponCode(rewardPlanDetails.getRewardCouponCode());
            rewardPlan.setRewardValue(rewardPlanDetails.getRewardValue());
            rewardPlan.setRewardPoints(rewardPlanDetails.getRewardPoints());
            rewardPlan.setExpirationDate(rewardPlanDetails.getExpirationDate());
            rewardPlan.setHasExpiration(rewardPlanDetails.isHasExpiration());

            RewardPlan updatedRewardPlan = rewardPlanRepository.save(rewardPlan);
            return ResponseHandler.generateResponse(updatedRewardPlan, HttpStatus.OK, "Ok");
        
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to load data");
		}
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRewardPlan(@PathVariable("id") long id) {
        try {
            Optional<RewardPlan> rewardPlanOptional = rewardPlanRepository.findById(id);
            if (rewardPlanOptional.isPresent()) {
                rewardPlanRepository.deleteById(id);
                return ResponseHandler.generateResponse("Successfully deleted", HttpStatus.OK, "OK");
            } else {
                return ResponseHandler.generateResponse("Content not found", HttpStatus.NOT_FOUND, "Content not found");
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to delete reward plan");
        }
    }


    @PostMapping("/addMultiple")
    public ResponseEntity<Object> addMultipleRewards(@RequestBody List<RewardPlan> rewardPlans) {
    	try {
            List<RewardPlan> createdRewardPlans = rewardPlanRepository.saveAll(rewardPlans);
            return ResponseHandler.generateResponse(createdRewardPlans, HttpStatus.OK, "Created All");
    	} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to load data");
		}
    }
}