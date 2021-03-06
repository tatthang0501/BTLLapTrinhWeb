package ptit;

import java.io.Serializable;
// import java.util.Date;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Table(name = "tblstudent")
@Entity
public class Student implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    @NotNull
    @Size(min = 10, message = "StudentID must be at least 10 characters long")
    @Column(name = "studentid")
    private String studentID;
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    @Column(name = "studentname")
    private String studentName;
    @NotNull
    @Size(min = 10, message = "IDCardNumber must be at least 10 characters long")
    @Column(name = "idcard")
    private String idCard;
    @Column(name = "dob")
    private String DOB;
    @Column(name = "classroom")
    private String classroom;
    @Column(name = "studentaddress")
    private String studentAddress;

    @OneToOne
    @JoinColumn(name = "roomid")
    private Room room;
}
