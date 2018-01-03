public class Person {
    private String id;
    private String name;
    private String CPR;
    private int age;

    public Person(String id, String name, String CPR, int age) {
        this.id = id;
        this.name = name;
        this.CPR = CPR;
        this.age = age;
    }

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

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", CPR='" + CPR + '\'' +
                ", age=" + age +
                '}';
    }
}
