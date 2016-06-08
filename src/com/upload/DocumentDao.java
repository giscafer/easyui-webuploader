package com.upload;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 * 
 * @Description: TODO(FileDocument保存接口)  
 * @author giscafer
 * @date 2016-6-7 上午11:48:48
 * @version V1.0
 */
@Repository
public class DocumentDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String tableName = "gc_form_document";
	private String insertSql = "insert into " + tableName
			+ "(D_UPSIZE,S_NAME,S_UPTIME,S_TYPE,S_UPIP,S_UPUSER,S_URL,S_LPATH,S_PLPATH,S_THUMBNAIL) values(?,?,?,?,?,?,?,?,?,?)";

	public int insertFileDocumentUseUpdate(FileDocument document) {

		Object[] params = new Object[] { document.getD_UPSIZE(),
				document.getS_NAME(), document.getS_UPTIME(),
				document.getS_TYPE(), document.getS_UPIP(),
				document.getS_UPUSER(), document.getS_URL(),
				document.getS_LPATH(), document.getS_PLPATH(),
				document.getS_THUMBNAIL() };
		return jdbcTemplate.update(insertSql, params);
	}

	public int insertFileDocumentUseExecute(FileDocument document) {
		Object[] params = new Object[] { document.getD_UPSIZE(),
				document.getS_NAME(), document.getS_UPTIME(),
				document.getS_TYPE(), document.getS_UPIP(),
				document.getS_UPUSER(), document.getS_URL(),
				document.getS_LPATH(), document.getS_PLPATH(),
				document.getS_THUMBNAIL() };
		int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
		return jdbcTemplate.update(insertSql, params, types);
	}

	public int[] updateFileDocumentUseBatchUpdate(final List list) {
		BatchPreparedStatementSetter setter = null;
		setter = new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				FileDocument document = (FileDocument) list
						.get(index);
				ps.setString(1, document.getD_UPSIZE());
				ps.setString(2, document.getS_NAME());
				ps.setString(3, document.getS_UPTIME());
				ps.setString(4, document.getS_TYPE());
				ps.setString(5, document.getS_UPIP());
				ps.setString(6, document.getS_UPUSER());
				ps.setString(7, document.getS_URL());
				ps.setString(8, document.getS_LPATH());
				ps.setString(9, document.getS_PLPATH());
				ps.setString(10, document.getS_THUMBNAIL());
			}

		};
		return jdbcTemplate.batchUpdate(insertSql, setter);
	}
}
