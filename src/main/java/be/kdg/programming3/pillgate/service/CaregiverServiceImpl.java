package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.repo.careGiverRepo.CareGiverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing CareGivers.
 * This service provides methods to retrieve and manipulate CareGiver entities.
 *
 * @author Team PillGate
 */
@Service
public class CaregiverServiceImpl implements CareGiverService {

    private Logger logger = LoggerFactory.getLogger(CaregiverServiceImpl.class);

    private CareGiverRepository careGiverRepository;

    /**
     * Constructs a new instance of CaregiverServiceImpl.
     *
     * @param careGiverRepository The repository for accessing CareGiver data.
     */
    @Autowired
    public CaregiverServiceImpl(CareGiverRepository careGiverRepository) {
        this.careGiverRepository = careGiverRepository;
    }

    /**
     * Retrieves a list of all CareGivers.
     *
     * @return A list of CareGiver entities.
     */
    @Override
    public List<CareGiver> getCaregivers() {
        return careGiverRepository.findAllCareGivers();
    }

    /**
     * Creates a new CareGiver.
     *
     * @param careGiver The CareGiver entity to be created.
     * @return The created CareGiver entity.
     */
    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        return careGiverRepository.createCareGiver(careGiver);
    }
}
