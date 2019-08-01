package com.zyot.shyn.dao;

import com.zyot.shyn.entities.DictEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Primary
public class DictDaoImpl implements DictDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<DictEntity> findWordByKeyword(String keyword, String transType) {
        List<DictEntity> words;
        String findWordQuery = "SELECT d FROM DictEntity as d WHERE d.word LIKE :keyword AND d.type = :transType";
        TypedQuery<DictEntity> query = entityManager.createQuery(findWordQuery, DictEntity.class)
                .setParameter("keyword", String.format("%s%%", keyword))
                .setParameter("transType", transType);
        words = query.getResultList();
        return words;
    }

    @Override
    @Transactional
    public List<String> getAllTranslationTypes() {
        List<String> transTypes;
        String getAllTransTypeQuery = "SELECT DISTINCT d.type FROM DictEntity as d";
        TypedQuery<String> query = entityManager.createQuery(getAllTransTypeQuery, String.class);
        transTypes = query.getResultList();
        return transTypes;
    }

    @Override
    @Transactional
    public List<DictEntity> findWordByKeywordWithPaging(String keyword, String transType, int pageLimit, int pageIndex) {
        List<DictEntity> words;
        String findWordQuery = "SELECT d FROM DictEntity as d WHERE d.word LIKE :keyword AND d.type = :transType";
        TypedQuery<DictEntity> query = entityManager.createQuery(findWordQuery, DictEntity.class)
                .setParameter("keyword", String.format("%s%%", keyword))
                .setParameter("transType", transType)
                .setFirstResult(pageLimit * pageIndex)
                .setMaxResults(pageLimit);
        words = query.getResultList();
        return words;
    }

    @Override
    @Transactional
    public void deleteWordById(int id) {
        DictEntity word = entityManager.find(DictEntity.class, id);
        if (word != null)
            entityManager.remove(word);
    }

    @Override
    @Transactional
    public void addWord(DictEntity word) {
        entityManager.persist(word);
    }

    @Override
    @Transactional
    public void updateWordMeaning(int id, String meaning) {
        DictEntity word = entityManager.find(DictEntity.class, id);
        word.setMeaning(meaning);
    }

    @Override
    @Transactional
    public DictEntity findWordById(int id) {
        return entityManager.find(DictEntity.class, id);
    }

    @Override
    public List<DictEntity> findWordByWordMeanType(String word, String meaning, String transType) {
        List<DictEntity> words;
        String findWordQuery = "SELECT d FROM DictEntity as d WHERE d.word= :word AND d.type = :transType AND d.meaning = :meaning";
        TypedQuery<DictEntity> query = entityManager.createQuery(findWordQuery, DictEntity.class)
                .setParameter("word", word)
                .setParameter("transType", transType)
                .setParameter("meaning", meaning);
        words = query.getResultList();
        return words;
    }
}
