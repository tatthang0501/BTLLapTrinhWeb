package ptit.web;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javassist.Loader.Simple;
import ptit.CheckIn;
import ptit.Motorbike;
import ptit.data.CheckInRepository;
import ptit.data.MotorbikeRepository;

@Controller
@RequestMapping("/check")
public class CheckController {
    private final CheckInRepository checkinRepo;
    private final MotorbikeRepository motoRepo;

    CheckController(CheckInRepository checkinRepo, MotorbikeRepository motoRepo){
        this.checkinRepo = checkinRepo;
        this.motoRepo = motoRepo;
    }

    public boolean CheckIfCheckMoreThan2Times(int id){
        boolean check = false;
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<CheckIn> list = (List<CheckIn>) checkinRepo.findCheckInToday(dtf.format(today), id);
        int checkInCount = list.size();
        if(checkInCount > 2){
            check = true;
        }
        return check;
    }

    public int getFee(boolean check){
        int fee = 0;
        if(check == true){
            fee = 3000;
        }
        if(check == false){
            fee = 0;
        }
        return fee;
    }

    @GetMapping("/checkIn")
    public String checkIn(ServletRequest request, Model model, Motorbike motorbike){
        try{
            CheckIn checkIn = new CheckIn();
            checkIn.setMotorbike(motorbike);
            checkinRepo.save(checkIn);
        }
        catch(Exception e){
            return "redirect:/motorbike/findMotorbike?error";
        }
        return "redirect:/check/findCheckOut";
    }

    @GetMapping("/findCheckOut")
    public String checkOut(ServletRequest request, Model model, Motorbike motorbike){
        List<CheckIn> listCheckedIn = (List<CheckIn>) checkinRepo.findAll();
        for(CheckIn checkedIn: listCheckedIn){
            Motorbike moto = motoRepo.findById(checkedIn.getMotorbike().getId()).get();
            checkedIn.setMotorbike(moto);
        }

        model.addAttribute("listCheckedIn",listCheckedIn);
        model.addAttribute("checkedIn", new CheckIn());
        return "redirect:/motorbike/findMotorbike";
    }

    @PostMapping
    public String checkOutProcess(ServletRequest request, Model model, CheckIn checkedIn){
        
        return "redirect:/motorbike/findCheckOut";
    }
}
