package pl.speedlog.investments.add.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.speedlog.investments.add.application.AddToWallet;
import pl.speedlog.investments.add.application.AddToWalletUsecase;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Controller
@RequiredArgsConstructor
public class AddController {

    private static final String ADD_TO_WALLET_PARAM = "addToWallet";

    private final AddToWalletUsecase addToWalletUsecase;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute(ADD_TO_WALLET_PARAM, new AddToWallet());
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute AddToWallet addToWallet, Model model) {
        try {
            addToWalletUsecase.addToWallet(addToWallet);
            model.addAttribute(ADD_TO_WALLET_PARAM, new AddToWallet());
            model.addAttribute("success", true);
        } catch (Exception exception) {
            model.addAttribute(ADD_TO_WALLET_PARAM, addToWallet);
            model.addAttribute("error", exception.getMessage());
        }
        return "add";
    }


}
