package ibf2022.paf.assessment.server;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import ibf2022.paf.assessment.server.models.User;

public class Utils {
    
    public static User toUser(SqlRowSet rs) {
		User user = new User();
        user.setUserId(rs.getString("user_id"));
		user.setUsername(rs.getString("username"));
		user.setName(rs.getString("name"));
		return user;
	}
}
