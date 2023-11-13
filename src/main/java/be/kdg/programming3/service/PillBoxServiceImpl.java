package be.kdg.programming3.service;

import be.kdg.programming3.domain.pillbox.PillBox;
import be.kdg.programming3.repository.PillBoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PillBoxServiceImpl implements PillBoxService {

    private Logger logger = LoggerFactory.getLogger(PillBoxService.class);

    private PillBoxRepository pillBoxRepository;

    public PillBoxServiceImpl(PillBoxRepository pillBoxRepository) {
        this.pillBoxRepository = pillBoxRepository;
    }

    @Override
    public List<PillBox> getPillBox() {
        logger.info("Getting pillboxes");
        return pillBoxRepository.readPillBoxes();
    }

}
