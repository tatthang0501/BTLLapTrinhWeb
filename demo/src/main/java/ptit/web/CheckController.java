package ptit.web;

import java.security.Timestamp;
import java.sql.SQLException;
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
import ptit.CheckOut;
import ptit.Motorbike;
import ptit.data.CheckInRepository;
import ptit.data.CheckOutRepository;
import ptit.data.MotorbikeRepository;

@Controller
@RequestMapping("/check")
public class CheckController {
    private final CheckInRepository checkinRepo;
    private final MotorbikeRepository motoRepo;
    private final CheckOutRepository checkOutRepo;

    CheckController(CheckInRepository checkinRepo, MotorbikeRepository motoRepo, CheckOutRepository checkOutRepo) {
        this.checkinRepo = checkinRepo;
        this.motoRepo = motoRepo;
        this.checkOutRepo = checkOutRepo;
    }

    public boolean CheckIfCheckMoreThan2Times(int motorbikeid) {
        boolean rs = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-MM-dd");
        List<CheckIn> list = (List<CheckIn>) checkinRepo.findCheckInToday(sdf.format(new Date()), motorbikeid);
        System.out.println(list.size());
        int checkInCount = list.size();
        if (checkInCount > 2) {
            rs = true;
        }
        return rs;
    }

    public int getFee(boolean check) {
        int fee = 0;
        if (check == true) {
            fee = 3000;
        }
        if (check == false) {
            fee = 0;
        }
        return fee;
    }

    public List<CheckIn> canCheckOut(List<CheckIn> list) {
        List<CheckIn> listCheckInCanCheckOut = new ArrayList<CheckIn>();
        for (CheckIn checkIn : list) {
            List<CheckOut> listCheckOut = (List<CheckOut>) checkOutRepo.findCheckOut(checkIn.getId());
            if (listCheckOut.size() == 0) {
                listCheckInCanCheckOut.add(checkIn);
            }
        }
        return listCheckInCanCheckOut;
    }

    public boolean canCheckIn(CheckIn checkIn) {
        boolean rs = true;
        List<CheckOut> listCheckOut = checkOutRepo.findCheckOut(checkIn.getId());
        if (listCheckOut.size() == 1) {
            rs = true;
        } else {
            rs = false;
        }
        return rs;
    }

    @GetMapping("/checkIn")
    public String checkIn(ServletRequest request, Model model, Motorbike motorbike) {
        String msg = "";
        try {
            CheckIn checkIn = new CheckIn();
            checkIn.setMotorbike(motorbike);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-MM-dd");
            List<CheckIn> list = (List<CheckIn>) checkinRepo.findCheckInToday(sdf.format(new Date()), motorbike.getId());
            if (list.size() > 0) {
                CheckIn lastCheckIn = list.get(list.size() - 1);
                boolean checkInAble = canCheckIn(lastCheckIn);
                if (checkInAble) {
                    msg = "Checkin thành công";
                    checkinRepo.save(checkIn);
                    model.addAttribute("msg", msg);
                }
                if (checkInAble == false) {
                    msg = "Không thể checkin vì chưa checkout";
                    model.addAttribute("msg", msg);
                    return "redirect:/check/findCheckOut";
                }
            }
            if (list.size() == 0) {
                msg = "Checkin thành công";
                checkinRepo.save(checkIn);
                model.addAttribute("msg", msg);
            }

        } catch (Exception e) {
            return "redirect:/motorbike/findMotorbike?error";
        }
        return "redirect:/check/findCheckOut";
    }

    @GetMapping("/findCheckOut")
    public String findCheckOut(ServletRequest request, Model model, Motorbike motorbike) {
        List<CheckIn> listCheckedIn = (List<CheckIn>) checkinRepo.findAll();
        for (CheckIn checkedIn : listCheckedIn) {
            Motorbike moto = motoRepo.findById(checkedIn.getMotorbike().getId()).get();
            checkedIn.setMotorbike(moto);
        }
        List<CheckIn> listCheckInCanCheckOut = canCheckOut(listCheckedIn);
        model.addAttribute("listCheckedIn", listCheckInCanCheckOut);
        model.addAttribute("checkedIn", new CheckIn());
        // return "redirect:/motorbike/findMotorbike";
        return "findCheckOut";
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model, int id) {
        try {
            CheckOut checkOut = new CheckOut();
            CheckIn checkIn = checkinRepo.findById(id).get();
            checkOut.setCheckin(checkIn);
            checkOutRepo.save(checkOut);
            
            int checkInCount = 0;
            boolean rs = CheckIfCheckMoreThan2Times(checkIn.getMotorbike().getId(), checkInCount);
            int fee = getFee(rs);
            System.out.println(checkInCount);
            System.out.println(fee);
            model.addAttribute("fee", fee);
            model.addAttribute("count", checkInCount);
        } catch (Exception e) {
            return "redirect:/check/findCheckOut?error";
        }
        return "redirect:/check/getCheckOutFee";
    }

    @GetMapping("/getCheckOutFee")
    public String getCheckOutFee(Model model){
    
        return "getCheckOutFee";
    }
}
