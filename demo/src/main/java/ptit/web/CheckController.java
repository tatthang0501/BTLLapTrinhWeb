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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @GetMapping
    public String showManagementPage(){
        return "check.html";
    }

    public int CheckIfCheckMoreThan2Times(int motorbikeid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-MM-dd");
        List<CheckIn> list = (List<CheckIn>) checkinRepo.findCheckInToday(sdf.format(new Date()), motorbikeid);
        int checkInCount = list.size();
        return checkInCount;
    }

    public int getFee(int checkInCount) {
        int fee = 0;
        if (checkInCount > 2) {
            fee = 3000;
        }
        if (checkInCount <= 2) {
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
    public String checkIn(HttpServletRequest request, Model model, Motorbike motorbike) {
        String msg = "";
        HttpSession session = request.getSession();
        try {
            CheckIn checkIn = new CheckIn();
            checkIn.setMotorbike(motorbike);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-MM-dd");
            List<CheckIn> list = (List<CheckIn>) checkinRepo.findCheckInToday(sdf.format(new Date()),
                    motorbike.getId());
            if (list.size() > 0) {
                CheckIn lastCheckIn = list.get(list.size() - 1);
                boolean checkInAble = canCheckIn(lastCheckIn);
                if (checkInAble) {
                    msg = "Checkin thành công";
                    checkinRepo.save(checkIn);
                    session.setAttribute("msg", msg);
                }
                if (checkInAble == false) {
                    msg = "Không thể checkin vì chưa checkout";
                    session.setAttribute("msg", msg);
                    return "redirect:/motorbike/findMotorbike";
                }
            }
            if (list.size() == 0) {
                msg = "Checkin thành công";
                checkinRepo.save(checkIn);
                session.setAttribute("msg", msg);
            }

        } catch (Exception e) {
            return "redirect:/motorbike/findMotorbike";
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

    @PostMapping("/findCheckOut")
    public String findCheckedOutMotorbike(ServletRequest request, Model model, String licensePlates){
        try{
            List<CheckIn> listCheckedIn = (List<CheckIn>) checkinRepo.findAll();
            List<CheckIn> listCheckInContainsLP = new ArrayList<CheckIn>();
            for(CheckIn checkIn :listCheckedIn){
                Motorbike moto = motoRepo.findById(checkIn.getMotorbike().getId()).get();
                checkIn.setMotorbike(moto);
                if(moto.getLicensePlates().contains(licensePlates)){
                    listCheckInContainsLP.add(checkIn);
                }
                
            }
            System.out.println(listCheckInContainsLP.size());

            List<CheckIn> listCheckInCanCheckOut = canCheckOut(listCheckInContainsLP);
            model.addAttribute("listCheckedIn", listCheckInCanCheckOut);
            model.addAttribute("checkedIn", new CheckIn());
        }
        catch(Exception e){
            return "redirect:/check/findCheckOut?error";
        }
        return "findCheckOut";
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model, int id, HttpServletRequest request) {
        try {
            CheckOut checkOut = new CheckOut();
            CheckIn checkIn = checkinRepo.findById(id).get();
            checkOut.setCheckin(checkIn);
            checkOutRepo.save(checkOut);

            int checkInCount = 0;
            checkInCount = CheckIfCheckMoreThan2Times(checkIn.getMotorbike().getId());
            int fee = getFee(checkInCount);

            HttpSession session = request.getSession();

            session.setAttribute("fee", fee);
            session.setAttribute("checkInCount", checkInCount);
        } catch (Exception e) {
            return "redirect:/check/findCheckOut?error";
        }
        return "redirect:/check/getCheckOutFee";
    }

    @GetMapping("/getCheckOutFee")
    public String getCheckOutFee(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int fee = (Integer) session.getAttribute("fee");
        int checkInCount = (Integer) session.getAttribute("checkInCount");
        System.out.println(fee);
        System.out.println(checkInCount);
        model.addAttribute("fee", fee);
        model.addAttribute("count", checkInCount);
        return "getCheckOutFee";
    }
}
