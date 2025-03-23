package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Exam;
import project.testmaster.backend.repository.ExamRepository;
// import project.testmaster.backend.repository.ExamResultRepository;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    // @Autowired
    // private ExamResultRepository examResultRepository;

    public Exam getExamById(UUID id) {
        return examRepository.findById(id).orElse(null);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public void deleteExam(UUID id) {
        examRepository.deleteById(id);
    }
}
