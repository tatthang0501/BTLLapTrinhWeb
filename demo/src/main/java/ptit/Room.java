package ptit;

import java.util.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tblroom")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "roomnumber")
    private String roomNumber;
    @NotNull
    @Column(name = "type")
    private String type;
    @NotNull
    private float price;
    @NotNull
    @Column(name = "amountpeople")
    private int amountPeople;
}