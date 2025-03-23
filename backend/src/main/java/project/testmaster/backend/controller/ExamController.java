package project.testmaster.backend.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import project.testmaster.backend.dto.ExamInfoDTO;
import project.testmaster.backend.service.ExamService;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    private final Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    @Operation(summary = "Get exam by id", description = "Get exam by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExamInfoDTO> getExam(
            @Parameter(description = "ID of the exam to be retrieved") @Valid @PathParam("id") String id) {
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
