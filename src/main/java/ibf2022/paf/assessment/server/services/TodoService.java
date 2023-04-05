package ibf2022.paf.assessment.server.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.exception.InsertTaskException;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7

@Service
public class TodoService {

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    @Transactional (rollbackFor = InsertTaskException.class)
    public void upsertTask(List<Task> tasks, String username) throws InsertTaskException {
        Boolean bProceed = false; 

        // 1. Check if user exists
        Optional<User> user = userRepo.getUserByUsername(username);
        if (user.isPresent()) {
            bProceed = true;
        }

        // 2. If user does not exist, create a new user
        if (!user.isPresent()) {
            User newUser = new User();
            String userid = UUID.randomUUID().toString().substring(0, 8);
            newUser.setName(username);
            newUser.setUserId(userid);
            newUser.setUsername(username);
            userRepo.createUser(newUser);
            bProceed = true;
        }

        // 3. Perform inserts for every Task in List
        if (bProceed) {
            for (Task task : tasks) {
                int iInserted = taskRepo.insertTask(task, username);
                if (iInserted < 1) {
                    throw new InsertTaskException();
                }
            }
        }
    }
}

