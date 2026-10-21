-- ====================================================================
-- Database Schema for Student Career Guidance App
-- Database: career_guidance_db
-- ====================================================================

CREATE DATABASE IF NOT EXISTS career_guidance_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE career_guidance_db;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    phone VARCHAR(30),
    role VARCHAR(30) NOT NULL DEFAULT 'STUDENT', -- 'STUDENT', 'CAREER_GUIDE', 'ADMIN'
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_email (email),
    INDEX idx_user_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Student Profiles
CREATE TABLE IF NOT EXISTS student_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    education_level VARCHAR(100),
    degree VARCHAR(150),
    branch VARCHAR(150),
    college_name VARCHAR(255),
    year_of_study VARCHAR(50),
    marks_or_cgpa VARCHAR(50),
    technical_skills TEXT,     -- Comma-separated or JSON list
    non_technical_skills TEXT,
    interests TEXT,
    strengths TEXT,
    preferred_fields TEXT,
    short_term_goal TEXT,
    long_term_goal TEXT,
    profile_completion_percentage INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Career Guide Profiles
CREATE TABLE IF NOT EXISTS career_guide_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    qualifications VARCHAR(255),
    expertise_areas TEXT,
    experience_years INT DEFAULT 0,
    current_designation VARCHAR(150),
    organization VARCHAR(200),
    bio TEXT,
    availability_info VARCHAR(200),
    linkedin_url VARCHAR(255),
    approval_status VARCHAR(30) NOT NULL DEFAULT 'PENDING', -- 'PENDING', 'APPROVED', 'REJECTED'
    approved_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_guide_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_guide_status (approval_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Careers
CREATE TABLE IF NOT EXISTS careers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    required_education TEXT NOT NULL,
    salary_range VARCHAR(100),
    job_outlook VARCHAR(100),
    sample_roles TEXT,
    preparation_steps TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_career_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Career Skills
CREATE TABLE IF NOT EXISTS career_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    career_id BIGINT NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    skill_type VARCHAR(30) NOT NULL DEFAULT 'TECHNICAL', -- 'TECHNICAL', 'SOFT'
    weight INT DEFAULT 1,
    CONSTRAINT fk_skill_career FOREIGN KEY (career_id) REFERENCES careers(id) ON DELETE CASCADE,
    INDEX idx_career_skill (career_id, skill_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Career Roadmaps
CREATE TABLE IF NOT EXISTS career_roadmaps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    career_id BIGINT NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    estimated_months VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_roadmap_career FOREIGN KEY (career_id) REFERENCES careers(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Roadmap Steps
CREATE TABLE IF NOT EXISTS roadmap_steps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roadmap_id BIGINT NOT NULL,
    step_number INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    key_topics TEXT,
    learning_resources_text TEXT,
    estimated_weeks VARCHAR(50),
    CONSTRAINT fk_step_roadmap FOREIGN KEY (roadmap_id) REFERENCES career_roadmaps(id) ON DELETE CASCADE,
    INDEX idx_step_order (roadmap_id, step_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Student Roadmap Progress
CREATE TABLE IF NOT EXISTS student_roadmap_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    roadmap_id BIGINT NOT NULL,
    step_id BIGINT NOT NULL,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMP NULL,
    CONSTRAINT fk_prog_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_prog_roadmap FOREIGN KEY (roadmap_id) REFERENCES career_roadmaps(id) ON DELETE CASCADE,
    CONSTRAINT fk_prog_step FOREIGN KEY (step_id) REFERENCES roadmap_steps(id) ON DELETE CASCADE,
    UNIQUE KEY uq_user_step (user_id, step_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. Courses
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    provider VARCHAR(150),
    career_category VARCHAR(100),
    level VARCHAR(50) DEFAULT 'BEGINNER', -- 'BEGINNER', 'INTERMEDIATE', 'ADVANCED'
    rating DECIMAL(3, 1) DEFAULT 4.5,
    is_free BOOLEAN DEFAULT TRUE,
    certification_available BOOLEAN DEFAULT TRUE,
    url VARCHAR(500),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Learning Resources
CREATE TABLE IF NOT EXISTS learning_resources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    type VARCHAR(50) DEFAULT 'ARTICLE', -- 'ARTICLE', 'DOCUMENTATION', 'VIDEO', 'BOOK'
    career_category VARCHAR(100),
    url VARCHAR(500),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. Mentor Connection Requests
CREATE TABLE IF NOT EXISTS mentor_connection_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    guide_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING', -- 'PENDING', 'ACCEPTED', 'REJECTED'
    initial_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_req_student FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_req_guide FOREIGN KEY (guide_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_req_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12. Chat Messages
CREATE TABLE IF NOT EXISTS chat_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    connection_request_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    message_text TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_chat_req FOREIGN KEY (connection_request_id) REFERENCES mentor_connection_requests(id) ON DELETE CASCADE,
    CONSTRAINT fk_chat_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_chat_recipient FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_chat_convo (connection_request_id, sent_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 13. Saved Resources
CREATE TABLE IF NOT EXISTS saved_resources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    resource_type VARCHAR(50) NOT NULL, -- 'COURSE', 'RESOURCE', 'CAREER'
    resource_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_saved_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uq_user_saved (user_id, resource_type, resource_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 14. Job Preparation Progress
CREATE TABLE IF NOT EXISTS job_prep_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category VARCHAR(50) NOT NULL, -- 'TECHNICAL', 'SOFT', 'INTERVIEW', 'RESUME', 'PORTFOLIO', 'INTERNSHIP'
    item_key VARCHAR(100) NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    notes TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_prep_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uq_user_prep (user_id, item_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
