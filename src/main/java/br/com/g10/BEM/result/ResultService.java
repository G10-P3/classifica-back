package br.com.g10.BEM.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;


    public ResultModel createResult(ResultModel result) {
        return resultRepository.save(result);
    }


    public List<ResultModel> getAllResults() {
        return resultRepository.findAll();
    }


    public ResultModel getResultById(UUID id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resultado não encontrado com ID " + id));
    }


    public ResultModel updateResult(UUID id, ResultModel resultDetails) {
        ResultModel result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resultado não encontrado com ID " + id));

        result.setMathScore(resultDetails.getMathScore());
        result.setPortugueseScore(resultDetails.getPortugueseScore());
        result.setTotalScore(resultDetails.getTotalScore());
        result.setType(resultDetails.isType());
        result.setExam(resultDetails.getExam());
        result.setStudent(resultDetails.getStudent());

        return resultRepository.save(result);
    }


    public void deleteResult(UUID id) {
        if (!resultRepository.existsById(id)) {
            throw new EntityNotFoundException("Resultado não encontrado com ID " + id);
        }
        resultRepository.deleteById(id);
    }
}
