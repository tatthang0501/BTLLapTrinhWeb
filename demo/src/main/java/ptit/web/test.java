package ptit.web;

import java.util.ArrayList;

import org.hibernate.annotations.SourceType;
import org.springframework.stereotype.Controller;

import ptit.Motorbike;
import ptit.data.MotorbikeRepository;

@Controller
public class test {
    private static MotorbikeRepository motoRepoo;

    test(MotorbikeRepository motoRepoo) {
        test.motoRepoo = motoRepoo;
    }

    public static void main(String[] args) {

        ArrayList<Motorbike> listMotor = (ArrayList<Motorbike>) motoRepoo.findAll();
        for(Motorbike motor : listMotor){
            System.out.println(motor.getId());
            // System.out.println(motor.getStudent().getId());
        }
    }
}
