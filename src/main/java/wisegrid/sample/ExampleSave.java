package wisegrid.sample;


import wisegrid.dao.CrudDao;
import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExampleSave extends HttpServlet {

    private static final long serialVersionUID = -6769648928176737604L;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        GridData gdReq = null;
        GridData gdRes = null;

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=utf-8");

        PrintWriter out = res.getWriter();

        try {

            String rawData = req.getParameter("WISEGRID_DATA");

            gdReq = OperateGridData.parse(rawData);

            String mode = gdReq.getParam("mode");

            if ("search".equals(mode)) {
                // 검색
                gdRes = doQuery(gdReq);
            } else if ("save".equals(mode)) {
                gdRes = doSave(gdReq);
            }

            gdRes.setStatus("true");
        } catch (Exception e) {
            gdRes = new GridData();

            try {
                gdRes.addParam("DOQUERY_SAVESTATUS_ROLLBACK", "false");
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            gdRes.setMessage("Error : " + e.getMessage());
            gdRes.setStatus("false");
            e.printStackTrace();
        } finally {
            try {
                OperateGridData.write(gdRes, out);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 조회
     */
    public GridData doQuery(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();
        int rowCount = 0;

        try {

            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            String fromDate = gdReq.getParam("from_date");
            String toDate = gdReq.getParam("to_date");
            String itemFlag = gdReq.getParam("item_flag");
            String itemName = gdReq.getParam("item_name");

            Map<String, String> searchMap = new HashMap<String, String>();
            searchMap.put("fromDate", fromDate);
            searchMap.put("toDate", toDate);
            searchMap.put("itemFlag",itemFlag);
            searchMap.put("itemName",itemName);

            CrudDao dao = new CrudDao();
            List<Map<String, String>> list = dao.crudSelect(searchMap);

            rowCount = list.size();

            if (rowCount == 0) {
                gdRes.addParam("mode", "search");
                gdRes.setMessage("조회결과가 없습니다.");
                gdRes.setStatus("true");
                return gdRes;
            }

            for(Map<String, String> data: list) {

                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("SEQ_NO").addValue(data.get("SEQ_NO"), "");
                gdRes.getHeader("ITEM_FLAG").addSelectedHiddenValue(data.get("ITEM_FLAG"));
                gdRes.getHeader("VENDOR_NAME").addValue(data.get("VENDOR_NAME"), "");
                gdRes.getHeader("ITEM_CODE").addValue(data.get("ITEM_CODE"), "", 0);
                gdRes.getHeader("ITEM_NAME").addValue(data.get("ITEM_NAME"), "");
                gdRes.getHeader("SPECIFICATION").addValue(data.get("SPECIFICATION"), "");
                gdRes.getHeader("UNIT").addSelectedHiddenValue(data.get("UNIT"));
                gdRes.getHeader("PRICE").addValue(String.valueOf(data.get("PRICE")), "");
                gdRes.getHeader("STOCK").addValue(String.valueOf(data.get("STOCK")), "");
                gdRes.getHeader("ADD_DATE").addValue(data.get("ADD_DATE"), "");
                gdRes.getHeader("CHANGE_DATE").addValue(data.get("CHANGE_DATE"), "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        gdRes.addParam("mode", "search");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");
        return gdRes;
    }

    /**
     * 저장
     */
    public GridData doSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        rowCount = gdReq.getHeader("CRUD").getRowCount();

        List<Map<String, String>> insertDataList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> updateDataList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> deleteDataList = new ArrayList<Map<String, String>>();

        for (int i = 0; i < rowCount; i++) {

            //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
            String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

            if (crud.equals("C")) {

                Map<String, String> map = new HashMap<String, String>();

                 map.put("ITEM_FLAG", gdReq.getHeader("ITEM_FLAG").getComboHiddenValues()[gdReq.getHeader("ITEM_FLAG").getSelectedIndex(i)]);
                 map.put("VENDOR_NAME", gdReq.getHeader("VENDOR_NAME").getValue(i));
                 map.put("ITEM_CODE", gdReq.getHeader("ITEM_CODE").getValue(i));
                 map.put("ITEM_NAME", gdReq.getHeader("ITEM_NAME").getValue(i));
                 map.put("SPECIFICATION", gdReq.getHeader("SPECIFICATION").getValue(i));
                 map.put("UNIT", gdReq.getHeader("UNIT").getComboHiddenValues()[gdReq.getHeader("UNIT").getSelectedIndex(i)]);
                 map.put("PRICE", gdReq.getHeader("PRICE").getValue(i));
                 map.put("STOCK", gdReq.getHeader("STOCK").getValue(i));
                insertDataList.add(map);

            } else if (crud.equals("U")) {

                Map<String, String> map = new HashMap<String, String>();

                map.put("SEQ_NO", gdReq.getHeader("SEQ_NO").getValue(i));
                map.put("ITEM_FLAG", gdReq.getHeader("ITEM_FLAG").getComboHiddenValues()[gdReq.getHeader("ITEM_FLAG").getSelectedIndex(i)]);
                map.put("VENDOR_NAME", gdReq.getHeader("VENDOR_NAME").getValue(i));
                map.put("ITEM_CODE", gdReq.getHeader("ITEM_CODE").getValue(i));
                map.put("ITEM_NAME", gdReq.getHeader("ITEM_NAME").getValue(i));
                map.put("SPECIFICATION", gdReq.getHeader("SPECIFICATION").getValue(i));
                map.put("UNIT", gdReq.getHeader("UNIT").getComboHiddenValues()[gdReq.getHeader("UNIT").getSelectedIndex(i)]);
                map.put("PRICE", gdReq.getHeader("PRICE").getValue(i));
                map.put("STOCK", gdReq.getHeader("STOCK").getValue(i));
                updateDataList.add(map);

            } else if (crud.equals("D")) {

                Map<String, String> map = new HashMap<String, String>();
                map.put("SEQ_NO", gdReq.getHeader("SEQ_NO").getValue(i));
                deleteDataList.add(map);

            }
        }


        CrudDao dao = new CrudDao();
        dao.crudSave(insertDataList, updateDataList, deleteDataList);
        gdRes.addParam("mode", "save");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");
        return gdRes;
    }
}