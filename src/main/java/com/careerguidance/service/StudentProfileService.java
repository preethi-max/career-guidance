package com.careerguidance.service;

import com.careerguidance.dto.StudentProfileDto;
import com.careerguidance.entity.StudentProfile;
import com.careerguidance.entity.User;
import com.careerguidance.repository.StudentProfileRepository;
import com.careerguidance.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentProfileService {

    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;

    public StudentProfileService(StudentProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public StudentProfileDto getProfile(User user) {
        StudentProfile profile = profileRepository.findByUser(user)
                .orElseGet(() -> profileRepository.save(new StudentProfile(user)));
        return StudentProfileDto.fromEntity(profile);
    }

    @Transactional
    public StudentProfileDto updateProfile(User user, StudentProfileDto dto) {
        StudentProfile profile = profileRepository.findByUser(user)
                .orElseGet(() -> new StudentProfile(user));

        // Update User fields if provided
        if (dto.getFullName() != null && !dto.getFullName().isBlank()) {
            user.setFullName(dto.getFullName().trim());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone().trim());
        }
        userRepository.save(user);

        // Update StudentProfile fields
        if (dto.getEducationLevel() != null) profile.setEducationLevel(dto.getEducationLevel());
        if (dto.getDegree() != null) profile.setDegree(dto.getDegree());
        if (dto.getBranch() != null) profile.setBranch(dto.getBranch());
        if (dto.getCollegeName() != null) profile.setCollegeName(dto.getCollegeName());
        if (dto.getYearOfStudy() != null) profile.setYearOfStudy(dto.getYearOfStudy());
        if (dto.getMarksOrCgpa() != null) profile.setMarksOrCgpa(dto.getMarksOrCgpa());
        if (dto.getTechnicalSkills() != null) profile.setTechnicalSkills(dto.getTechnicalSkills());
        if (dto.getNonTechnicalSkills() != null) profile.setNonTechnicalSkills(dto.getNonTechnicalSkills());
        if (dto.getInterests() != null) profile.setInterests(dto.getInterests());
        if (dto.getStrengths() != null) profile.setStrengths(dto.getStrengths());
        if (dto.getPreferredFields() != null) profile.setPreferredFields(dto.getPreferredFields());
        if (dto.getShortTermGoal() != null) profile.setShortTermGoal(dto.getShortTermGoal());
        if (dto.getLongTermGoal() != null) profile.setLongTermGoal(dto.getLongTermGoal());

        // Calculate completion percentage
        int percentage = calculateCompletion(profile, user);
        profile.setProfileCompletionPercentage(percentage);

        StudentProfile saved = profileRepository.save(profile);
        return StudentProfileDto.fromEntity(saved);
    }

    private int calculateCompletion(StudentProfile p, User u) {
        int totalFields = 13;
        int completed = 0;

        if (u.getFullName() != null && !u.getFullName().isBlank()) completed++;
        if (p.getEducationLevel() != null && !p.getEducationLevel().isBlank()) completed++;
        if (p.getDegree() != null && !p.getDegree().isBlank()) completed++;
        if (p.getBranch() != null && !p.getBranch().isBlank()) completed++;
        if (p.getCollegeName() != null && !p.getCollegeName().isBlank()) completed++;
        if (p.getYearOfStudy() != null && !p.getYearOfStudy().isBlank()) completed++;
        if (p.getMarksOrCgpa() != null && !p.getMarksOrCgpa().isBlank()) completed++;
        if (p.getTechnicalSkills() != null && !p.getTechnicalSkills().isBlank()) completed++;
        if (p.getNonTechnicalSkills() != null && !p.getNonTechnicalSkills().isBlank()) completed++;
        if (p.getInterests() != null && !p.getInterests().isBlank()) completed++;
        if (p.getStrengths() != null && !p.getStrengths().isBlank()) completed++;
        if (p.getPreferredFields() != null && !p.getPreferredFields().isBlank()) completed++;
        if (p.getShortTermGoal() != null && !p.getShortTermGoal().isBlank()) completed++;

        return Math.min(100, (int) Math.round(((double) completed / totalFields) * 100));
    }
}
