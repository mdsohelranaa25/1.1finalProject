public class Course {
    private String id;
    private String name;
    private String code;
    private String instructor;
    private int totalClasses;
    
    public Course(String id, String name, String code, String instructor) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.instructor = instructor;
        this.totalClasses = 0;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public int getTotalClasses() {
        return totalClasses;
    }
    
    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
    
    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}