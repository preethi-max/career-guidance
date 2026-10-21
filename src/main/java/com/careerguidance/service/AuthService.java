package com.careerguidance.service;

import com.careerguidance.dto.LoginRequest;
import com.careerguidance.dto.RegisterRequest;
import com.careerguidance.dto.UserDto;
import com.careerguidance.entity.CareerGuideProfile;
import com.careerguidance.entity.Role;
import com.careerguidance.entity.StudentProfile;
import com.careerguidance.entity.User;
import com.careerguidance.repository.CareerGuideProfileRepository;
import com.careerguidance.repository.StudentProfileRepository;
import com.careerguidance.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CareerGuideProfileRepository careerGuideProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthService(UserRepository userRepository,
                       StudentProfileRepository studentProfileRepository,
                       CareerGuideProfileRepository careerGuideProfileRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       SecurityContextRepository securityContextRepository) {
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.careerGuideProfileRepository = careerGuideProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Transactional
    public UserDto register(RegisterRequest req, HttpServletRequest request, HttpServletResponse response) {
        if (userRepository.existsByEmail(req.getEmail().toLowerCase().trim())) {
            throw new IllegalArgumentException("An account with email " + req.getEmail() + " already exists.");
        }

        // Prevent escalation: only STUDENT or CAREER_GUIDE allowed from registration
        Role role = Role.STUDENT;
        if ("CAREER_GUIDE".equalsIgnoreCase(req.getRole())) {
            role = Role.CAREER_GUIDE;
        }

        User user = new User();
        user.setEmail(req.getEmail().toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName().trim());
        user.setPhone(req.getPhone());
        user.setRole(role);
        user.setActive(true);
        User savedUser = userRepository.save(user);

        if (role == Role.STUDENT) {
            StudentProfile profile = new StudentProfile(savedUser);
            studentProfileRepository.save(profile);
        } else if (role == Role.CAREER_GUIDE) {
            CareerGuideProfile guideProfile = new CareerGuideProfile(savedUser);
            guideProfile.setQualifications(req.getQualifications());
            guideProfile.setExpertiseAreas(req.getExpertiseAreas());
            guideProfile.setExperienceYears(req.getExperienceYears() != null ? req.getExperienceYears() : 0);
            guideProfile.setOrganization(req.getOrganization());
            guideProfile.setBio(req.getBio());
            guideProfile.setApprovalStatus("PENDING"); // Requires Admin approval
            careerGuideProfileRepository.save(guideProfile);
        }

        // Automatically log in after registration
        return performLogin(req.getEmail(), req.getPassword(), request, response);
    }

    public UserDto login(LoginRequest req, HttpServletRequest request, HttpServletResponse response) {
        return performLogin(req.getEmail(), req.getPassword(), request, response);
    }

    private UserDto performLogin(String email, String rawPassword, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(email.toLowerCase().trim(), rawPassword);
        Authentication auth = authenticationManager.authenticate(token);

        SecurityContext sc = SecurityContextHolder.createEmptyContext();
        sc.setAuthentication(auth);
        SecurityContextHolder.setContext(sc);
        securityContextRepository.saveContext(sc, request, response);

        HttpSession session = request.getSession(true);
        session.setAttribute("USER_EMAIL", email.toLowerCase().trim());

        User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in database"));

        return buildUserDto(user);
    }

    public Optional<User> getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return Optional.empty();
        }
        return userRepository.findByEmail(auth.getName().toLowerCase().trim());
    }

    public User getRequiredAuthenticatedUser() {
        return getCurrentAuthenticatedUser()
                .orElseThrow(() -> new IllegalStateException("Authentication required for this operation"));
    }

    public UserDto buildUserDto(User user) {
        UserDto dto = UserDto.fromEntity(user);
        if (user.getRole() == Role.STUDENT) {
            studentProfileRepository.findByUser(user)
                    .ifPresent(p -> dto.setProfileCompletionPercentage(p.getProfileCompletionPercentage()));
        } else if (user.getRole() == Role.CAREER_GUIDE) {
            careerGuideProfileRepository.findByUser(user)
                    .ifPresent(g -> dto.setGuideApprovalStatus(g.getApprovalStatus()));
        }
        return dto;
    }
}
