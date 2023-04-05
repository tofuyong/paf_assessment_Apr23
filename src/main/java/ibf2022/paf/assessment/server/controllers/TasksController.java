package ibf2022.paf.assessment.server.controllers;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.exception.InsertTaskException;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonWriter;

// TODO: Task 4, Task 8

@RestController
@RequestMapping
public class TasksController {

    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);

    @Autowired
    TodoService toDoSvc;

    @PostMapping(path="/task", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView saveTask(@RequestBody MultiValueMap<String, String> form) throws InsertTaskException {
        
        // Convert payload to Json
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (String key : form.keySet()) {
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (String value : form.get(key)) {
                jsonArrayBuilder.add(value);
            }
            jsonObjectBuilder.add(key, jsonArrayBuilder);
        }
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stringWriter);
        jsonWriter.writeObject(jsonObjectBuilder.build());
        String json = stringWriter.toString();
        logger.info(">>>> Json created: " + json);

        // Create tasks from Json and add them to a list
        List<Task> tasks = new ArrayList<>();
        String username = null;
        for (String key : form.keySet()) {
            if (key.equals("username")) {
                username = form.getFirst(key);
            } else {
                int taskIndex = Integer.parseInt(key.substring(key.indexOf("-") + 1));
                if (tasks.size() <= taskIndex) {
                    tasks.add(new Task());
                }
                Task task = tasks.get(taskIndex);
                if (key.startsWith("description")) {
                    task.setDescription(form.getFirst(key));
                } else if (key.startsWith("priority")) {
                    task.setPriority(Integer.parseInt(form.getFirst(key)));
                } else if (key.startsWith("dueDate")) {
                    task.setDueDate(form.getFirst(key));
                }
            }
        }
        int taskCount = tasks.size();
        for (Task task : tasks) {
            task.setUsername(username);
        }

        toDoSvc.upsertTask(tasks, username);
        logger.info(">>>> Username: " + username);
        logger.info(">>>> List of Tasks: " + tasks.toString());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result.html");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("username", username);
        modelAndView.addObject("taskCount", taskCount);
       
        return modelAndView;
    }
}



