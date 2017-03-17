package edu.mju.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.google.gson.Gson;

import edu.mju.dao.FindPageDataDAO;
import edu.mju.util.BaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "ResultfilesAction", results = {
	
})
public class ResultfilesAction extends BaseAction {

	private static final long serialVersionUID = -4677955841001448328L;

	/**
	 * dataTable需要的数据
	 */
	 //获取请求次数
    private String draw = "0";
    //数据起始位置
    private String start;
    //数据长度
    private String length;
    //总记录数
    private String recordsTotal = "0";
	//过滤后记录数
    private String recordsFiltered = "";
    //定义列名
    private String[] cols = {"admin_id", "admin_name", "sex", "phone_num"};
    //获取客户端需要那一列排序
    private String orderColumn = "0";
    //获取排序方式 默认为asc
    private String orderDir = "asc";
    //获取用户过滤框里的字符
    private String searchValue = "";
    public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public String getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(String recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String[] getCols() {
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    /**
     * 服务端获取dataTable数据处理
     */
    public void getAllResultFiles() throws IOException
    {
    	
        //获取文件参数
        draw=request.getParameter("draw");
        start = request.getParameter("start");
        length = request.getParameter("length");
        orderColumn = request.getParameter("order[0][column]");
        orderColumn = cols[Integer.parseInt(orderColumn)];
        orderDir = request.getParameter("order[0][dir]");
        searchValue = request.getParameter("search[value]");
        
        List<String> sArray = new ArrayList<String>();
        
        if (searchValue != null ) {//判断搜索框是否为空，添加查询条件
        	System.out.println("判断搜索框是否为空，添加查询条件");
            sArray.add(" admin_id like '%" + searchValue + "%'");
            sArray.add(" admin_name like '%" + searchValue + "%'");
            sArray.add(" sex like '%" + searchValue + "%'");
            sArray.add(" phone_num like '%" + searchValue + "%'");
        }
        System.out.println("sArray.size() = " + sArray.size());
        String individualSearch = "";
        if (sArray.size() == 1) {
            individualSearch = sArray.get(0);
        } else if (sArray.size() > 1) {
            for (int i = 0; i < sArray.size() - 1; i++) {
                individualSearch += sArray.get(i) + " or ";
            }
            individualSearch += sArray.get(sArray.size() - 1);
        }
        //获取数据库总记录数
        recordsTotal=""+FindPageDataDAO.findModelCount(null);
        System.out.println("recordsTotal = "+ recordsTotal);

        String searchSQL = "";
        if (individualSearch != "") {
            searchSQL = individualSearch ;
        }
        
        if (searchValue != null) {
        	System.out.println("-------------------------------------------------------");
            recordsFiltered =""+FindPageDataDAO.findModelCount(searchSQL);
            System.out.println(recordsFiltered);
        }
        else {
            recordsFiltered = recordsTotal;
        }  
        
//        searchSQL+= " order by cast( " + orderColumn + " as int) " + orderDir;
        
        //获取data数据
        System.out.println("+++++++++++++++++分割线+++++++++++++++++++++");
        
        List<Map<String, Object>> resultList = FindPageDataDAO.pageList(searchSQL,orderColumn, Integer.valueOf(start), Integer.valueOf(length), orderDir);
        
        System.out.println("-------------------------+分割线+---------------------------------");
        Gson gson = new Gson();
		String results = null;
        if(resultList !=null)
        {
            //对数据进行处理
            Map<Object, Object> info = new HashMap<Object, Object>();
            info.put("data", resultList);
            info.put("recordsTotal", recordsTotal);
            info.put("recordsFiltered", recordsFiltered);
            info.put("draw", draw);

    		results = gson.toJson(info);
    		System.out.println("results = " + results);
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("请求数据22222222222222222222");
        response.getWriter().write(results);
        response.getWriter().close();
    }
    
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
