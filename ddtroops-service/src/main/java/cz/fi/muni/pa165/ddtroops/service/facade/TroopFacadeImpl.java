package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.facade.TroopFacade;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.TroopService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author xgono
 */
@Service
@Transactional
public class TroopFacadeImpl implements TroopFacade {
    
    @Autowired
    private TroopService troopService;
    
    @Autowired
    private BeanMappingService beanMappingService;
    
    private final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class.getName());

    @Override
    public TroopDTO findById(Long troopId) {
        Troop troop = null;
        try {
            troop = troopService.findById(troopId);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (troop == null) ? null : beanMappingService.mapTo(troop, TroopDTO.class);
    }

    @Override
    public TroopDTO findByName(String name) {
        Troop troop = null;
        try {
            troop = troopService.findByName(name);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (troop == null) ? null : beanMappingService.mapTo(troop, TroopDTO.class);
    }

    @Override
    public List<TroopDTO> findAll() {
        try {
            return beanMappingService.mapTo(troopService.findAll(), TroopDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public TroopDTO update(TroopDTO troopDTO) {
        Troop troopEntity = beanMappingService.mapTo(troopDTO, Troop.class);
        try {
            troopService.update(troopEntity);
            troopDTO.setId(troopEntity.getId());
            return troopDTO;
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void delete(TroopDTO troopDTO) {
        Troop troopEntity = beanMappingService.mapTo(troopDTO, Troop.class);
        try {
            troopService.delete(troopEntity);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
    }
    
    
}
