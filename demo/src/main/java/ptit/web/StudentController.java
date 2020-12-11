
package ptit.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.MonthlyTicket;
import ptit.Room;
import ptit.Student;
import ptit.data.MonthlyTicketRepository;
import ptit.data.MotorbikeRepository;
import ptit.data.RoomRepository;
import ptit.data.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository stuRepo;
    private final RoomRepository roomRepo;
    private final MonthlyTicketRepository ticketRepo;
    private final MotorbikeRepository motoRepo;

    public StudentController(StudentRepository stuRepo, RoomRepository roomRepo, MonthlyTicketRepository ticketRepo, MotorbikeRepository motoRepo) {
        this.stuRepo = stuRepo;
        this.roomRepo = roomRepo;
        this.ticketRepo = ticketRepo;
        this.motoRepo = motoRepo;
    }

    @GetMapping
    public String openQLSV() {
        return "QLSV.html";
    }

    @GetMapping("/addStudent")
    public String addSV(ServletRequest request, Model model) {
        model.addAttribute("add", new Student());
        List<Room> rooms = (List<Room>) roomRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("room", new Room());
        return "addStudent";
    }

    @PostMapping("/addStudent")
    public String addStudentProcess(Student student, ServletRequest request) {
        try {
            String phongid = request.getParameter("phongid");
            Room room = new Room();
            room.setId(phongid);
            student.setRoom(room);
            stuRepo.save(student);
        } catch (Exception e) {
            return "redirect:/student/addStudent?error";
        }
        return "redirect:/student/studentFound";
    }

    @GetMapping("/studentFound")
    public String findStudent(ServletRequest request, Model model, String studentName) {

        model.addAttribute("student", new Student());
        List<Student> listStudent = (List<Student>) stuRepo.findAll();
        for (Student s : listStudent) {
            Room room = roomRepo.findById(s.getRoom().getId()).get();
            s.setRoom(room);
        }
        model.addAttribute("students", listStudent);
        return "studentFound";
    }

    @PostMapping("/studentFound")
    public String findStudentProcess(String studentName, Model model) {
        try {
            List<Student> listAllStudent = (List<Student>) stuRepo.findAll();
            List<Student> listStudent = new ArrayList<Student>();
            for (Student s : listAllStudent) {
                if (s.getStudentName().contains(studentName)) {
                    listStudent.add(s);
                }
                model.addAttribute("students", listStudent);
                model.addAttribute("student", new Student());
                model.addAttribute("studentName", studentName);
            }
        } catch (Exception e) {
            return "redirect:/studentFound?error";
        }
        return "studentFound";
    }

    @GetMapping("/editStudent")
    public String editStudent(ServletRequest request, ServletResponse response, Student student, Model model) {
        Student temp = stuRepo.findById(student.getId()).get();
        List<Room> listRoom = (List<Room>) roomRepo.findAll();
        model.addAttribute("student", temp);
        model.addAttribute("room", new Room());
        model.addAttribute("rooms", listRoom);
        return "editStudent";
    }

    @PostMapping("/editStudent")
    public String editStudentProcess(ServletRequest request, ServletResponse response, Student student) {
        try {
            String phongid = request.getParameter("phongid");
            Room room = roomRepo.findById(phongid).get();
            student.setRoom(room);
            stuRepo.save(student);
        } catch (Exception e) {
            return "redirect:/student/editStudent?error";
        }
        return "redirect:/student/studentFound";
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(ServletRequest request, Student student) {
        try {
            System.out.println(student.getId());
            ticketRepo.deleteByStudentId(student.getId());
            motoRepo.deleteByStudentId(student.getId());
            stuRepo.delete(student);
        } catch (Exception e) {
            return "redirect:/student/deleteStudent?error";
        }
        return "redirect:/student/studentFound";
    }
}
