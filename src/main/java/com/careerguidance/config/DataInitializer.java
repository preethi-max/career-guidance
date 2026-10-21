package com.careerguidance.config;

import com.careerguidance.entity.*;
import com.careerguidance.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CareerGuideProfileRepository guideProfileRepository;
    private final CareerRepository careerRepository;
    private final CareerSkillRepository careerSkillRepository;
    private final CareerRoadmapRepository roadmapRepository;
    private final RoadmapStepRepository stepRepository;
    private final CourseRepository courseRepository;
    private final LearningResourceRepository resourceRepository;
    private final MentorConnectionRequestRepository requestRepository;
    private final StudentRoadmapProgressRepository studentRoadmapProgressRepository;
    private final SavedResourceRepository savedResourceRepository;
    private final JobPrepProgressRepository jobPrepProgressRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           StudentProfileRepository studentProfileRepository,
                           CareerGuideProfileRepository guideProfileRepository,
                           CareerRepository careerRepository,
                           CareerSkillRepository careerSkillRepository,
                           CareerRoadmapRepository roadmapRepository,
                           RoadmapStepRepository stepRepository,
                           CourseRepository courseRepository,
                           LearningResourceRepository resourceRepository,
                           MentorConnectionRequestRepository requestRepository,
                           StudentRoadmapProgressRepository studentRoadmapProgressRepository,
                           SavedResourceRepository savedResourceRepository,
                           JobPrepProgressRepository jobPrepProgressRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.guideProfileRepository = guideProfileRepository;
        this.careerRepository = careerRepository;
        this.careerSkillRepository = careerSkillRepository;
        this.roadmapRepository = roadmapRepository;
        this.stepRepository = stepRepository;
        this.courseRepository = courseRepository;
        this.resourceRepository = resourceRepository;
        this.requestRepository = requestRepository;
        this.studentRoadmapProgressRepository = studentRoadmapProgressRepository;
        this.savedResourceRepository = savedResourceRepository;
        this.jobPrepProgressRepository = jobPrepProgressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Seed data already loaded
        }

        System.out.println(">>> Initializing sample data for Student Career Guidance App...");

        // 1. Create Admin
        User admin = new User("admin@careerguidance.com", passwordEncoder.encode("Admin@123"), "Platform Administrator", "+1-555-0199", Role.ADMIN);
        userRepository.save(admin);

        // 2. Create Career Guides
        User guide1 = new User("priya.sharma@careerguidance.com", passwordEncoder.encode("Guide@123"), "Dr. Priya Sharma", "+1-555-0101", Role.CAREER_GUIDE);
        userRepository.save(guide1);
        CareerGuideProfile gp1 = new CareerGuideProfile(guide1);
        gp1.setQualifications("Ph.D. in Computer Science & Artificial Intelligence (IIT Delhi)");
        gp1.setExpertiseAreas("Artificial Intelligence, Machine Learning, Deep Learning, Python, Research Careers");
        gp1.setExperienceYears(12);
        gp1.setCurrentDesignation("Principal AI Scientist");
        gp1.setOrganization("NeuralTech Research Labs");
        gp1.setBio("Passionate mentor helping ambitious students navigate cutting-edge AI, machine learning research, and high-impact industry careers.");
        gp1.setAvailabilityInfo("Weekdays 6:00 PM - 8:00 PM IST, Weekends by appointment");
        gp1.setLinkedinUrl("https://linkedin.com/in/demo-priya-sharma");
        gp1.setApprovalStatus("APPROVED");
        gp1.setApprovedAt(LocalDateTime.now());
        guideProfileRepository.save(gp1);

        User guide2 = new User("rajesh.kumar@careerguidance.com", passwordEncoder.encode("Guide@123"), "Rajesh Kumar", "+1-555-0102", Role.CAREER_GUIDE);
        userRepository.save(guide2);
        CareerGuideProfile gp2 = new CareerGuideProfile(guide2);
        gp2.setQualifications("M.S. in Software Systems, AWS Certified Solutions Architect Professional");
        gp2.setExpertiseAreas("Cloud Architecture, DevOps, Distributed Systems, Microservices, Kubernetes");
        gp2.setExperienceYears(9);
        gp2.setCurrentDesignation("Cloud Solutions Architect");
        gp2.setOrganization("Global Cloud Infrastructure Inc.");
        gp2.setBio("Helping engineering students master distributed architecture, cloud certifications, and modern DevOps career paths.");
        gp2.setAvailabilityInfo("Tuesdays & Thursdays 7:00 PM - 9:00 PM");
        gp2.setLinkedinUrl("https://linkedin.com/in/demo-rajesh-kumar");
        gp2.setApprovalStatus("APPROVED");
        gp2.setApprovedAt(LocalDateTime.now());
        guideProfileRepository.save(gp2);

        User guide3 = new User("ananya.roy@careerguidance.com", passwordEncoder.encode("Guide@123"), "Ananya Roy", "+1-555-0103", Role.CAREER_GUIDE);
        userRepository.save(guide3);
        CareerGuideProfile gp3 = new CareerGuideProfile(guide3);
        gp3.setQualifications("Master of Design (M.Des) in Human-Computer Interaction");
        gp3.setExpertiseAreas("UI/UX Design, Product Design, Design Thinking, Figma, User Research");
        gp3.setExperienceYears(7);
        gp3.setCurrentDesignation("Lead Product Designer");
        gp3.setOrganization("FinScale Digital Studio");
        gp3.setBio("Guiding aspiring designers to build standout portfolios, master Figma, and crack product design interviews.");
        gp3.setAvailabilityInfo("Weekends 10:00 AM - 1:00 PM");
        gp3.setLinkedinUrl("https://linkedin.com/in/demo-ananya-roy");
        gp3.setApprovalStatus("APPROVED");
        gp3.setApprovedAt(LocalDateTime.now());
        guideProfileRepository.save(gp3);

        User guide4 = new User("vikram.singh@careerguidance.com", passwordEncoder.encode("Guide@123"), "Vikram Singh", "+1-555-0104", Role.CAREER_GUIDE);
        userRepository.save(guide4);
        CareerGuideProfile gp4 = new CareerGuideProfile(guide4);
        gp4.setQualifications("B.Tech in Information Technology, Certified Ethical Hacker (CEH)");
        gp4.setExpertiseAreas("Cybersecurity, Penetration Testing, Network Security, SOC Operations");
        gp4.setExperienceYears(5);
        gp4.setCurrentDesignation("Senior Security Analyst");
        gp4.setOrganization("CyberShield Defense");
        gp4.setBio("Mentoring cybersecurity enthusiasts in hands-on CTFs, SOC workflows, and defense strategies.");
        gp4.setAvailabilityInfo("Flexible evenings");
        gp4.setApprovalStatus("PENDING"); // Pending approval for admin testing
        guideProfileRepository.save(gp4);

        // 3. Create Sample Student
        User student = new User("student@careerguidance.com", passwordEncoder.encode("Student@123"), "Aarav Sharma", "+1-555-0120", Role.STUDENT);
        userRepository.save(student);

        StudentProfile sp = new StudentProfile(student);
        sp.setEducationLevel("Undergraduate");
        sp.setDegree("Bachelor of Technology (B.Tech)");
        sp.setBranch("Computer Science and Engineering");
        sp.setCollegeName("Institute of Technology & Science");
        sp.setYearOfStudy("3rd Year");
        sp.setMarksOrCgpa("8.6 CGPA");
        sp.setTechnicalSkills("Java, Spring Boot, SQL, MySQL, Git, Data Structures, Algorithms, HTML, CSS, JavaScript");
        sp.setNonTechnicalSkills("Problem Solving, Team Collaboration, Analytical Thinking, Technical Communication");
        sp.setInterests("Software Development, Backend Architecture, Cloud Computing, High-Performance Systems");
        sp.setStrengths("Fast Learner, Code Optimization, Debugging Complex Problems, Consistency");
        sp.setPreferredFields("Software Development, Cloud Solutions, Distributed Systems");
        sp.setShortTermGoal("Crack a competitive Summer Software Engineering Internship at a top tech enterprise");
        sp.setLongTermGoal("Grow into a Staff Software Engineer / Architect building mission-critical scalable platforms");
        sp.setProfileCompletionPercentage(92);
        studentProfileRepository.save(sp);

        // 4. Seed Careers, Skills, Roadmaps & Steps
        initCareersAndRoadmaps(student, guide1);

        // 5. Seed Courses & Resources
        initCoursesAndResources(student);

        // 6. Seed Student Initial Connections & Progress
        MentorConnectionRequest demoRequest = new MentorConnectionRequest(student, guide1,
                "Hello Dr. Priya! I am an aspiring software and AI engineer interested in distributed backend systems. I would love your guidance on tailoring my portfolio.");
        demoRequest.setStatus("ACCEPTED");
        demoRequest.setUpdatedAt(LocalDateTime.now());
        requestRepository.save(demoRequest);

        // Seed some initial job prep progress
        jobPrepProgressRepository.save(new JobPrepProgress(student, "TECHNICAL", "tech_hashmap", true, "Mastered bucket collision resolution"));
        jobPrepProgressRepository.save(new JobPrepProgress(student, "RESUME", "resume_format", true, "Single-page ATS format finalized"));

        System.out.println(">>> Sample data initialization completed successfully!");
    }

    private void initCareersAndRoadmaps(User student, User guide1) {
        // Career 1: Full Stack Software Engineer
        Career c1 = new Career(
            "Full Stack Software Engineer",
            "Software Development",
            "Designs, builds, tests, and deploys end-to-end web applications and services spanning responsive client interfaces, robust backend APIs, and scalable relational databases.",
            "B.Tech / B.E. in Computer Science, Information Technology, or equivalent Bachelor's degree in technical field",
            "$75,000 - $140,000 / year",
            "High Demand (25% projected growth)",
            "Junior Full Stack Developer, Backend Engineer, Frontend Specialist, Full Stack Lead",
            "1. Master programming fundamentals (Java/JS). 2. Build full stack projects with database persistence. 3. Practice DSA. 4. Deploy web apps to cloud."
        );
        careerRepository.save(c1);
        addSkills(c1, List.of("Java", "JavaScript", "HTML", "CSS", "SQL", "Spring Boot", "Git", "REST APIs"),
                      List.of("Problem Solving", "Collaboration", "Code Review", "Agile Mindset"));

        CareerRoadmap rm1 = new CareerRoadmap(c1, "Full Stack Software Engineer Learning Roadmap",
                "Comprehensive milestone path from core coding fundamentals to production deployment and interview mastery.", "6 - 9 Months");
        roadmapRepository.save(rm1);

        createStep(rm1, 1, "Programming Fundamentals & Clean Code", "Master object-oriented programming in Java or JavaScript, clean coding principles, and control flows.", "Variables, OOP Concepts, Polymorphism, Collections Framework, Clean Code", "Oracle Java Docs, W3Schools, clean-code handbook", "3 Weeks");
        RoadmapStep step2 = createStep(rm1, 2, "Data Structures & Algorithms (DSA)", "Develop core algorithmic efficiency and problem-solving skills required for technical assessments.", "Arrays, LinkedLists, Stacks, Queues, Binary Trees, HashMaps, Sorting & Searching", "LeetCode, HackerRank, GeeksforGeeks", "5 Weeks");
        createStep(rm1, 3, "Database Design & SQL Mastery", "Design normalized relational schemas, write complex joins, indexes, and understand ACID transaction guarantees.", "DDL/DML, Foreign Keys, Indexing, Transactions, MySQL Optimization", "MySQL Official Docs, SQLZoo", "3 Weeks");
        createStep(rm1, 4, "Backend APIs with Spring Boot", "Develop scalable enterprise RESTful APIs, dependency injection, Spring Data JPA, and security authentication.", "Spring Web, JPA Entities, Hibernate, Spring Security, BCrypt, Session & JWT", "Spring Guides, Baeldung", "4 Weeks");
        createStep(rm1, 5, "Frontend Web Fundamentals & DOM", "Create modern responsive user interfaces using semantic HTML5, modern CSS3 Flexbox/Grid, and modular Vanilla JS.", "Semantic HTML5, CSS Flexbox & Grid, Fetch API, Asynchronous JS, Events", "MDN Web Docs, freeCodeCamp", "3 Weeks");
        createStep(rm1, 6, "Full-Stack Project Development", "Combine backend REST APIs with interactive frontend interfaces into a complete deployable application.", "Project Architecture, API Integration, Error Handling, Responsive UX", "GitHub Guides, Full Stack Tutorials", "4 Weeks");
        createStep(rm1, 7, "Version Control & CI/CD Basics", "Collaborate using Git, master branching workflows, pull requests, and automated testing.", "Git branching, Merge Conflicts, GitHub Actions, Docker Basics", "Git Book, Pro Git", "2 Weeks");
        createStep(rm1, 8, "Interview Preparation & System Design", "Prepare for technical coding interviews, behavioral STAR rounds, and foundational system design.", "System Design Basics, LeetCode Blind 75, Behavioral Questions", "Grokking System Design, NeetCode", "3 Weeks");

        // Mark steps 1 and 2 completed for demo student
        List<RoadmapStep> c1Steps = stepRepository.findByRoadmapOrderByStepNumberAsc(rm1);
        if (!c1Steps.isEmpty()) {
            studentRoadmapProgressRepository.save(new StudentRoadmapProgress(student, rm1, c1Steps.get(0), true));
            if (c1Steps.size() > 1) {
                studentRoadmapProgressRepository.save(new StudentRoadmapProgress(student, rm1, c1Steps.get(1), true));
            }
        }

        // Career 2: Data Scientist & Machine Learning Specialist
        Career c2 = new Career(
            "Data Scientist & Machine Learning Specialist",
            "Data Science & AI",
            "Transforms complex raw data into actionable intelligence and builds predictive statistical and machine learning models to solve business challenges.",
            "B.Tech / M.Sc in Computer Science, Mathematics, Statistics, Data Science, or related quantitative discipline",
            "$85,000 - $155,000 / year",
            "Very High Demand (32% growth)",
            "Data Analyst, Junior Data Scientist, Machine Learning Engineer, AI Research Associate",
            "1. Solidify Python and linear algebra. 2. Master pandas and SQL data wrangling. 3. Learn Scikit-Learn ML models. 4. Build Kaggle portfolio."
        );
        careerRepository.save(c2);
        addSkills(c2, List.of("Python", "SQL", "Pandas", "NumPy", "Scikit-Learn", "Machine Learning", "Statistics"),
                      List.of("Critical Thinking", "Data Storytelling", "Curiosity", "Business Acumen"));

        CareerRoadmap rm2 = new CareerRoadmap(c2, "Data Science & Machine Learning Roadmap",
                "Structured journey from exploratory data analysis to production predictive modeling.", "8 - 12 Months");
        roadmapRepository.save(rm2);
        createStep(rm2, 1, "Mathematics, Statistics & Probability", "Core foundations of linear algebra, calculus, descriptive statistics, and probability distributions.", "Linear Algebra, Vectors, Matrices, Probability, Hypothesis Testing", "Khan Academy Math, StatQuest", "4 Weeks");
        createStep(rm2, 2, "Python for Data Science", "Master Python scripting, Jupyter notebooks, NumPy arrays, and Pandas DataFrames.", "Python Data Structures, NumPy, Pandas, Vectorization", "Python Data Science Handbook", "3 Weeks");
        createStep(rm2, 3, "Data Wrangling, Cleaning & SQL", "Extract, transform, and clean messy real-world datasets using advanced SQL queries and Pandas.", "Data Imputation, Joins, Aggregations, Window Functions", "Mode Analytics SQL Tutorial", "3 Weeks");
        createStep(rm2, 4, "Data Visualization & EDA", "Uncover patterns, correlations, and anomalies using visual storytelling tools.", "Matplotlib, Seaborn, Storytelling with Data", "Seaborn Gallery, Tableau Public", "2 Weeks");
        createStep(rm2, 5, "Supervised & Unsupervised Machine Learning", "Implement linear regression, classification trees, random forests, clustering, and evaluation metrics.", "Scikit-Learn, Regression, Classification, ROC-AUC, K-Means", "Coursera ML by Andrew Ng", "6 Weeks");
        createStep(rm2, 6, "Applied Capstone & Model Deployment", "Build an end-to-end predictive web service and document findings in a portfolio case study.", "Model Serialization, FastAPI, Streamlit, Cloud Hosting", "Kaggle Competitions, GitHub", "4 Weeks");

        // Career 3: Cloud Solutions Architect
        Career c3 = new Career(
            "Cloud Solutions Architect",
            "Cloud Computing",
            "Designs scalable, resilient, and secure cloud infrastructure environments across AWS, Azure, or Google Cloud, optimizing performance and cost.",
            "Bachelor's degree in Computer Science, IT, or Engineering + Industry Cloud Certifications (AWS/Azure)",
            "$90,000 - $160,000 / year",
            "High Demand (27% growth)",
            "Cloud Engineer, Cloud DevOps Specialist, Cloud Solutions Architect, Infrastructure Lead",
            "1. Master Linux and networking fundamentals. 2. Learn cloud service models (IaaS/PaaS). 3. Earn associate cloud certification. 4. Implement IaC with Terraform."
        );
        careerRepository.save(c3);
        addSkills(c3, List.of("AWS", "Linux", "Docker", "Kubernetes", "Terraform", "Networking", "Security"),
                      List.of("Architectural Thinking", "Cost Optimization", "Strategic Communication"));
        CareerRoadmap rm3 = new CareerRoadmap(c3, "Cloud Architecture Roadmap", "End-to-end blueprint to master modern cloud platforms.", "6 - 10 Months");
        roadmapRepository.save(rm3);
        createStep(rm3, 1, "Operating Systems & Networking Basics", "Grasp Linux CLI, DNS, TCP/IP, OSI model, subnets, and routing protocols.", "Linux Administration, Bash, VPC, CIDR, Firewalls", "Linux Journey, CompTIA Network+", "4 Weeks");
        createStep(rm3, 2, "Core Cloud Services (Compute & Storage)", "Provision virtual machines, object storage, and block storage in AWS or Azure.", "EC2, S3, IAM, EBS, Azure VMs, Blob Storage", "AWS Skill Builder, A Cloud Guru", "4 Weeks");
        createStep(rm3, 3, "Cloud Networking & High Availability", "Architect multi-AZ VPCs, public/private subnets, NAT gateways, and load balancers.", "VPC Peering, ALB, Auto Scaling Groups, Route 53", "AWS Well-Architected Framework", "4 Weeks");
        createStep(rm3, 4, "Containers & Orchestration", "Package microservices with Docker and orchestrate them with Kubernetes or ECS.", "Dockerfiles, Images, Pods, Deployments, Services", "Kubernetes Official Docs", "4 Weeks");
        createStep(rm3, 5, "Infrastructure as Code (IaC)", "Automate repeatable infrastructure provisioning using Terraform or CloudFormation.", "Terraform HCL, State Management, Modules", "Terraform Tutorials", "3 Weeks");

        // Career 4: Cybersecurity Analyst & Specialist
        Career c4 = new Career(
            "Cybersecurity Analyst & Specialist",
            "Cybersecurity",
            "Protects organization networks, software systems, and data assets from cyber attacks, unauthorized breaches, and security vulnerabilities.",
            "Degree in Cybersecurity, Computer Science, IT, or relevant certifications (CompTIA Security+, CEH, CISSP)",
            "$78,000 - $145,000 / year",
            "Extremely High (35% growth)",
            "SOC Analyst, Penetration Tester, Incident Responder, Information Security Specialist",
            "1. Understand networks and protocols. 2. Learn vulnerability scanning and SIEM tools. 3. Practice on TryHackMe/HackTheBox. 4. Earn Security+."
        );
        careerRepository.save(c4);
        addSkills(c4, List.of("Network Security", "Linux", "Penetration Testing", "Wireshark", "SIEM", "Cryptography"),
                      List.of("Attention to Detail", "Ethical Mindset", "Analytical Investigation"));
        CareerRoadmap rm4 = new CareerRoadmap(c4, "Cybersecurity Analyst Roadmap", "Step-by-step path to becoming an enterprise defense specialist.", "6 - 9 Months");
        roadmapRepository.save(rm4);
        createStep(rm4, 1, "Network Protocols & Traffic Analysis", "Deep-dive into Wireshark, packet inspection, and common protocol vulnerabilities.", "TCP/UDP, SSL/TLS, Port Scanning, Wireshark", "Professor Messer Network+", "3 Weeks");
        createStep(rm4, 2, "Security Fundamentals & Threat Landscape", "Explore OWASP Top 10, malware analysis, social engineering, and defense-in-depth.", "OWASP Top 10, Common CVEs, Threat Actors", "OWASP Foundation Docs", "3 Weeks");
        createStep(rm4, 3, "SOC Operations & SIEM Monitoring", "Analyze security event logs and detect attack signatures using Splunk or ELK.", "Splunk, Log Analysis, Incident Triaging", "Splunk Fundamentals Free", "4 Weeks");
        createStep(rm4, 4, "Hands-on Ethical Hacking Practice", "Solve lab challenges and learn privilege escalation and web application testing.", "TryHackMe, OverTheWire, PortSwigger Web Security", "PortSwigger Web Academy", "5 Weeks");

        // Career 5: UI/UX Product Designer
        Career c5 = new Career(
            "UI/UX Product Designer",
            "UI/UX Design",
            "Researches user needs, crafts wireframes, designs interactive high-fidelity prototypes, and builds cohesive design systems for intuitive digital experiences.",
            "Degree in Design, Human-Computer Interaction (HCI), Computer Science, or strong UX portfolio",
            "$70,000 - $130,000 / year",
            "Strong Demand (16% growth)",
            "Junior UI Designer, UX Researcher, Interaction Designer, Lead Product Designer",
            "1. Learn design principles & typography. 2. Master Figma & auto-layout. 3. Conduct user interviews. 4. Publish 2 deep case studies."
        );
        careerRepository.save(c5);
        addSkills(c5, List.of("Figma", "Wireframing", "Prototyping", "User Research", "Design Systems", "Usability Testing"),
                      List.of("Empathy", "Visual Communication", "Active Listening", "Presentation Skills"));
        CareerRoadmap rm5 = new CareerRoadmap(c5, "UI/UX Product Design Roadmap", "From design thinking theory to a production-ready Figma portfolio.", "4 - 8 Months");
        roadmapRepository.save(rm5);
        createStep(rm5, 1, "Design Thinking & UX Foundations", "Understand user-centric problem solving, double diamond process, and cognitive heuristics.", "Empathize, Define, Ideate, Prototype, Test, Nielsen Heuristics", "Nielsen Norman Group Articles", "3 Weeks");
        createStep(rm5, 2, "Figma Mastery & Component Architecture", "Master responsive auto-layout, variants, design tokens, and interactive components.", "Figma Auto-layout, Variants, Variables, Prototyping", "Figma YouTube Tutorials", "3 Weeks");
        createStep(rm5, 3, "User Research & Usability Testing", "Conduct qualitative interviews, create user personas, journey maps, and card sorting.", "User Personas, Empathy Maps, Journey Mapping", "UX Design Institute Guides", "3 Weeks");
        createStep(rm5, 4, "High-Fidelity Case Study Portfolio", "Document two comprehensive mobile/web application redesigns or product concepts.", "Case Study Structure, Problem Statement, Metrics", "Behance, Dribbble, Medium UX", "4 Weeks");

        // Career 6: Product / Business Manager
        Career c6 = new Career(
            "Product / Business Manager",
            "Business & Management",
            "Defines product vision, prioritizes feature roadmaps, aligns engineering, marketing, and design teams, and drives business growth metrics.",
            "Bachelor's in Business, Engineering, or Computer Science; MBA or relevant product leadership certifications",
            "$85,000 - $165,000 / year",
            "Steady Growth (18% growth)",
            "Associate Product Manager (APM), Product Owner, Business Analyst, Senior PM",
            "1. Understand product lifecycle & agile. 2. Learn customer discovery. 3. Master metric definition (KPIs/OKRs). 4. Create PRDs."
        );
        careerRepository.save(c6);
        addSkills(c6, List.of("Agile / Scrum", "Product Roadmapping", "User Stories", "Market Research", "Jira", "Data Analytics"),
                      List.of("Leadership", "Strategic Vision", "Stakeholder Management", "Negotiation"));
        CareerRoadmap rm6 = new CareerRoadmap(c6, "Product Management Roadmap", "Master the intersection of technology, business, and user experience.", "6 - 9 Months");
        roadmapRepository.save(rm6);
        createStep(rm6, 1, "Product Fundamentals & Agile Scrum", "Learn product discovery, agile sprints, backlog grooming, and user story mapping.", "Agile Manifestos, Scrum Ceremonies, Backlog Prioritization", "Scrum.org Open Assessments", "3 Weeks");
        createStep(rm6, 2, "User Discovery & Product Spec (PRD)", "Write detailed Product Requirement Documents (PRDs) with acceptance criteria and mockups.", "Problem Definition, Functional Specs, Edge Cases", "Lenny's Newsletter, Product School", "3 Weeks");
        createStep(rm6, 3, "Product Metrics & A/B Testing", "Define North Star metrics, retention funnels, and statistical experimentation.", "Acquisition, Activation, Retention, Revenue, Referral (AARRR)", "Reforge Articles, Amplitude Guide", "3 Weeks");

        // Career 7: DevOps & Site Reliability Engineer
        Career c7 = new Career(
            "DevOps & Site Reliability Engineer",
            "DevOps & Infrastructure",
            "Bridges software development and IT operations to automate software delivery pipelines, enforce reliability, and maintain continuous uptime.",
            "Degree in Computer Science, Systems Engineering, or equivalent practical operations experience",
            "$85,000 - $155,000 / year",
            "High Demand (28% growth)",
            "Junior DevOps Engineer, SRE, Build Engineer, Platform Engineering Lead",
            "1. Master Linux and Bash. 2. Implement CI/CD pipelines. 3. Learn Docker & Kubernetes. 4. Set up observability with Prometheus/Grafana."
        );
        careerRepository.save(c7);
        addSkills(c7, List.of("Linux", "Docker", "Kubernetes", "CI/CD", "Git", "Bash", "Prometheus", "Grafana"),
                      List.of("Automation Mindset", "Troubleshooting", "Calmness Under Pressure"));
        CareerRoadmap rm7 = new CareerRoadmap(c7, "DevOps & SRE Roadmap", "The complete path to automated delivery and high-availability operations.", "6 - 10 Months");
        roadmapRepository.save(rm7);
        createStep(rm7, 1, "Scripting & Linux Systems Administration", "Master shell scripting, process monitoring, systemd, and cron automation.", "Bash, Systemd, Process Management, Logs", "Linux Command Line Book", "3 Weeks");
        createStep(rm7, 2, "Continuous Integration & Deployment (CI/CD)", "Build automated pipelines with GitHub Actions or Jenkins to build, test, and package code.", "GitHub Actions, Jenkins, Webhooks, Artifact Registry", "GitHub Actions Documentation", "3 Weeks");
        createStep(rm7, 3, "Containerization & Orchestration", "Containerize apps with Docker and deploy resilient Kubernetes clusters.", "Docker Compose, Helm, K8s Pods, Ingress Controllers", "Kubernetes The Hard Way", "5 Weeks");

        // Career 8: Artificial Intelligence & NLP Engineer
        Career c8 = new Career(
            "Artificial Intelligence & NLP Engineer",
            "Data Science & AI",
            "Specializes in language models, transformer architectures, computer vision, and building intelligent agents for real-world enterprise applications.",
            "Degree in Computer Science, AI, or Mathematics with strong programming background",
            "$95,000 - $175,000 / year",
            "Explosive Demand (40% growth)",
            "AI Research Engineer, NLP Specialist, Machine Learning Engineer, Prompt/LLM Engineer",
            "1. Master deep learning basics. 2. Understand Attention and Transformer models. 3. Work with PyTorch & Hugging Face. 4. Build RAG pipelines."
        );
        careerRepository.save(c8);
        addSkills(c8, List.of("Python", "PyTorch", "Hugging Face", "Transformers", "NLP", "LangChain", "Vector Databases"),
                      List.of("Mathematical Rigor", "Innovation", "Experimentation"));
        CareerRoadmap rm8 = new CareerRoadmap(c8, "AI & NLP Engineer Roadmap", "Advance from core deep learning to Large Language Models and intelligent agents.", "8 - 12 Months");
        roadmapRepository.save(rm8);
        createStep(rm8, 1, "Deep Learning Foundations with PyTorch", "Understand neural networks, backpropagation, activation functions, and PyTorch tensors.", "Tensors, Loss Functions, Optimizers, CNNs, RNNs", "Deep Learning with PyTorch Book", "4 Weeks");
        createStep(rm8, 2, "Transformers & Hugging Face Ecosystem", "Master attention mechanisms, BERT, GPT architectures, tokenizers, and Hugging Face pipelines.", "Self-Attention, Pretraining, Fine-Tuning, Hugging Face", "Hugging Face NLP Course", "4 Weeks");
        createStep(rm8, 3, "Retrieval-Augmented Generation (RAG) & LLM Apps", "Build knowledge-grounded AI applications using vector search and orchestration frameworks.", "Vector DBs, Embeddings, LangChain, LlamaIndex, Prompting", "LangChain Documentation", "4 Weeks");

        // Career 9: Financial Analyst & FinTech Specialist
        Career c9 = new Career(
            "Financial Analyst & FinTech Specialist",
            "Finance & Economics",
            "Combines traditional financial modeling, risk analysis, and budgeting with modern financial technology tools, automated analytics, and algorithmic trading concepts.",
            "Degree in Finance, Economics, Commerce, Business, or Computer Science with finance minor",
            "$72,000 - $135,000 / year",
            "Steady Growth (15% growth)",
            "Financial Analyst, Risk Associate, FinTech Product Specialist, Quantitative Analyst",
            "1. Master financial statements & valuation. 2. Learn Excel/VBA & Python financial modeling. 3. Understand FinTech payment rails & blockchain."
        );
        careerRepository.save(c9);
        addSkills(c9, List.of("Financial Modeling", "Excel", "Python", "SQL", "Valuation", "Risk Management"),
                      List.of("Quantitative Precision", "Commercial Awareness", "Ethical Responsibility"));
        CareerRoadmap rm9 = new CareerRoadmap(c9, "Financial Analyst & FinTech Roadmap", "Master quantitative financial analysis and modern FinTech systems.", "6 - 9 Months");
        roadmapRepository.save(rm9);
        createStep(rm9, 1, "Financial Accounting & Statement Analysis", "Analyze Balance Sheets, Income Statements, Cash Flow, and compute liquidity and profitability ratios.", "Accounting Principles, Ratio Analysis, EBITDA", "Corporate Finance Institute (CFI)", "3 Weeks");
        createStep(rm9, 2, "Financial Modeling in Excel & DCF Valuation", "Build dynamic 3-statement models, discounted cash flow (DCF) models, and sensitivity tables.", "Excel Shortcuts, DCF Modeling, WACC, Sensitivity Analysis", "WallStreetPrep Free Tutorials", "4 Weeks");
        createStep(rm9, 3, "Python & SQL for Financial Analytics", "Automate financial data ingestion, portfolio risk calculations, and stock performance analysis.", "yfinance, Pandas, SQL Joins, Monte Carlo Simulation", "Python for Finance Handbook", "4 Weeks");

        // Career 10: Computer Science Educator & Technical Trainer
        Career c10 = new Career(
            "Computer Science Educator & Technical Trainer",
            "Education & Training",
            "Empowers aspiring software developers and university students through structured coding bootcamps, academic courses, technical writing, and corporate training.",
            "Bachelor's or Master's degree in Computer Science, Education, or related field with strong instructional enthusiasm",
            "$60,000 - $115,000 / year",
            "Growing Demand (20% growth)",
            "Technical Trainer, CS Lecturer, Bootcamp Instructor, Developer Advocate",
            "1. Solidify computer science pedagogy. 2. Build open source curriculum. 3. Create interactive coding tutorials. 4. Deliver public workshops."
        );
        careerRepository.save(c10);
        addSkills(c10, List.of("Java", "Python", "Curriculum Design", "Technical Writing", "Public Speaking", "Mentorship"),
                      List.of("Patience", "Empathy", "Clear Articulation", "Pedagogical Structure"));
        CareerRoadmap rm10 = new CareerRoadmap(c10, "Technical Educator Roadmap", "Transform technical mastery into inspiring and scalable learning experiences.", "4 - 8 Months");
        roadmapRepository.save(rm10);
        createStep(rm10, 1, "Pedagogical Design & Curriculum Architecture", "Learn how adults learn programming, bloom's taxonomy, and structured syllabus creation.", "Learning Objectives, Assessment Rubrics, Active Learning", "Teaching Tech Together Book", "3 Weeks");
        createStep(rm10, 2, "Technical Writing & Developer Documentation", "Write clear code tutorials, interactive READMEs, and technical blog breakdowns.", "Markdown, Diataxis Framework, Technical Blogging", "Google Technical Writing Course", "3 Weeks");
        createStep(rm10, 3, "Public Speaking & Interactive Live Coding", "Deliver engaging workshops, live code without fear, and manage student Q&A seamlessly.", "Workshop Delivery, Live Coding Strategies, Feedback Loops", "Toastmasters, DevRel Guides", "3 Weeks");
    }

    private void addSkills(Career career, List<String> techSkills, List<String> softSkills) {
        for (String skill : techSkills) {
            careerSkillRepository.save(new CareerSkill(career, skill, "TECHNICAL", 3));
        }
        for (String skill : softSkills) {
            careerSkillRepository.save(new CareerSkill(career, skill, "SOFT", 2));
        }
    }

    private RoadmapStep createStep(CareerRoadmap roadmap, int number, String title, String desc, String topics, String resources, String weeks) {
        RoadmapStep step = new RoadmapStep(roadmap, number, title, desc, topics, resources, weeks);
        return stepRepository.save(step);
    }

    private void initCoursesAndResources(User student) {
        // Sample Courses
        Course c1 = new Course("Java Programming Masterclass: From Beginner to Pro", "Oracle / Udemy", "Software Development", "BEGINNER", 4.8, true, true, "https://dev.java/learn/", "Comprehensive course covering modern Java syntax, OOP, collections, and clean multithreading.");
        courseRepository.save(c1);

        Course c2 = new Course("Spring Boot 3 & Microservices: Build Real-World Enterprise Backends", "Spring Academy", "Software Development", "INTERMEDIATE", 4.9, true, true, "https://spring.io/guides", "Learn Spring Web, Spring Data JPA, Hibernate, security, and cloud-ready RESTful architecture.");
        courseRepository.save(c2);

        Course c3 = new Course("Machine Learning Specialization", "DeepLearning.AI / Coursera", "Data Science & AI", "BEGINNER", 4.9, false, true, "https://www.coursera.org/specializations/machine-learning-introduction", "Andrew Ng's flagship foundation in supervised ML, neural networks, and decision trees.");
        courseRepository.save(c3);

        Course c4 = new Course("AWS Certified Solutions Architect Associate Complete Guide", "FreeCodeCamp & AWS Skill Builder", "Cloud Computing", "INTERMEDIATE", 4.8, true, true, "https://explore.skillbuilder.aws/", "Complete free curriculum to master AWS compute, storage, VPC networking, IAM, and high availability.");
        courseRepository.save(c4);

        Course c5 = new Course("Cybersecurity Fundamentals & Security Operations (SOC)", "Cisco Networking Academy", "Cybersecurity", "BEGINNER", 4.7, true, true, "https://www.netacad.com/courses/cybersecurity", "Explore enterprise security concepts, threat defense, ethical hacking ethics, and traffic monitoring.");
        courseRepository.save(c5);

        Course c6 = new Course("Google UX Design Professional Certificate", "Google", "UI/UX Design", "BEGINNER", 4.8, false, true, "https://grow.google/certificates/ux-design/", "Hands-on foundation covering user research, low-fidelity wireframing, Figma prototyping, and usability testing.");
        courseRepository.save(c6);

        Course c7 = new Course("Data Structures and Algorithms in Java", "HackerRank & LeetCode", "Software Development", "INTERMEDIATE", 4.9, true, false, "https://leetcode.com/explore/", "Core algorithmic patterns, time complexity analysis, recursion, dynamic programming, and interview challenges.");
        courseRepository.save(c7);

        Course c8 = new Course("Full Stack Web Development with Modern HTML, CSS & Vanilla JS", "MDN Web Docs", "Software Development", "BEGINNER", 4.8, true, false, "https://developer.mozilla.org/en-US/docs/Learn", "Authoritative guide from Mozilla covering web fundamentals, DOM manipulation, responsive layouts, and modern Fetch APIs.");
        courseRepository.save(c8);

        // Bookmark course 1 and 2 for sample student
        savedResourceRepository.save(new SavedResource(student, "COURSE", c1.getId()));
        savedResourceRepository.save(new SavedResource(student, "COURSE", c2.getId()));

        // Sample Learning Resources
        resourceRepository.save(new LearningResource("System Design Primer: An Open-Source Master Guide", "DOCUMENTATION", "Software Development", "https://github.com/donnemartin/system-design-primer", "Learn how to architect large-scale systems, caching strategies, load balancing, and SQL vs NoSQL."));
        resourceRepository.save(new LearningResource("NeetCode 150: Essential LeetCode Patterns Explained", "VIDEO", "Software Development", "https://neetcode.io/practice", "Curated interactive roadmap of core coding interview patterns with video breakdowns."));
        resourceRepository.save(new LearningResource("Python Data Science Handbook by Jake VanderPlas", "BOOK", "Data Science & AI", "https://jakevdp.github.io/PythonDataScienceHandbook/", "Free online text covering NumPy, Pandas, Matplotlib, and Scikit-Learn with code notebooks."));
        resourceRepository.save(new LearningResource("Nielsen Norman Group UX Heuristics & Research Articles", "ARTICLE", "UI/UX Design", "https://www.nngroup.com/articles/", "World-renowned research repository for usability principles, heuristics, and user interview methodologies."));
        resourceRepository.save(new LearningResource("OWASP Top 10 Web Application Security Risks", "DOCUMENTATION", "Cybersecurity", "https://owasp.org/www-project-top-ten/", "Official security guidelines detailing the most critical web application security vulnerabilities and prevention tips."));
    }
}
