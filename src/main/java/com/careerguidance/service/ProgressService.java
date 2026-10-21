package com.careerguidance.service;

import com.careerguidance.dto.CareerRecommendationDto;
import com.careerguidance.dto.RoadmapDetailDto;
import com.careerguidance.entity.*;
import com.careerguidance.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProgressService {

    private final StudentProfileRepository profileRepository;
    private final StudentRoadmapProgressRepository roadmapProgressRepository;
    private final RoadmapService roadmapService;
    private final CareerRecommendationService recommendationService;
    private final SavedResourceRepository savedResourceRepository;
    private final MentorConnectionRequestRepository mentorRequestRepository;
    private final JobPrepProgressRepository jobPrepProgressRepository;

    public ProgressService(StudentProfileRepository profileRepository,
                           StudentRoadmapProgressRepository roadmapProgressRepository,
                           RoadmapService roadmapService,
                           CareerRecommendationService recommendationService,
                           SavedResourceRepository savedResourceRepository,
                           MentorConnectionRequestRepository mentorRequestRepository,
                           JobPrepProgressRepository jobPrepProgressRepository) {
        this.profileRepository = profileRepository;
        this.roadmapProgressRepository = roadmapProgressRepository;
        this.roadmapService = roadmapService;
        this.recommendationService = recommendationService;
        this.savedResourceRepository = savedResourceRepository;
        this.mentorRequestRepository = mentorRequestRepository;
        this.jobPrepProgressRepository = jobPrepProgressRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStudentProgressOverview(User user) {
        Map<String, Object> data = new HashMap<>();

        // 1. Profile Completion
        Optional<StudentProfile> profileOpt = profileRepository.findByUser(user);
        int profileCompletion = profileOpt.map(StudentProfile::getProfileCompletionPercentage).orElse(0);
        data.put("profileCompletion", profileCompletion);

        String shortTermGoal = profileOpt.map(StudentProfile::getShortTermGoal).orElse("Not specified yet");
        String longTermGoal = profileOpt.map(StudentProfile::getLongTermGoal).orElse("Not specified yet");
        data.put("shortTermGoal", shortTermGoal);
        data.put("longTermGoal", longTermGoal);

        // 2. Active Roadmaps and Completed Steps
        long totalCompletedRoadmapSteps = roadmapProgressRepository.countTotalCompletedStepsByUser(user.getId());
        List<RoadmapDetailDto> activeRoadmaps = roadmapService.getActiveRoadmapsForUser(user);
        data.put("totalCompletedRoadmapSteps", totalCompletedRoadmapSteps);
        data.put("activeRoadmaps", activeRoadmaps);

        // 3. Recommended Careers (Top 3)
        List<CareerRecommendationDto> recommendations = recommendationService.getRecommendationsForStudent(user);
        List<CareerRecommendationDto> topRecommendations = recommendations.stream().limit(3).toList();
        data.put("topRecommendations", topRecommendations);

        // Missing Skills (Skills to improve from top recommendations)
        Set<String> skillsToImprove = new LinkedHashSet<>();
        for (CareerRecommendationDto rec : topRecommendations) {
            skillsToImprove.addAll(rec.getMissingSkills());
        }
        data.put("skillsToImprove", skillsToImprove.stream().limit(6).toList());

        // 4. Saved Resources count
        long savedResourcesCount = savedResourceRepository.countByUserId(user.getId());
        data.put("savedResourcesCount", savedResourcesCount);

        // 5. Mentor Connection Status
        List<MentorConnectionRequest> requests = mentorRequestRepository.findByStudentOrderByCreatedAtDesc(user);
        long activeConnectionsCount = requests.stream().filter(r -> "ACCEPTED".equalsIgnoreCase(r.getStatus())).count();
        long pendingConnectionsCount = requests.stream().filter(r -> "PENDING".equalsIgnoreCase(r.getStatus())).count();
        data.put("activeConnectionsCount", activeConnectionsCount);
        data.put("pendingConnectionsCount", pendingConnectionsCount);

        // 6. Job Prep Progress count
        long completedJobPrepItems = jobPrepProgressRepository.countByUserIdAndIsCompletedTrue(user.getId());
        data.put("completedJobPrepItems", completedJobPrepItems);

        // 7. Chart.js Data Payload
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", List.of("Profile", "Roadmaps", "Job Prep", "Mentorship", "Resources"));
        int roadmapScore = activeRoadmaps.isEmpty() ? 0 : activeRoadmaps.get(0).getProgressPercentage();
        int jobPrepScore = (int) Math.min(100, completedJobPrepItems * 20);
        int mentorScore = activeConnectionsCount > 0 ? 100 : (pendingConnectionsCount > 0 ? 50 : 10);
        int resourceScore = (int) Math.min(100, savedResourcesCount * 25);
        chartData.put("scores", List.of(profileCompletion, roadmapScore, jobPrepScore, mentorScore, resourceScore));
        data.put("chartData", chartData);

        return data;
    }
}
