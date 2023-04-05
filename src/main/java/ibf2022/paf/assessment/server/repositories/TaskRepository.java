package ibf2022.paf.assessment.server.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6

@Repository
public class TaskRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private final String INSERT_TASK_SQL = "insert into task (description, priority, due_date) values (?, ?, ?)";

    public Integer insertTask(Task task) {
        // LocalDate dueDate = LocalDate.parse(task.getDueDate());
        // Date sqlDueDate = Date.valueOf(dueDate);

        Integer iResult = jdbcTemplate.update(INSERT_TASK_SQL, task.getDescription(), task.getPriority(), task.getDueDate()); 
        return iResult;
    }

}



// Batch update 
// public int[] batchUpdate(List<Task> task) {
//     return jdbcTemplate.batchUpdate(INSERT_TASK_SQL, new BatchPreparedStatementSetter() {
        
//         // Sets values
//         public void setValues(PreparedStatement ps, int i) throws SQLException {
//             ps.setString(1, task.get(i).getUsername());
//             ps.setString(2, task.get(i).getDescription());
//             ps.setInt(3, task.get(i).getPriority());
//             ps.setDate(4, java.sql.Date.valueOf(task.get(i).getDueDate()));
//         }

//         // Returns size of update
//         public int getBatchSize() {
//             return task.size();
//         }
//     });    
// }