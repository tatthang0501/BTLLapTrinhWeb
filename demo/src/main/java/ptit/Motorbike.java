package ptit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="tblXe")
public class Motorbike {
    @Id
    private int id;
    @NotNull
    private String loaixe;
    @NotNull
    private float giatri;
    @NotNull
    private String bienso;
    @NotNull
    private String sinhvienid;
}
