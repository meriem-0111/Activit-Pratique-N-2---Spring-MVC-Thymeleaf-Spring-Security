package com.example.bdcc_enset_spring_mvc.web;

import com.example.bdcc_enset_spring_mvc.entities.Product;
import com.example.bdcc_enset_spring_mvc.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller

public class ProductConroller {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/index")
    public String index(Model model) {
       List<Product> products = productRepository.findAll();
       model.addAttribute("productList", products);
        return "products";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        productRepository.deleteById(id);
        return "redirect:/index";
    }

    @PostMapping("/admin/saveProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveProduct(@Valid Product product,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "new-product";
        }
        productRepository.save(product);
        return "redirect:/user/index";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping("/user/findProduct")
    public String findProduct(@RequestParam(name = "search") String name, Model model) {
        System.out.println(productRepository.findByName(name));
        model.addAttribute("product", productRepository.findByName(name));
        return "find-product";
    }

    @GetMapping("/admin/viewEditProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String vieweditProduct(@RequestParam(name = "id") Long id,
                                  Model model) {
        Optional<Product> productOpt = productRepository.findById(id);

        Product product = productOpt.get();
        model.addAttribute("product", product);

        return "view-product";
    }

    @PostMapping("/admin/editProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String editProduct(@Valid Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            productRepository.save(existingProduct);
        }
        return "redirect:/user/index";
    }

}
