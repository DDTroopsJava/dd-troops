package cz.fi.muni.pa165.ddtroops.sampledata;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.transaction.Transactional;

import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by pstanko.
 * @author pstanko
 */
@Component
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);


    @Autowired
    private UserService userService;

    private void loadUsers() throws IOException
    {
        User chuck = user(
            "heslo",
            "Chuck",
            "chuck@example.com", "12345", toDate(1999, 5,4), true);
        User superman = user(
            "heslo",
            "Superman",
            "superman@example.com",
            "564321",
            toDate(2099, 5,21),
            false);
        User bill = user(
            "heslo",
            "Bill",
            "bill@microsoft.com",
            "00000",
            toDate(1991, 1,1),
            false
        );
        log.info("Loaded eShop users.");
    }

    @Override
    public void loadData() throws IOException {
        loadUsers();
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private User user(String password, String name, String email, String phone, Date joined, boolean admin) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPhone(phone);
        u.setJoinedDate(joined);
        u.setAdmin(admin);
        userService.register(u, password);
        return u;
    }
}
