## Project Overview
InternFinder aggregates internship listings from multiple job platforms into a single interface, eliminating duplicates through smart hash-based identification.

## Problem Statement
Finding internships requires checking multiple job sites daily, resulting in redundant listings and wasted time. InternFinder centralizes listings and removes duplicates to streamline this process.

## System Architecture

- **Backend**: Spring Boot with JPA/JDBC
- **Frontend**: React with filtering capabilities
- **Database**: Currently AWS DynamoDB (PostgreSQL planned for other informations like user information)
- **Job Collection**: AWS Lambda functions (coordinator-worker pattern) with JobSpy

### AWS Lambda Architecture
- **Coordinator**: Manages search configurations and dispatches tasks
- **Worker**: Scrapes job listings and handles deduplication

## Features
- Multi-platform job aggregation (LinkedIn, Indeed)
- Intelligent deduplication
- Location and field filtering
- Support for various job types (Remote, Office, Hybrid)

## Planned Features
- PostgreSQL integration 
- Authentication
- Advanced filtering
- Job recommendations

## Deployment
- AWS deployment with Docker support

## Getting Started

### Prerequisites
- Java JDK 23
- Maven 
- Docker

