package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.facade.TroopFacade;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.TroopService;
import java.util.List;
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

    @Override
    public TroopDTO findById(Long troopId) {
        Troop troop = troopService.findById(troopId);
        return (troop == null) ? null : beanMappingService.mapTo(troop, TroopDTO.class);
    }

    @Override
    public TroopDTO findByName(String name) {
        Troop troop = troopService.findByName(name);
        return (troop == null) ? null : beanMappingService.mapTo(troop, TroopDTO.class);
    }

    @Override
    public List<TroopDTO> findAll() {
        return beanMappingService.mapTo(troopService.findAll(), TroopDTO.class);
    }

    @Override
    public TroopDTO update(TroopDTO troopDTO) {
        Troop troopEntity = beanMappingService.mapTo(troopDTO, Troop.class);
        troopService.update(troopEntity);
        troopDTO.setId(troopEntity.getId());
        return troopDTO;
    }

    @Override
    public void delete(TroopDTO troopDTO) {
        Troop troopEntity = beanMappingService.mapTo(troopDTO, Troop.class);
        troopService.delete(troopEntity);
    }
    
    
}
