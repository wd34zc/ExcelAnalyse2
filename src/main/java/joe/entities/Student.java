package joe.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GenericGenerator(name = "NativeIdGenerator",strategy = "native")
    @GeneratedValue(generator = "NativeIdGenerator")
    @Column
    private Integer id;

    @Column
    private String  StudentId;

    @Column
    private String name;

    @Column
    private Double chinese;

    @Column
    private Integer chineseClassRanking;

    @Column
    private Integer chineseGradeRanking;

    @Column
    private Double maths;

    @Column
    private Integer mathsClassRanking;

    @Column
    private Integer mathsGradeRanking;

    @Column
    private Double english;

    @Column
    private Integer englishClassRanking;

    @Column
    private Integer englishGradeRanking;

    @Column
    private Double physics;

    @Column
    private Integer physicsClassRanking;

    @Column
    private Integer physicsGradeRanking;

    @Column
    private Double chemistry;

    @Column
    private Integer chemistryClassRanking;

    @Column
    private Integer chemistryGradeRanking;

    @Column
    private Double biology;

    @Column
    private Integer biologyClassRanking;

    @Column
    private Integer biologyGradeRanking;

    @Column
    private Double sum;

    @Column
    private Integer sumClassRanking;

    @Column
    private Integer sumGradeRanking;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getChinese() {
        return chinese;
    }

    public void setChinese(Double chinese) {
        this.chinese = chinese;
    }

    public Integer getChineseClassRanking() {
        return chineseClassRanking;
    }

    public void setChineseClassRanking(Integer chineseClassRanking) {
        this.chineseClassRanking = chineseClassRanking;
    }

    public Integer getChineseGradeRanking() {
        return chineseGradeRanking;
    }

    public void setChineseGradeRanking(Integer chineseGradeRanking) {
        this.chineseGradeRanking = chineseGradeRanking;
    }

    public Double getMaths() {
        return maths;
    }

    public void setMaths(Double maths) {
        this.maths = maths;
    }

    public Integer getMathsClassRanking() {
        return mathsClassRanking;
    }

    public void setMathsClassRanking(Integer mathsClassRanking) {
        this.mathsClassRanking = mathsClassRanking;
    }

    public Integer getMathsGradeRanking() {
        return mathsGradeRanking;
    }

    public void setMathsGradeRanking(Integer mathsGradeRanking) {
        this.mathsGradeRanking = mathsGradeRanking;
    }

    public Double getEnglish() {
        return english;
    }

    public void setEnglish(Double english) {
        this.english = english;
    }

    public Integer getEnglishClassRanking() {
        return englishClassRanking;
    }

    public void setEnglishClassRanking(Integer englishClassRanking) {
        this.englishClassRanking = englishClassRanking;
    }

    public Integer getEnglishGradeRanking() {
        return englishGradeRanking;
    }

    public void setEnglishGradeRanking(Integer englishGradeRanking) {
        this.englishGradeRanking = englishGradeRanking;
    }

    public Double getPhysics() {
        return physics;
    }

    public void setPhysics(Double physics) {
        this.physics = physics;
    }

    public Integer getPhysicsClassRanking() {
        return physicsClassRanking;
    }

    public void setPhysicsClassRanking(Integer physicsClassRanking) {
        this.physicsClassRanking = physicsClassRanking;
    }

    public Integer getPhysicsGradeRanking() {
        return physicsGradeRanking;
    }

    public void setPhysicsGradeRanking(Integer physicsGradeRanking) {
        this.physicsGradeRanking = physicsGradeRanking;
    }

    public Double getChemistry() {
        return chemistry;
    }

    public void setChemistry(Double chemistry) {
        this.chemistry = chemistry;
    }

    public Integer getChemistryClassRanking() {
        return chemistryClassRanking;
    }

    public void setChemistryClassRanking(Integer chemistryClassRanking) {
        this.chemistryClassRanking = chemistryClassRanking;
    }

    public Integer getChemistryGradeRanking() {
        return chemistryGradeRanking;
    }

    public void setChemistryGradeRanking(Integer chemistryGradeRanking) {
        this.chemistryGradeRanking = chemistryGradeRanking;
    }

    public Double getBiology() {
        return biology;
    }

    public void setBiology(Double biology) {
        this.biology = biology;
    }

    public Integer getBiologyClassRanking() {
        return biologyClassRanking;
    }

    public void setBiologyClassRanking(Integer biologyClassRanking) {
        this.biologyClassRanking = biologyClassRanking;
    }

    public Integer getBiologyGradeRanking() {
        return biologyGradeRanking;
    }

    public void setBiologyGradeRanking(Integer biologyGradeRanking) {
        this.biologyGradeRanking = biologyGradeRanking;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Integer getSumClassRanking() {
        return sumClassRanking;
    }

    public void setSumClassRanking(Integer sumClassRanking) {
        this.sumClassRanking = sumClassRanking;
    }

    public Integer getSumGradeRanking() {
        return sumGradeRanking;
    }

    public void setSumGradeRanking(Integer sumGradeRanking) {
        this.sumGradeRanking = sumGradeRanking;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", StudentId='" + StudentId + '\'' +
                ", name='" + name + '\'' +
                ", chinese=" + chinese +
                ", chineseClassRanking=" + chineseClassRanking +
                ", chineseGradeRanking=" + chineseGradeRanking +
                ", maths=" + maths +
                ", mathsClassRanking=" + mathsClassRanking +
                ", mathsGradeRanking=" + mathsGradeRanking +
                ", english=" + english +
                ", englishClassRanking=" + englishClassRanking +
                ", englishGradeRanking=" + englishGradeRanking +
                ", physics=" + physics +
                ", physicsClassRanking=" + physicsClassRanking +
                ", physicsGradeRanking=" + physicsGradeRanking +
                ", chemistry=" + chemistry +
                ", chemistryClassRanking=" + chemistryClassRanking +
                ", chemistryGradeRanking=" + chemistryGradeRanking +
                ", biology=" + biology +
                ", biologyClassRanking=" + biologyClassRanking +
                ", biologyGradeRanking=" + biologyGradeRanking +
                ", sum=" + sum +
                ", sumClassRanking=" + sumClassRanking +
                ", sumGradeRanking=" + sumGradeRanking +
                '}';
    }
}
