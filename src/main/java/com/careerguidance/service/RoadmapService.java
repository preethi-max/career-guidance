package com.careerguidance.service;

import com.careerguidance.dto.RoadmapDetailDto;
import com.careerguidance.entity.*;
import com.careerguidance.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoadmapService {

    private final CareerRoadmapRepository roadmapRepository;
    private final RoadmapStepRepository stepRepository;
    private final StudentRoadmapProgressRepository progressRepository;
    private final CareerRepository careerRepository;

    public RoadmapService(CareerRoadmapRepository roadmapRepository,
                          RoadmapStepRepository stepRepository,
                          StudentRoadmapProgressRepository progressRepository,
                          CareerRepository careerRepository) {
        this.roadmapRepository = roadmapRepository;
        this.stepRepository = stepRepository;
        this.progressRepository = progressRepository;
        this.careerRepository = careerRepository;
    }

    @Transactional(readOnly = true)
    public RoadmapDetailDto getRoadmapByCareerId(Long careerId, User currentUser) {
        CareerRoadmap roadmap = roadmapRepository.findByCareerId(careerId)
                .orElseThrow(() -> new IllegalArgumentException("No roadmap found for career ID: " + careerId));

        return buildRoadmapDetailDto(roadmap, currentUser);
    }

    @Transactional(readOnly = true)
    public RoadmapDetailDto getRoadmapById(Long roadmapId, User currentUser) {
        CareerRoadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new IllegalArgumentException("Roadmap not found with ID: " + roadmapId));

        return buildRoadmapDetailDto(roadmap, currentUser);
    }

    @Transactional
    public RoadmapDetailDto toggleStepCompletion(Long stepId, boolean completed, User currentUser) {
        RoadmapStep step = stepRepository.findById(stepId)
                .orElseThrow(() -> new IllegalArgumentException("Roadmap step not found with ID: " + stepId));

        CareerRoadmap roadmap = step.getRoadmap();

        StudentRoadmapProgress progress = progressRepository.findByUserAndStep(currentUser, step)
                .orElseGet(() -> new StudentRoadmapProgress(currentUser, roadmap, step, false));

        progress.setCompleted(completed);
        progress.setCompletedAt(completed ? LocalDateTime.now() : null);
        progressRepository.save(progress);

        return buildRoadmapDetailDto(roadmap, currentUser);
    }

    @Transactional(readOnly = true)
    public List<RoadmapDetailDto> getActiveRoadmapsForUser(User user) {
        List<CareerRoadmap> allRoadmaps = roadmapRepository.findAll();
        List<RoadmapDetailDto> result = new ArrayList<>();

        for (CareerRoadmap roadmap : allRoadmaps) {
            RoadmapDetailDto dto = buildRoadmapDetailDto(roadmap, user);
            if (dto.getCompletedSteps() > 0) {
                result.add(dto);
            }
        }
        return result;
    }

    private RoadmapDetailDto buildRoadmapDetailDto(CareerRoadmap roadmap, User user) {
        RoadmapDetailDto dto = new RoadmapDetailDto();
        dto.setRoadmapId(roadmap.getId());
        if (roadmap.getCareer() != null) {
            dto.setCareerId(roadmap.getCareer().getId());
            dto.setCareerTitle(roadmap.getCareer().getTitle());
        }
        dto.setTitle(roadmap.getTitle());
        dto.setDescription(roadmap.getDescription());
        dto.setEstimatedMonths(roadmap.getEstimatedMonths());

        List<RoadmapStep> steps = stepRepository.findByRoadmapOrderByStepNumberAsc(roadmap);
        dto.setTotalSteps(steps.size());

        Set<Long> completedStepIds = new HashSet<>();
        if (user != null) {
            List<StudentRoadmapProgress> userProgress = progressRepository.findByUserAndRoadmap(user, roadmap);
            for (StudentRoadmapProgress p : userProgress) {
                if (p.isCompleted()) {
                    completedStepIds.add(p.getStep().getId());
                }
            }
        }

        dto.setCompletedSteps(completedStepIds.size());
        int percentage = steps.isEmpty() ? 0 : (int) Math.round(((double) completedStepIds.size() / steps.size()) * 100);
        dto.setProgressPercentage(percentage);

        List<RoadmapDetailDto.RoadmapStepDto> stepDtos = steps.stream().map(s -> {
            RoadmapDetailDto.RoadmapStepDto stepDto = new RoadmapDetailDto.RoadmapStepDto();
            stepDto.setStepId(s.getId());
            stepDto.setStepNumber(s.getStepNumber());
            stepDto.setTitle(s.getTitle());
            stepDto.setDescription(s.getDescription());
            stepDto.setKeyTopics(s.getKeyTopics());
            stepDto.setLearningResourcesText(s.getLearningResourcesText());
            stepDto.setEstimatedWeeks(s.getEstimatedWeeks());
            stepDto.setCompleted(completedStepIds.contains(s.getId()));
            return stepDto;
        }).collect(Collectors.toList());

        dto.setSteps(stepDtos);
        return dto;
    }
}
