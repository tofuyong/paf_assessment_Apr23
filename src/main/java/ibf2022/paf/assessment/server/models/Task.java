package ibf2022.paf.assessment.server.models;

// TODO: Task 4

public class Task {

    private String username;
    private String description;
	private int priority;
	private String dueDate;

    public String getUsername() {return this.username;}
    public void setUsername(String username) {this.username = username;}

    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}

    public int getPriority() {return this.priority;}
    public void setPriority(int priority) {this.priority = priority;}

    public String getDueDate() {return this.dueDate;}
    public void setDueDate(String dueDate) {this.dueDate = dueDate;}
    
    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", description='" + getDescription() + "'" +
            ", priority='" + getPriority() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
   
}
