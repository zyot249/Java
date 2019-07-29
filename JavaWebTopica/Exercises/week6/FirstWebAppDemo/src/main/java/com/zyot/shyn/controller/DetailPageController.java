package com.zyot.shyn.controller;

import com.zyot.shyn.dao.DictDao;
import com.zyot.shyn.utils.PageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailPageController {
    @Autowired
    private DictDao dictDao;

    @RequestMapping("/update")
    public String updateWord(@RequestParam(name = "id", defaultValue = "-1") String wordId,
                             @RequestParam(name = "word") String word,
                             @RequestParam(name = "meaning") String meaning,
                             @RequestParam(name = "type") String type) {

        int id = Integer.parseInt(wordId);
        if (id == -1) {
            // Add a new word
            dictDao.addWord(word, meaning, type);
        } else {
            // Update a word
            dictDao.updateWordMeaning(id, meaning);
        }
        return PageRouter.VIEW_PAGE;
    }
}
