package com.zyot.shyn.dao;

import com.zyot.shyn.entities.DictEntity;

import java.util.List;

public interface DictDao {
    List<DictEntity> findWordByKeyword(String keyword, String transType);

    List<String> getAllTranslationTypes();

    List<DictEntity> findWordByKeywordWithPaging(String keyword, String transType, int pageLimit, int pageIndex);

    void deleteWordById(int id);

    void addWord(String word, String meaning, String type);

    void updateWordMeaning(int id, String meaning);

    List<DictEntity> findWordById(int id);
}
