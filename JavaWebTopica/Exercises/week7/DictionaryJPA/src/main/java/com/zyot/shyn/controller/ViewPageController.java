package com.zyot.shyn.controller;

import com.zyot.shyn.common.RoleConstant;
import com.zyot.shyn.common.SessionAttr;
import com.zyot.shyn.dao.DictDao;
import com.zyot.shyn.entities.DictEntity;
import com.zyot.shyn.utils.PageRouter;
import com.zyot.shyn.utils.PagingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ViewPageController {
    private static final int PAGE_LIMIT = 5;

    @Autowired
    private DictDao dictDao;

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String defaultTransType = (String) session.getAttribute(SessionAttr.DEFAULT_TYPE_ATTR);
        if (defaultTransType == null) {
            List<String> transTypes = dictDao.getAllTranslationTypes();
            session.setAttribute(SessionAttr.TYPE_LIST_ATTR, transTypes);
            session.setAttribute(SessionAttr.DEFAULT_TYPE_ATTR, transTypes.get(0));
        }
        return PageRouter.INDEX_PAGE;
    }

    @RequestMapping("/search")
    public String search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                         @RequestParam(name = "type", defaultValue = "none") String transType,
                         @RequestParam(name = "pageIndex", defaultValue = "1") String pageIndex,
                         Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (transType.equalsIgnoreCase("none"))
            return PageRouter.VIEW_PAGE;
        if (keyword.equalsIgnoreCase("")) {
            String role = (String) session.getAttribute(SessionAttr.ROLE_ATTR);
            if (!role.equalsIgnoreCase(RoleConstant.ADMIN)) {
                model.addAttribute("warning", "Empty keyword");
                return PageRouter.VIEW_PAGE;
            }
        }
        int page = Integer.parseInt(pageIndex) - 1;
        List<DictEntity> words = dictDao.findWordByKeywordWithPaging(keyword, transType, PAGE_LIMIT, page);
        List<DictEntity> totalWords = dictDao.findWordByKeyword(keyword, transType);
        int numOfResults = totalWords.size();
        model.addAttribute("words", words);
        int numOfPages;
        if (numOfResults % PAGE_LIMIT != 0) {
            numOfPages = (numOfResults / PAGE_LIMIT) + 1;
        } else numOfPages = numOfResults / PAGE_LIMIT;
        session.setAttribute(SessionAttr.DEFAULT_TYPE_ATTR, transType);
        int startPage = PagingUtils.getStartIndex(page + 1);
        int endPage = PagingUtils.getEndIndex(page + 1, numOfPages);
        model.addAttribute("curKeyword", keyword);
        model.addAttribute("curPageIndex", pageIndex);
        model.addAttribute("startIndex", startPage);
        model.addAttribute("endIndex", endPage);
        return PageRouter.VIEW_PAGE;
    }

    @RequestMapping("/edit")
    public String editWord(@RequestParam(name = "wordId") String wordId,
                           Model model) {
        int id = Integer.parseInt(wordId);
        List<String> transTypes = new ArrayList<>();
        DictEntity word = dictDao.findWordById(id);
        transTypes.add(word.getType());
        model.addAttribute("word", word);
        model.addAttribute("transTypes", transTypes);
        model.addAttribute("op", "Edit");
        return PageRouter.DETAIL_PAGE;
    }

    @RequestMapping("/add")
    public String addWord(Model model) {
        model.addAttribute("word", null);
        model.addAttribute("transTypes", Collections.emptyList());
        model.addAttribute("op", "Add");
        return PageRouter.DETAIL_PAGE;
    }

    @RequestMapping("/delete")
    public String deleteWord(@RequestParam(name = "wordId") String wordId) {
        int id = Integer.parseInt(wordId);
        dictDao.deleteWordById(id);
        return PageRouter.VIEW_PAGE;
    }
}
