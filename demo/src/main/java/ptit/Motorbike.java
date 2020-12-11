package ptit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "tblmotorbike")
public class Motorbike {
    @Id
    private int id;
    @NotNull
    @Column(name = "motorbikename")
    private String motorbikeName;
    @NotNull
    @Column(name = "price")
    private float price;
    @NotNull
    @Column(name = "licenseplates")
    private String licensePlates;
    @NotNull
    @OneToOne
    @JoinColumn(name = "studentid")
    private Student student;
}
