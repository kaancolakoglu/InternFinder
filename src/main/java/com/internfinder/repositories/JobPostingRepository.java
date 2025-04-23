package com.internfinder.repositories;

import com.internfinder.domain.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    Optional<JobPosting> findJobPostingById(Long id);

    Page<JobPosting> findAll(Pageable pageable);

    @Query(value = ""
            + "SELECT jp.* "
            + "  FROM job_posting jp "
            + "  JOIN company c            ON jp.company_id            = c.id "
            + "  JOIN company_size cs      ON c.company_size_id        = cs.id "
            + "  JOIN industry ind         ON c.industry_id            = ind.id "
            + "  JOIN posting_type pt      ON jp.id                     = pt.job_posting_id "
            + "  JOIN currency cur         ON jp.currency_id           = cur.id "
            + "  JOIN experience_level el  ON jp.experience_level_id   = el.id "
            + "  JOIN role_type rt         ON jp.id                     = rt.job_posting_id "
            + "  JOIN location loc         ON jp.location_id           = loc.id "
            + "  JOIN city ci              ON loc.city_id              = ci.id "
            + "  JOIN state_province sp    ON ci.state_province_id     = sp.id "
            + "  JOIN country co           ON sp.country_id            = co.id "
            + " WHERE (:title           IS NULL OR jp.job_title         LIKE %:title%) "
            + "   AND (:company         IS NULL OR c.name               LIKE %:company%) "
            + "   AND (:workType        IS NULL OR jp.work_type         = :workType) "
            + "   AND (:location        IS NULL OR CONCAT(ci.name, ', ', sp.name, ', ', co.name) LIKE %:location%) "
            + "   AND (:datePosted      IS NULL OR jp.date_posted       >= :datePosted) "
            + "   AND (:postingType     IS NULL OR pt.type              = :postingType) "
            + "   AND (:minSalary       IS NULL OR jp.salary            >= :minSalary) "
            + "   AND (:maxSalary       IS NULL OR jp.salary            <= :maxSalary) "
            + "   AND (:sourceSite      IS NULL OR jp.source_site      = :sourceSite) "
            + "   AND (:experienceLevel IS NULL OR el.level             = :experienceLevel) "
            + "   AND (:roleType        IS NULL OR rt.role_type        = :roleType)",
            nativeQuery = true)
    Page<JobPosting> findJobPostingsByCriteria(
            @Param("title")           String title,
            @Param("company")         String company,
            @Param("workType")        String workType,
            @Param("location")        String location,
            @Param("datePosted")      LocalDate datePosted,
            @Param("postingType")     String postingType,
            @Param("minSalary")       Integer minSalary,
            @Param("maxSalary")       Integer maxSalary,
            @Param("sourceSite")      String sourceSite,
            @Param("experienceLevel") String experienceLevel,
            @Param("roleType")        String roleType,
            Pageable pageable);
}
