package com.zyot.shyn.controller;

import com.zyot.shyn.dao.DictDao;
import com.zyot.shyn.entities.DictEntity;
import com.zyot.shyn.utils.PageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DetailPageController {
    @Autowired
    private DictDao dictDao;

    @RequestMapping("/update")
    public String updateWord(@RequestParam(name = "id", defaultValue = "-1") String wordId,
                             @RequestParam(name = "word") String word,
                             @RequestParam(name = "meaning") String meaning,
                             @RequestParam(name = "type") String type,
                             Model model) {
        boolean isValid = true;
        int id = Integer.parseInt(wordId);
        String op;
        if (id == -1)
            op = "Add";
        else op = "Edit";
        if (word.equalsIgnoreCase("") || meaning.equalsIgnoreCase("")) {
            model.addAttribute("warning", "PLEASE fill in all the blanks");
            model.addAttribute("op", op);
            isValid = false;
        } else {
            List<DictEntity> words = dictDao.findWordByWordMeanType(word, meaning, type);
            if (!words.isEmpty()) {
                model.addAttribute("warning", "This word existed!");
                model.addAttribute("op", op);
                isValid = false;
            }
        }
        if (!isValid) {
            if (op.equalsIgnoreCase("Edit")) {
                DictEntity curWord = new DictEntity();
                curWord.setId(id);
                curWord.setWord(word);
                curWord.setMeaning(meaning);
                curWord.setType(type);
                model.addAttribute("word", curWord);
            }
            return PageRouter.DETAIL_PAGE;
        }

        if (id == -1) {
            // Add a new word
            DictEntity newWord = new DictEntity();
            newWord.setWord(word);
            newWord.setMeaning(meaning);
            newWord.setType(type);
            dictDao.addWord(newWord);
        } else {
            // Update a word
            dictDao.updateWordMeaning(id, meaning);
        }
        return PageRouter.VIEW_PAGE;
    }
}
