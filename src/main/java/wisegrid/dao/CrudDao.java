package wisegrid.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import wisegrid.util.MyBatisConnectionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by BONG on 2015-7월-22일 /022.
 */
public class CrudDao {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;


    public CrudDao() {
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    }

    /* 조회 */
    public List<Map<String, String>> crudSelect(Map<String, String> searchMap) {

        List<Map<String, String>> list = null;
        sqlSession = sqlSessionFactory.openSession();

        try {
            list = sqlSession.selectList("com.wisegrid.crudSelect", searchMap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return list;
    }


    /* 저장 */
    public int crudSave(List<Map<String, String>> insertDataList, List<Map<String, String>> updateDataList, List<Map<String, String>> deleteDataList) {

        int count = 0;

        sqlSession = sqlSessionFactory.openSession();

        try {

            for (Map<String, String> map : insertDataList) {
                count += sqlSession.insert("com.wisegrid.crudInsert", map);
            }

            for (Map<String, String> map : updateDataList) {
                count += sqlSession.update("com.wisegrid.crudUpdate", map);
            }

            for (Map<String, String> map : deleteDataList) {
                count += sqlSession.delete("com.wisegrid.crudDelete", map);
            }

            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        return count;
    }
}
