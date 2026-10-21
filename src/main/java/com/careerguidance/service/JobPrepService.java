package com.careerguidance.service;

import com.careerguidance.dto.JobPrepUpdateDto;
import com.careerguidance.entity.JobPrepProgress;
import com.careerguidance.entity.User;
import com.careerguidance.repository.JobPrepProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class JobPrepService {

    private final JobPrepProgressRepository jobPrepProgressRepository;

    public JobPrepService(JobPrepProgressRepository jobPrepProgressRepository) {
        this.jobPrepProgressRepository = jobPrepProgressRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Boolean> getUserProgressMap(User user) {
        List<JobPrepProgress> list = jobPrepProgressRepository.findByUser(user);
        Map<String, Boolean> map = new HashMap<>();
        for (JobPrepProgress item : list) {
            map.put(item.getItemKey(), item.getIsCompleted());
        }
        return map;
    }

    @Transactional
    public JobPrepProgress updateProgress(User user, JobPrepUpdateDto dto) {
        JobPrepProgress progress = jobPrepProgressRepository
                .findByUserIdAndItemKey(user.getId(), dto.getItemKey())
                .orElseGet(() -> new JobPrepProgress(user, dto.getCategory(), dto.getItemKey(), false, null));

        progress.setCategory(dto.getCategory());
        progress.setIsCompleted(dto.getIsCompleted());
        if (dto.getNotes() != null) {
            progress.setNotes(dto.getNotes());
        }
        return jobPrepProgressRepository.save(progress);
    }

    public List<Map<String, Object>> getInterviewQuestions() {
        List<Map<String, Object>> questions = new ArrayList<>();

        questions.add(Map.of(
            "id", "q1",
            "category", "Technical - Data Structures",
            "question", "How does a HashMap work internally in Java, and how does it handle hash collisions?",
            "answer", "A HashMap uses an array of Node buckets. When put(K, V) is called, it computes hash(K) to determine the bucket index. If multiple keys hash to the same bucket (collision), Java 8+ chains nodes in a LinkedList, and converts to a Balanced Red-Black Tree once the bucket reaches 8 elements (TREEIFY_THRESHOLD), reducing search time from O(n) to O(log n).",
            "key", "tech_hashmap"
        ));

        questions.add(Map.of(
            "id", "q2",
            "category", "Technical - System Design",
            "question", "What is the difference between horizontal and vertical scaling, and when should you choose each?",
            "answer", "Vertical scaling (scaling up) adds more compute/memory/disk to an existing server, which is simple but has hardware limits and single point of failure. Horizontal scaling (scaling out) adds more server instances behind a load balancer, providing fault tolerance, elasticity, and near-infinite scale.",
            "key", "tech_scaling"
        ));

        questions.add(Map.of(
            "id", "q3",
            "category", "Technical - Databases",
            "question", "Explain ACID properties in relational databases with a banking transaction example.",
            "answer", "Atomicity: All steps of money transfer (debit A and credit B) succeed together or rollback completely. Consistency: Account balances adhere to constraints. Isolation: Concurrent transactions do not read partial uncommitted balances. Durability: Once committed, the recorded transfer survives server crashes.",
            "key", "tech_acid"
        ));

        questions.add(Map.of(
            "id", "q4",
            "category", "Behavioral - STAR Method",
            "question", "Tell me about a challenging technical problem you faced in a project and how you resolved it.",
            "answer", "Use the STAR method: Situation (describe project & context), Task (what was the bottleneck or bug), Action (investigation, tools used, profiling, refactoring), Result (quantifiable improvement, e.g. latency dropped by 40% and code maintainability increased).",
            "key", "star_challenge"
        ));

        questions.add(Map.of(
            "id", "q5",
            "category", "Behavioral - Teamwork",
            "question", "How do you handle disagreement with a teammate regarding technical architecture or code reviews?",
            "answer", "Listen to their reasoning actively. Focus on project objectives, user impact, maintainability, and data benchmarks rather than subjective opinions. Create a brief prototype or benchmark test if uncertain, and seek constructive consensus or mentor guidance.",
            "key", "star_conflict"
        ));

        return questions;
    }

    public List<Map<String, Object>> getPortfolioProjectSuggestions() {
        List<Map<String, Object>> projects = new ArrayList<>();

        projects.add(Map.of(
            "title", "Enterprise Task & Sprint Management System",
            "category", "Full Stack Development",
            "techStack", "Java Spring Boot, MySQL, REST API, Vanilla JavaScript, CSS Flexbox/Grid",
            "description", "A production-ready Kanban project management app with JWT/session auth, drag-and-drop task boards, activity audit logs, and sprint velocity charts.",
            "key", "proj_kanban"
        ));

        projects.add(Map.of(
            "title", "AI-Powered Resume Analyzer & Skill Gap Recommender",
            "category", "Data Science & AI",
            "techStack", "Python, Scikit-learn / Hugging Face, FastAPI, React/Vanilla JS",
            "description", "Parses PDF resumes, extracts skills using NLP, calculates cosine similarity against real job descriptions, and highlights missing keywords with improvement suggestions.",
            "key", "proj_resume_ai"
        ));

        projects.add(Map.of(
            "title", "Cloud Microservices E-Commerce Platform",
            "category", "Cloud & DevOps",
            "techStack", "Docker, Kubernetes, Spring Cloud, PostgreSQL / MySQL, Prometheus",
            "description", "Containerized microservices covering Product Catalog, Order Processing, and Notification service with API Gateway, service discovery, and Docker Compose scripts.",
            "key", "proj_microservices"
        ));

        projects.add(Map.of(
            "title", "Cybersecurity Vulnerability & Log Monitor",
            "category", "Cybersecurity",
            "techStack", "Java / Python, SQLite / MySQL, ELK Stack concepts, Alerting Webhooks",
            "description", "Monitors server auth logs for brute-force SSH/HTTP attempts, geo-locates IP addresses, and triggers automated email/webhook notifications upon breach thresholds.",
            "key", "proj_security_log"
        ));

        return projects;
    }

    public List<Map<String, Object>> getResumeChecklist() {
        return List.of(
            Map.of("key", "resume_format", "title", "Clean, Single-Page ATS-Friendly Layout", "desc", "Use standard 1-inch margins, clear section headings, and sans-serif fonts without complex multi-column tables."),
            Map.of("key", "resume_verbs", "title", "Strong Action Verbs & Metrics", "desc", "Start each bullet point with strong action verbs (Architected, Engineered, Implemented, Streamlined) and quantify impact (reduced latency by 35%, increased throughput by 2x)."),
            Map.of("key", "resume_skills", "title", "Categorized Skills Section", "desc", "Group skills into Languages, Frameworks, Databases, Tools & Platforms rather than an unorganized blob."),
            Map.of("key", "resume_links", "title", "Live Project & GitHub / LinkedIn Links", "desc", "Include verified clickable hyperlinks to your GitHub repositories, live demo deployments, and professional LinkedIn profile."),
            Map.of("key", "resume_tailoring", "title", "Tailor for Specific Job Descriptions", "desc", "Refine project descriptions and keywords to match the exact requirements and technologies mentioned in the job post.")
        );
    }

    public List<Map<String, Object>> getSampleInternships() {
        // Clearly labeled as examples as required in section 8
        return List.of(
            Map.of("id", "ex1", "role", "Software Engineer Intern (Sample Opportunity)", "company", "TechCorp Labs (Example)", "location", "Remote / Hybrid", "stipend", "$25-35 / hr", "skills", "Java, Spring Boot, SQL, Git", "note", "Illustrative sample posting for application preparation practice"),
            Map.of("id", "ex2", "role", "Associate Data Analyst Intern (Sample Opportunity)", "company", "FinAnalytics Global (Example)", "location", "Bangalore / Remote", "stipend", "Competitive", "skills", "Python, SQL, Tableau, Statistics", "note", "Illustrative sample posting for application preparation practice"),
            Map.of("id", "ex3", "role", "Cloud Infrastructure Intern (Sample Opportunity)", "company", "CloudSphere Networks (Example)", "location", "Hybrid", "stipend", "Competitive", "skills", "Linux, AWS/GCP, Docker, Scripting", "note", "Illustrative sample posting for application preparation practice")
        );
    }
}
