package com.ubuntuvim.svnlog;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.ubuntuvim.framework.model.SvnLog;
import com.ubuntuvim.framework.service.impl.SVNLogService;


public class Tests {
	
	@SuppressWarnings({ "rawtypes", "rawtypes", "deprecation" })
	public List<SvnLog> sync(String name, String password, String url, long startRevision, long endRevision) {
		
		System.out.println("------------ start -----------------");
		
        // 版本库初始化
        DAVRepositoryFactory.setup();
  
        // 资源库相关登录配置信息
//	      String url = "http://svn.svnkit.com/repos/svnkit/branches/1.3.x/";
//	      String username = "anonymous";
//	      String password = "anonymous";
        
//        long startRevision = 0;// 最初版本
        long lastRevision = -1;
        // 资源库
        SVNRepository repository = null;
  
        try {
  
            // 建立一个新的资源库
            repository = DAVRepositoryFactory.create(SVNURL.parseURIEncoded(url));
            // 登录确认信息
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
            repository.setAuthenticationManager(authManager);
            lastRevision = repository.getLatestRevision();
            System.out.println("lastRevision ===== " + lastRevision);
            // 存放结果
            final Collection logEntries = new ArrayList<SVNLogEntry>();
            int page = 1;
			int rows = 15;
			startRevision = lastRevision - ((page) * rows) + 1;
            if (startRevision < 0) {
                startRevision = 0;
            }
            endRevision = lastRevision - ((page - 1) * rows);
            repository.log(new String[] { "/" }, logEntries, startRevision, endRevision, true, true);
            // 遍历
            for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
                SVNLogEntry logEntry = (SVNLogEntry) entries.next();
                if (logEntry.getChangedPaths().size() > 0) {
                    Set changedPathsSet = logEntry.getChangedPaths().keySet();
                    for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {
                        SVNLogEntryPath entryPath = logEntry.getChangedPaths().get(changedPaths.next());
                        
                        //  冗余一些数据
//                        SvnLog svnLog = new SvnLog();
//                        svnLog.setRevision(logEntry.getRevision()+"");
//                        svnLog.setAuthor(logEntry.getAuthor());
//                        svnLog.setChangeDate(logEntry.getDate());
//                        svnLog.setLogMsg(logEntry.getMessage());
//                        svnLog.setOpType(entryPath.getType()+"");
//                        svnLog.setChangeFilePath(entryPath.getPath());
//                        
//                        list.add(svnLog);
  
                        // 生成sql文件
                        String sql = "insert into svn_log(changeFilePath, revision, author, logMsg, changeDate, opType)  " +
                        		"values('"+entryPath.getPath()+"', '"+logEntry.getRevision()+"', '"+logEntry.getAuthor()+"', '"+logEntry.getMessage()+"', '"+entryPath.getType()+"');";
                        contentToTxt("c:/svn_log.sql", sql);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return null;
    }
	
	public static void contentToTxt(String filePath, String content) {  
		BufferedWriter fw = null;
		try {
			File file = new File(filePath);
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
			fw.append(content);
			fw.newLine();
			fw.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
    }  

	
	public static void main(String[] args) {

		SVNLogService slog = new SVNLogService();
		Tests syn = new Tests();
		String url = "https://10.7.86.68/svn/JAYZT/4 Implement/WEBBRANCH/2117";
        String username = "chendq";
        String password = "chendq";
		System.out.println("=======================================");
		syn.sync(username, password, url, 0, 5);
		// 保存数据
	}
	
} 