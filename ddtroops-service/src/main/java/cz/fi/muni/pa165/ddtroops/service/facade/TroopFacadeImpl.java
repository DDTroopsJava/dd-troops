package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.facade.TroopFacade;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.TroopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xgono
 */
@Service
@Transactional
public class TroopFacadeImpl implements TroopFacade {

    private final Logger logger = LoggerFactory.getLogger(TroopFacadeImpl.class.getName());
    @Autowired
    private TroopService troopService;
    @Autowired
    private BeanMappingService beanMappingService;

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
            Troop t = troopService.update(troopEntity);
            troopDTO.setId(troopEntity.getId());
            return beanMappingService.mapTo(t, TroopDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id");
        }

        Troop troopEntity = beanMappingService.mapTo(findById(id), Troop.class);

        try {
            troopService.delete(troopEntity);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    @Override
    public TroopDTO battle(TroopDTO troop1DTO, TroopDTO troop2DTO)  {
        Troop t1 = beanMappingService.mapTo(troop1DTO, Troop.class);
        Troop t2 = beanMappingService.mapTo(troop2DTO, Troop.class);
        try {
            Troop troopWinner = troopService.battle(t1,t2);
            return beanMappingService.mapTo(troopWinner,TroopDTO.class);
        }  catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<TroopDTO> topN(int n, String mission, Long troopSize) {
        try {
            List<Troop> topNTroops = troopService.topN(n,mission,troopSize);
            return beanMappingService.mapTo(topNTroops,TroopDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }
}
