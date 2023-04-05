package ibf2022.paf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6

@Repository
public class TaskRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private final String INSERT_TASK_SQL = "insert into task (description, priority, due_date, username) values (?, ?, ?, ?)";

    public Integer insertTask(Task task, String username) {
        Integer iResult = jdbcTemplate.update(INSERT_TASK_SQL, task.getDescription(), task.getPriority(), task.getDueDate(), username); 
        return iResult;
    }

}

