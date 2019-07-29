package com.zyot.shyn.dao;

import com.zyot.shyn.entities.DictEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Primary
public class DictDaoImpl implements DictDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<DictEntity> findWordByKeyword(String keyword, String transType) {
        List<DictEntity> words;
        Session session = sessionFactory.getCurrentSession();
        String findWordQuery = "FROM DictEntity as d WHERE d.word LIKE :keyword AND d.type = :transType";
        Query<DictEntity> query = session.createQuery(findWordQuery, DictEntity.class)
                .setParameter("keyword", String.format("%s%%", keyword))
                .setParameter("transType", transType);
        words = query.getResultList();
        return words;
    }

    @Override
    @Transactional
    public List<String> getAllTranslationTypes() {
        List<String> transTypes;
        Session session = sessionFactory.getCurrentSession();
        String getAllTransTypeQuery = "SELECT DISTINCT d.type FROM DictEntity as d";
        Query<String> query = session.createQuery(getAllTransTypeQuery, String.class);
        transTypes = query.getResultList();
        return transTypes;
    }

    @Override
    @Transactional
    public List<DictEntity> findWordByKeywordWithPaging(String keyword, String transType, int pageLimit, int pageIndex) {
        List<DictEntity> words;
        Session session = sessionFactory.getCurrentSession();
        String findWordQuery = "FROM DictEntity as d WHERE d.word LIKE :keyword AND d.type = :transType";
        Query<DictEntity> query = session.createQuery(findWordQuery, DictEntity.class)
                .setParameter("keyword", String.format("%s%%", keyword))
                .setParameter("transType", transType)
                .setFirstResult(pageLimit * pageIndex)
                .setMaxResults(pageLimit);
        words = query.list();
        return words;
    }

    @Override
    @Transactional
    public void deleteWordById(int id) {
        Session session = sessionFactory.getCurrentSession();
        String findWordQuery = "DELETE DictEntity as d WHERE d.id = :id";
        Query query = session.createQuery(findWordQuery)
                .setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void addWord(String word, String meaning, String type) {
        Session session = sessionFactory.getCurrentSession();
        String findWordQuery = "INSERT INTO dict (word, meaning, type) VALUES (:word, :meaning, :type)";
        NativeQuery query = session.createSQLQuery(findWordQuery);
        query.setParameter("word", word);
        query.setParameter("meaning", meaning);
        query.setParameter("type", type);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateWordMeaning(int id, String meaning) {
        Session session = sessionFactory.getCurrentSession();
        String findWordQuery = "UPDATE DictEntity as d SET d.meaning = :meaning WHERE d.id = :id";
        Query query = session.createQuery(findWordQuery)
                .setParameter("meaning", meaning)
                .setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public List<DictEntity> findWordById(int id) {
        Session session = sessionFactory.getCurrentSession();
        String findWordQuery = "FROM DictEntity as d WHERE d.id = :id";
        Query<DictEntity> query = session.createQuery(findWordQuery, DictEntity.class)
                .setParameter("id", id);
        return query.getResultList();
    }
}
