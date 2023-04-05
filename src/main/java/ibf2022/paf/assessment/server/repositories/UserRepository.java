package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.Utils;
import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String GET_USER_BY_USERNAME_SQL = "select * from user where username = ?";
    private final String INSERT_USER_SQL = "insert into user (user_id, username) values (?, ?)";

    // #1 Find User by Username
    public Optional<User> getUserByUsername(String username) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_USER_BY_USERNAME_SQL, username);
        if (!rs.next()) {
            return Optional.empty();
        } return Optional.of(Utils.toUser(rs)); 
    }

    // #2 Create User
    public String createUser(String username) {
        String userid = UUID.randomUUID().toString().substring(0, 8);
        System.out.println(">>>>>> Generated userid: " + userid); // testing
        Integer iResult = jdbcTemplate.update(INSERT_USER_SQL, userid, username);
        return iResult > 0 ? userid : "failed to create new User";
    }

}




    // public Optional<User> getUserByUsername(String username) {
    //     User user = null;
    //     user = jdbcTemplate.queryForObject(GET_USER_BY_USERNAME_SQL, BeanPropertyRowMapper.newInstance(User.class), username);
    //     return Optional.of(user);
    // }