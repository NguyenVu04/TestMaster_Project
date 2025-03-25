package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.repository.ExamRepository;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public Exam getExamById(UUID id) {
        return examRepository.findById(id).orElse(null);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public void deleteExam(UUID id) {
        examRepository.deleteById(id);
    }

    public Exam updateExam(Exam exam) {
        if (examRepository.existsById(exam.getId())) {
            return examRepository.save(exam);
        } else {
            throw new EntityNotFoundException("Exam not found");
        }
    }
}
