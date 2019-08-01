package com.zyot.shyn.dao;

import com.zyot.shyn.entities.DictEntity;

import java.util.List;

public interface DictDao {
    List<DictEntity> findWordByKeyword(String keyword, String transType);

    List<String> getAllTranslationTypes();

    List<DictEntity> findWordByKeywordWithPaging(String keyword, String transType, int pageLimit, int pageIndex);

    void deleteWordById(int id);

    void addWord(DictEntity word);

    void updateWordMeaning(int id, String meaning);

    DictEntity findWordById(int id);

    List<DictEntity> findWordByWordMeanType(String word, String meaning, String type);
}
