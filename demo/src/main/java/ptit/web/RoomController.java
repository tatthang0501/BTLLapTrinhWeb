package ptit.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import ptit.Room;
import ptit.data.RoomRepository;

@Slf4j
@Controller
@RequestMapping("/QLPT.html")
public class RoomController {
    private final RoomRepository roomRepo;

    @Autowired
    public RoomController(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    // @ModelAttribute
    // public void addIngredientsToModel(Model model) {
    // List<Ingredient> ingredients = (List<Ingredient>) ingredientRepo.findAll();
    // Type[] types = Ingredient.Type.values();
    // for (Type type : types) {
    // model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,
    // type));
    // }
    // }

    @GetMapping
    public String showRoomMgmt(Model model) {
        // model.addAttribute("taco", new Taco());
        return "QLPT";
    }

    @GetMapping("/addRoom")
    public String showAddRoom(ServletRequest request, Model model) {
        model.addAttribute("add", new Room());
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap.containsKey("error")) {
            model.addAttribute("msg", "Có lỗi xảy ra (Trùng thông tin)");
        }
        return "addRoom";
    }

    @GetMapping("/roomSearch")
    public String showRoomSearch(ServletRequest request, Model model) {
        List<Room> rooms = (List<Room>) roomRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", new Room());
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap.containsKey("error")) {
            model.addAttribute("msg", "Có lỗi xảy ra (Phòng có sinh viên đang ở)");
        }
        return "roomSearch";
    }

    @GetMapping("/roomEdit")
    // @RequestMapping("/roomEdit")
    public String showRoomEdit(Model model, @RequestParam(name = "id") String id) {
        Room room = roomRepo.findById(id).get();
        model.addAttribute("room", room);
        return "editRoom";
    }

    @GetMapping("/delete")
    // @RequestMapping("/roomEdit")
    public String deleteRoom(Model model, @RequestParam(name = "id") String id) {
        String msg = "";
        try {
            roomRepo.deleteById(id);
        } catch (Exception e) {
            msg = "Có lỗi xảy ra (phòng đang có sinh viên)";
            List<Room> rooms = (List<Room>) roomRepo.findAll();
            model.addAttribute("rooms", rooms);
            model.addAttribute("search", new Room());
            return "redirect:/QLPT.htmlroomSearch?error";
        }
        List<Room> rooms = (List<Room>) roomRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", new Room());
        return "redirect:/QLPT.html/roomSearch?error";
    }

    @PostMapping("/roomSearch")
    public String processSearch(Room room, Model model) {
        List<Room> allRooms = (List<Room>) roomRepo.findAll();
        List<Room> rooms = new ArrayList<>();
        for (Room r : allRooms) {
            if (r.getDongia() <= room.getDongia()) {
                rooms.add(r);
            }
        }
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", new Room());
        return "roomSearch";
    }

    @PostMapping("/roomEdit")
    public String processEdit(Room room, Model model) {
        Room roomB4 = roomRepo.findById(room.getId()).get();
        roomB4.setDongia(room.getDongia());
        roomB4.setLoaiphong(room.getLoaiphong());
        roomB4.setSonguoi(room.getSonguoi());
        roomB4.setSophong(room.getSophong());
        roomRepo.save(roomB4);

        List<Room> allRooms = (List<Room>) roomRepo.findAll();
        List<Room> rooms = new ArrayList<>();
        for (Room r : allRooms) {
            if (r.getDongia() <= room.getDongia()) {
                rooms.add(r);
            }
        }
        return "redirect:/QLPT.html/roomSearch";
    }

    @PostMapping("/addRoom")
    public String processAdd(Room room) {
        try {
            roomRepo.save(room);
        } catch (Exception e) {
            return "redirect:/QLPT.html/addRoom?error";
        }
        return "redirect:/QLPT.html/roomSearch";
    }

}
