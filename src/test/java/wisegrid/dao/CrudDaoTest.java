package wisegrid.dao;

import junit.framework.TestCase;
import org.apache.ibatis.session.SqlSession;
import wisegrid.util.MyBatisConnectionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BONG on 2015-7¿ù-21ÀÏ /021.
 */
public class CrudDaoTest extends TestCase {

    SqlSession sqlSession;

    public void testCrudSelect() throws Exception {

        List<Map<String, String>> list = null;
        sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("fromDate", "20150701");
        map.put("toDate", "20150730");
        map.put("itemFlag","ALL");
        map.put("itemName","E6700");

        try {
            System.out.println(map);
            list = sqlSession.selectList("com.wisegrid.crudSelect", map);
            System.out.println(list.size());

//            List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
//            for(int i=0; i<1; i++) {
//                list2.add(list.get(i));
//            }
//
//            System.out.println(list.get(0));
//            System.out.println(sqlSession.insert("com.wisegrid.crudInsert", list));
//            System.out.println(sqlSession.insert("com.wisegrid.crudDelete", list));
//            System.out.println(sqlSession.insert("com.wisegrid.crudUpdate", list));
//
//            System.out.println(list2.get(0));
//            System.out.println(sqlSession.insert("com.wisegrid.crudDelete", list2));
//            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }


    }
}