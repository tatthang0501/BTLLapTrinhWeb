package ptit;

import lombok.Data;

@Data
public class CheckedInMotorbike {
    private int id;
    private Motorbike motorbike;
    private String thoigian;
    private int fee;
}
