package ptit.web;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.SecretKeyCallback.Request;
import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.Motorbike;
import ptit.Student;
import ptit.data.MotorbikeRepository;
import ptit.data.StudentRepository;

@Controller
@RequestMapping("/motorbike")
public class MotorbikeController {
    private final MotorbikeRepository motoRepo;
    private final StudentRepository stuRepo;
    MotorbikeController(MotorbikeRepository motoRepo, StudentRepository stuRepo){
        this.motoRepo = motoRepo;
        this.stuRepo = stuRepo;
    }

    @GetMapping
    public String showManagerPage(){
        return "QLXE.html";
    }

    @GetMapping("/addMotorbike")
    public String addMotorbike(ServletRequest request, Model model){
        
        List<Student> listStudent = (List<Student>) stuRepo.findAll();
        model.addAttribute("students", listStudent);
        model.addAttribute("student", new Student());
        return "addMotorbike";
    }

    @PostMapping("/addMotorbike")
    public String addMotorbikeProcess(ServletRequest request, Model model, Motorbike motorbike){
        try{
            String sinhvienid = request.getParameter("sinhvienid");
            motorbike.setSinhvienid(sinhvienid);
            motoRepo.save(motorbike);
        }
        catch(Exception e){
            return "redirect:/addMotorbike?error";
        }
        return "redirect:/motorbike/findMotorbike";
    }

    @GetMapping("/findMotorbike")
    public String findMotorbike(ServletRequest request, Model model){
        try{
            List<Motorbike> listMoto = (List<Motorbike>) motoRepo.findAll();
            for(Motorbike moto: listMoto){
                Student stu = stuRepo.findById(Integer.parseInt(moto.getSinhvienid())).get();
                String studentName = stu.getStudentName();
                moto.setSinhvienid(studentName);
            }
            model.addAttribute("motos", listMoto);
            model.addAttribute("moto", new Motorbike());
        }
        catch(Exception e){
            return "redirect:/findMotorbike?error";
        }
        return "findMotorbike";
    }

    @PostMapping("/findMotorbike")
    public String findMotorbikeProcess(ServletRequest request, Model model, String loaixe){
        try{
            List<Motorbike> listALlMoto = (List<Motorbike>) motoRepo.findAll();
            List<Motorbike> listMoto = new ArrayList<Motorbike>();
            for(Motorbike moto: listALlMoto){
                if(moto.getLoaixe().contains(loaixe)){
                    listMoto.add(moto);
                }
            }

            for(Motorbike moto: listMoto){
                Student stu = stuRepo.findById(Integer.parseInt(moto.getSinhvienid())).get();
                String studentName = stu.getStudentName();
                moto.setSinhvienid(studentName);
            }
            model.addAttribute("motos", listMoto);
            model.addAttribute("moto", new Motorbike());
        }
        catch(Exception e){
            return "redirect:/findMotorbike?error";
        }
        return "findMotorbike";
    }
}
