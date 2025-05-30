package project.testmaster.backend.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import project.testmaster.backend.dto.ExamInfoDTO;
import project.testmaster.backend.service.ExamService;

/**
 * Controller to manage operations related to exams.
 * Provides endpoints to retrieve exam information.
 */
@RestController
@RequestMapping(path = "/api/exam")
public class ExamController {
    private final Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    /**
     * Retrieves an exam by its ID.
     *
     * @param id the ID of the exam to be retrieved
     * @return a ResponseEntity containing the ExamInfoDTO if the exam is found, or an appropriate HTTP status
     */
    @Operation(summary = "Get exam by id", description = "Get exam by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExamInfoDTO> getExam(
            @Parameter(description = "ID of the exam to be retrieved", in = ParameterIn.PATH) @Valid @PathVariable("id") String id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(ExamInfoDTO
                            .fromEntity(examService
                                    .getExamById(UUID
                                            .fromString(id))));
        } catch (NullPointerException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();

        }
    }
}
