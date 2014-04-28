package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;
import com.excilys.computerdatabase.wrapper.ListWrapper;
import com.excilys.computerdatabase.wrapper.PageWrapper;

@Controller
public class ComputerController {
	/**
	 * 
	 */

	// TODO: front validation for add and edit features
	// TODO: custom error pages
	// TODO: for model objects, switch from DateTime to LocalDate and refactor
	// TODO: implement filter parameters saving after deleting/pagination...
	// TODO: complete internationalization by adapting date validator to each
	// language
	// TODO: solve the "sign in" button text problem in login.jsp

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper cm;
	@Autowired
	private ComputerValidator cv;

	private PageWrapper pw = new PageWrapper();
	private static Logger logger = LoggerFactory
			.getLogger(ComputerController.class);

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public ModelAndView displayList(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "searchBy", required = false, defaultValue = "computer") String searchBy,
			@RequestParam(value = "search", required = false, defaultValue = "") String search,
			@RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
			@RequestParam(value = "way", required = false, defaultValue = "asc") String way) {

		logger.debug("Entering displayList");

		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		List<Computer> computerList = new ArrayList<Computer>();
		ListWrapper wrapper = null;

		pw = PageWrapper.builder().searchBy(searchBy).search(search)
				.orderBy(orderBy).way(way).currentPage(currentPage).build();
		pw.setOffset((pw.getCurrentPage() - 1) * pw.getComputersPerPage());

		wrapper = computerService.retrieveList(pw);
		computerList = wrapper.getComputerList();

		pw.setNumberOfComputers(wrapper.getNumberofComputers());
		pw.setNumberOfPages((int) Math.ceil(pw.getNumberOfComputers()
				/ pw.getComputersPerPage()));

		if (computerList != null) {
			for (Computer c : computerList) {
				if (c.getCompany() != null) {
					Company company = companyService.retrieveById(c
							.getCompany().getId());
					c.setCompany(company);
				}
				ComputerDTO computerDTO = cm.toComputerDTO(c);
				computerDTOList.add(computerDTO);
			}
		}
		ModelAndView model = new ModelAndView();
		model.addObject("PageWrapper", pw);
		model.addObject("computerDTOList", computerDTOList);

		model.setViewName("dashboard");

		logger.debug("Exiting displayList");

		return model;
	}

	@RequestMapping(value = "addComputerForm", method = RequestMethod.GET)
	public ModelAndView addComputerForm(ModelAndView mAndV) {

		List<Company> companyList = companyService.retrieveList();
		ComputerDTO cDTO = new ComputerDTO();

		mAndV.addObject("companyList", companyList);
		mAndV.addObject("computerDTO", cDTO);
		mAndV.setViewName("addComputer");
		return mAndV;
	}

	@RequestMapping(value = "addComputer", method = RequestMethod.POST)
	public String add(@ModelAttribute("computerDTO") @Valid ComputerDTO cDTO,
			BindingResult result) {

		logger.debug("Entering add");

		if (!result.hasErrors()) {
			Computer computer = cm.toComputer(cDTO);
			computerService.create(computer);
			logger.debug("Exiting add");
			return "redirect:/dashboard";

		} else {
			return "redirect:/addComputerForm";
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView mAndV,
			@RequestParam(value = "id", required = true) Long id) {

		List<Company> companyList = companyService.retrieveList();

		Computer computer = computerService.retrieveById(id);
		ComputerDTO computerDTO = cm.toComputerDTO(computer);

		mAndV.addObject("computerDTO", computerDTO);
		mAndV.addObject("companyList", companyList);
		mAndV.setViewName("editComputer");

		return mAndV;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(ModelAndView mAndV,
			@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO,
			BindingResult result) {

		logger.debug("Entering update");

		if (!result.hasErrors()) {
			Computer computer = cm.toComputer(computerDTO);
			computerService.update(computer);
			logger.debug("Exiting update");
			return "redirect:/dashboard";

		} else {
			mAndV.addObject("computerDTO", computerDTO);
			return "redirect:/edit?id=" + computerDTO.getId();
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(
			Model model,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "searchBy", required = false, defaultValue = "computer") String searchBy,
			@RequestParam(value = "search", required = false, defaultValue = "") String search,
			@RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
			@RequestParam(value = "way", required = false, defaultValue = "asc") String way) {

		logger.debug("Entering delete");

		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		List<Computer> computerList = new ArrayList<Computer>();

		ListWrapper wrapper = new ListWrapper();

		pw = PageWrapper.builder().searchBy(searchBy).search(search)
				.orderBy(orderBy).way(way).build();

		Computer computer = computerService.retrieveById(id);
		computerService.delete(computer);

		wrapper = computerService.retrieveList(pw);
		computerList = wrapper.getComputerList();

		for (Computer c : computerList) {
			if (c.getCompany() != null) {
				Company company = companyService.retrieveById(c.getCompany()
						.getId());
				c.setCompany(company);
			}
			ComputerDTO computerDTO = cm.toComputerDTO(c);
			computerDTOList.add(computerDTO);
		}
		model.addAttribute("computerDTOList", computerDTOList);
		logger.debug("Exiting delete");

		return "redirect:/dashboard";
	}

	@RequestMapping(value = "loginForm", method = RequestMethod.GET)
	public String loginForm() {
		System.out.println("affiche cette putain de page de login!!!");
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		System.out.println("affiche cette putain de page de login!!!");
		return "login";
	}
}
