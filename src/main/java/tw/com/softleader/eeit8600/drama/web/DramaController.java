package tw.com.softleader.eeit8600.drama.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.com.softleader.eeit8600.drama.entity.Drama;
import tw.com.softleader.eeit8600.drama.service.DramaService;

@Controller
@RequestMapping("/dramas")
public class DramaController {
	@Autowired
	private DramaService dramaService;

	// http://**.**.**/dramas/list
	@RequestMapping("/list")
	public String listPage(Model model) {
		// prefix + string + suffix;
		// "/WEB-INF/jsp/drama/dramaList.jsp
		model.addAttribute("dramas", dramaService.getAll());
		return "/drama/dramaList";
	}

	@RequestMapping("/add")
	public String addPage() {
		return "/drama/dramaAdd";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@RequestParam String name, @RequestParam String episodes, @RequestParam String actor,
			@RequestParam String genre, Model model, RedirectAttributes attr) {

		String[] data = { name, episodes, actor, genre };
		Map<String, String> errorMsg = validateData(data);
		if (!errorMsg.isEmpty()) {
			model.addAttribute("errorMsg", errorMsg);		
			model.addAttribute("insertMsg","新增失敗,請依錯誤訊息更正輸入值");
			return "forward:/dramas/add";
		}
		Drama drama = new Drama(name, Integer.valueOf(episodes), actor, genre);
		dramaService.insert(drama);
		
		attr.addFlashAttribute("insertMsg","新增成功");
		attr.addFlashAttribute("drama",drama);
		return "redirect:/dramas/add";

	}

	@RequestMapping("/edit")
	public String editPage(@RequestParam Long id, Model model) {
		model.addAttribute("drama", dramaService.getById(id));
		return "/drama/dramaEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@RequestParam Long id, @RequestParam String name, @RequestParam String episodes,
			@RequestParam String actor, @RequestParam String genre, Model model ,RedirectAttributes attr
			) {

		String[] data = { name, episodes, actor, genre };
		Map<String, String> errorMsg = validateData(data);
		if (!errorMsg.isEmpty()) {
			model.addAttribute("errorMsg", errorMsg);
			model.addAttribute("updateMsg","更新失敗,請依錯誤訊息更正輸入值");
			return "forward:/dramas/edit?id="+id;
		}
		Drama drama = new Drama();
		drama.setId(id);
		drama.setName(name);
		drama.setEpisodes(Integer.valueOf(episodes));
		drama.setActor(actor);
		drama.setGenre(genre);
		dramaService.update(drama);
		
		attr.addAttribute("id", drama.getId());
		attr.addFlashAttribute("updateMsg","更新成功");
		attr.addFlashAttribute("drama", drama);
		
		return "redirect:/dramas/edit";
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam Long id) {
		dramaService.delete(id);
		return "redirect:/dramas/list";
	}
	
	@RequestMapping("/findname")
	public String findByName(@RequestParam String inputbox, Model model){		
		Set<Drama> dramas = dramaService.getByName(inputbox);
		if(dramas.isEmpty()){
			model.addAttribute("findMsg","目前尚無符合資料");
			return "/drama/dramaList";
		}else{
			model.addAttribute("dramas", dramas);
			return "/drama/dramaList";
		}
	}
	
	@RequestMapping("/findactor")
	public String findByActor(@RequestParam String inputbox,Model model){
		Set<Drama> dramas=dramaService.getByActor(inputbox);
		if(dramas.isEmpty()){
			model.addAttribute("findMsg","目前尚無符合資料");
			return "/drama/dramaList";
		}else{
			model.addAttribute("dramas",dramas);
			return "/drama/dramaList";
		}			
	}
	
//	@RequestMapping(value = "/findgenre" , method=RequestMethod.POST)
//	public String findByGenre(@RequestParam String genresel,Model model){
//		model.addAttribute("dramas",dramaService.getByGenre(genresel));
//		return "/drama/dramaList";
//	}

	public Map<String, String> validateData(String[] data) {
		Map<String, String> errorMsg = new HashMap<String, String>();

		if (data[0] == null || data[0].trim().length() == 0) {
			errorMsg.put("name", "請輸入劇名");
		}
		if (data[1] == null || data[1].trim().length() == 0) {
			errorMsg.put("episodes", "請輸入集數");
		} else {
			try {
				int epis = Integer.parseInt(data[1].trim());
			} catch (NumberFormatException e) {
				errorMsg.put("episodes", "集數必須為數字");
			}
		}
		if (data[2] == null || data[2].trim().length() == 0) {
			errorMsg.put("actor", "請輸入主演");
		}
		if (data[3] == null || data[3].trim().length() == 0) {
			errorMsg.put("genre", "請輸入類型");
		} 
		return errorMsg;
	}

}
