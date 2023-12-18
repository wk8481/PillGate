package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.repo.careGiverRepo.CareGiverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class
CaregiverServiceImpl implements CareGiverService {

    private Logger logger = LoggerFactory.getLogger(CaregiverServiceImpl.class);


    private CareGiverRepository careGiverRepository;

    @Autowired
    public CaregiverServiceImpl(CareGiverRepository careGiverRepository) {
        this.careGiverRepository = careGiverRepository;
    }


    @Override
    public List<CareGiver> getCaregivers() {
        return careGiverRepository.findAllCareGivers();
    }

    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        return careGiverRepository.createCareGiver(careGiver);
    }

}
