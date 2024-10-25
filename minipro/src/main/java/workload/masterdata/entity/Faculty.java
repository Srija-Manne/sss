package workload.masterdata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") // Explicitly setting the column name to uppercase
    private Long id;
    @Column(name = "NAME") // Faculty Name in uppercase
    private String name;
    
    @Column(name = "DESIG") // Designation in uppercase
    private String desig;
    
    @Column(name = "MONDAY_M") // Morning session for Monday in uppercase
    private String MONDAYM;
    
    @Column(name = "MONDAY_A") // Afternoon session for Monday in uppercase
    private String MONDAYA;
    
    @Column(name = "TUESDAY_M")
    private String TUESDAYM;
    
    @Column(name = "TUESDAY_A")
    private String TUESDAYA;
    
    @Column(name = "WEDNESDAY_M")
    private String WEDNESDAYM;
    
    @Column(name = "WEDNESDAY_A")
    private String WEDNESDAYA;
    
    @Column(name = "THURSDAY_M")
    private String THURSDAYM;
    
    @Column(name = "THURSDAY_A")
    private String THURSDAYA;
    
    @Column(name = "FRIDAY_M")
    private String FRIDAYM;
    
    @Column(name = "FRIDAY_A")
    private String FRIDAYA;
    
    @Column(name = "SATURDAY_M")
    private String SATURDAYM;
    
    @Column(name = "SATURDAY_A")
    private String SATURDAYA;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public String getMONDAYM() {
        return MONDAYM;
    }

    public void setMONDAYM(String MONDAYM) {
        this.MONDAYM = MONDAYM;
    }

    public String getMONDAYA() {
        return MONDAYA;
    }

    public void setMONDAYA(String MONDAYA) {
        this.MONDAYA = MONDAYA;
    }

    public String getTUESDAYM() {
        return TUESDAYM;
    }

    public void setTUESDAYM(String TUESDAYM) {
        this.TUESDAYM = TUESDAYM;
    }

    public String getTUESDAYA() {
        return TUESDAYA;
    }

    public void setTUESDAYA(String TUESDAYA) {
        this.TUESDAYA = TUESDAYA;
    }

    public String getWEDNESDAYM() {
        return WEDNESDAYM;
    }	

    public void setWEDNESDAYM(String WEDNESDAYM) {
        this.WEDNESDAYM = WEDNESDAYM;
    }

    public String getWEDNESDAYA() {
        return WEDNESDAYA;
    }

    public void setWEDNESDAYA(String WEDNESDAYA) {
        this.WEDNESDAYA = WEDNESDAYA;
    }

    public String getTHURSDAYM() {
        return THURSDAYM;
    }

    public void setTHURSDAYM(String THURSDAYM) {
        this.THURSDAYM = THURSDAYM;
    }

    public String getTHURSDAYA() {
        return THURSDAYA;
    }

    public void setTHURSDAYA(String THURSDAYA) {
        this.THURSDAYA = THURSDAYA;
    }

    public String getFRIDAYM() {
        return FRIDAYM;
    }

    public void setFRIDAYM(String FRIDAYM) {
        this.FRIDAYM = FRIDAYM;
    }

    public String getFRIDAYA() {
        return FRIDAYA;
    }

    public void setFRIDAYA(String FRIDAYA) {
        this.FRIDAYA = FRIDAYA;
    }

    public String getSATURDAYM() {
        return SATURDAYM;
    }

    public void setSATURDAYM(String SATURDAYM) {
        this.SATURDAYM = SATURDAYM;
    }

    public String getSATURDAYA() {
        return SATURDAYA;
    }

    public void setSATURDAYA(String SATURDAYA) {
        this.SATURDAYA = SATURDAYA;
    }
}
