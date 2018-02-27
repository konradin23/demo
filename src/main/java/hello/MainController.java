package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/demo")
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String helloForm() {
		return "form";
	}

	// @RequestMapping(value="/hello", method=RequestMethod.POST)
	// public String hello(HttpServletRequest request, Model model) {
	// String name=request.getParameter("name");
	// String email=request.getParameter("email");
	//
	//
	// model.addAttribute("name", name);
	// model.addAttribute("email", email);
	//
	// return "hello";
	// }

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "userslist";
	}

	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	public ModelAndView deleteUser(@RequestParam("user_id") long id) {//nazwa w RequestParam ma być taka sama jak w html form name
		userRepository.delete(id);
		return new ModelAndView("redirect:/demo/all");
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createUser(@RequestParam("name") String name, @RequestParam("email") String email) {
		userRepository.save(new User(name, email));
		return new ModelAndView("redirect:/demo/all");
	}

	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editPage(@PathVariable long id, Model model) {
		User user=userRepository.findOne(id);
		model.addAttribute("user", user);
		return "edituser";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView editUser(@RequestParam("user_id") long id,
								@RequestParam("name") String name,
								@RequestParam("email") String email) {
		User user = userRepository.findOne(id);
		user.setName(name);
		user.setEmail(email);
		userRepository.save(user);
		return new ModelAndView("redirect:/demo/all");
	}
}
