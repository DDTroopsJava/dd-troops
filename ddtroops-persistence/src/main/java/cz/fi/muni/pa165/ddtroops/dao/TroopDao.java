package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Troop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by xgono
 *
 * @author xgono
 */
public interface TroopDao extends JpaRepository<Troop, Long> {

    /**
     * Returns the troop from DB with the given name
     *
     * @param name - name of the troop to be retrieved from DB
     * @return troop with given name if it exists, null if not
     */
    Troop findByName(String name);

    List<Troop> findByMission(String name);
}
