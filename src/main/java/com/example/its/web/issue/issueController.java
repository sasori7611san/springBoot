package com.example.its.web.issue;

import com.example.its.domain.issue.issueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class issueController {

    private final issueService issueServ;

    @GetMapping
    public String showList(Model model) {
        model.addAttribute("issueList", issueServ.findAll());
        return "issues/list";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute IssueForm form) {
        return "issues/creationForm";
    }

    //POST /issues
    @PostMapping
    public String create(@Validated IssueForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        }
        issueServ.create(form.getSummary(), form.getDescription());
        return "redirect:/issues";
    }
    // GET localhost:8080/issues/1
    @GetMapping("/{issueId}")
    public String showDetail(@PathVariable("issueId") long issueId, Model model) {
        model.addAttribute("issue", issueServ.findById(issueId));
        return "issues/detail";
    }
}

