package com.upload;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestDocumentDao {
	@Autowired
	private DocumentDao docDao;
	
	@RequestMapping(value = "/testDocumentSave.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String test(HttpServletRequest request,HttpServletResponse res) {
		FileDocument doc=new FileDocument("1024","testName","2016-6-7 11:07:57","7","localhost","admin","1","2","3","10");
		FileDocument doc2=new FileDocument("1236","testName","2016-6-7 11:07:57","7","localhost","admin","1","2","3","10");
		  //使用jdbcTemplate.update方式
		docDao.insertFileDocumentUseUpdate(doc);
        //使用jdbcTemplate.execute方式
		docDao.insertFileDocumentUseExecute(doc2);
		List docs=new ArrayList();
		docs.add(doc);
		docs.add(doc2);
		//测试批量保存
		docDao.updateFileDocumentUseBatchUpdate(docs);
		return "success!";
	}
}
