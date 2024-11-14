package kinostat.controller;

import kinostat.entity.Category;
import kinostat.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String category(Model model) {
        model.addAttribute("category", new Category());
        return "category";
    }

    @PostMapping
    public String addCategory(@ModelAttribute Category category) {
        categoryService.save(category);

        return "category";
    }
}
