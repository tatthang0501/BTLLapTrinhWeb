package ptit.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.MonthlyTicket;
import ptit.Motorbike;
import ptit.Student;
import ptit.data.MonthlyTicketRepository;
import ptit.data.MotorbikeRepository;
import ptit.data.StudentRepository;

@Controller
@RequestMapping("/ticket")
public class MonthLyTicketController {
    private final MonthlyTicketRepository ticketRepo;
    private final StudentRepository stuRepo;
    private final MotorbikeRepository motoRepo;
    MonthLyTicketController(MonthlyTicketRepository ticketRepo, StudentRepository stuRepo, MotorbikeRepository motoRepo){
        this.ticketRepo = ticketRepo;
        this.motoRepo = motoRepo;
        this.stuRepo = stuRepo;
    }

    @GetMapping
    public String showManagementPage(){
        return "QLVX.html";
    }

    @GetMapping("/addMonthlyTicket")
    public String addMonthlyTicket(ServletRequest request, Model model){
        List<Student> listStudent = (List<Student>) stuRepo.findAll();
        List<Motorbike> listMoto = (List<Motorbike>) motoRepo.findAll();
        model.addAttribute("ticket", new MonthlyTicket());
        model.addAttribute("students", listStudent);
        model.addAttribute("motorbikes", listMoto);
        model.addAttribute("student", new Student());
        model.addAttribute("motorbike", new Motorbike());
        LocalDate date = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM");
        model.addAttribute("month", Integer.parseInt(dtf.format(date)));
        return "addMonthlyTicket";
    }

    @PostMapping("/addMonthlyTicket")
    public String addMonthlyTicketProcess(ServletRequest request, Model model, MonthlyTicket ticket){
        try{
            String sinhvienid = request.getParameter("sinhvienid");
            String thang = request.getParameter("thang");
            String xeid = request.getParameter("xeid");
            ticket.setSinhvienid(Integer.parseInt(sinhvienid));
            ticket.setThang(Integer.parseInt(thang));
            ticket.setXeid(Integer.parseInt(xeid));
            ticketRepo.save(ticket);
        }
        catch(Exception ex){
            return "redirect:/addMonthlyTicket?error";
        }
        return "redirect:/ticket/findMonthlyTicket";
    }

    @GetMapping("/findMonthlyTicket")
    public String findTicket(Model model){
        try{

        }
        catch(Exception ex){
            return "redirect:/findMonthlyTicket?error";
        }
        return "findMonthlyTicket";
    }
}
